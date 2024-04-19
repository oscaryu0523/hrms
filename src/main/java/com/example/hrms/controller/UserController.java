package com.example.hrms.controller;

import com.example.hrms.dto.*;
import com.example.hrms.entity.User;
import com.example.hrms.entity.UserPermission;
import com.example.hrms.repository.UserPermissionRepository;
import com.example.hrms.repository.UserRepository;
import com.example.hrms.security.CustomUserDetailService;
import com.example.hrms.service.PermissionService;
import com.example.hrms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log= LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private UserPermissionRepository userPermissionRepository;

    //    註冊後跳轉回首頁
    @PostMapping("/register")
    public String register(@ModelAttribute RegisterDto registerDto, Model model){
        try {
            UserDto existingUserDto = userService.getUserByUsername(registerDto.getUsername());
            if (existingUserDto != null) {
                model.addAttribute("error", "用戶名已存在");
                return "error";
            }

            UserDto newUserDto = userService.register(registerDto);
            log.info("Created User with ID: {}", newUserDto.getUserId());
            return "redirect:/user/index";
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("registerDto", registerDto);
            return "login";
        }
    }


//    登入頁面
    @GetMapping("/loginPage")
    public String loginPage(Model model){
        model.addAttribute("loginDto", new LoginDto());
        model.addAttribute("registerDto", new RegisterDto());
        return "login";
    }
//    首頁
    @GetMapping("/index")
    public String index(){
        System.out.println("認證信息: " + SecurityContextHolder.getContext().getAuthentication());
        return "index";
    }

//    用戶資料及權限列表
    @GetMapping("/list")
    public String list(Model model){
//        取得所有權限列表
        List<PermissionDto> permissionDtos = userService.getAllPermissions();
        model.addAttribute("permissionDtos", permissionDtos);
//        取得所有員工列表(包含員工所有權限)
        List<UserPermissionDto> users = userService.getAllUsersWithPermissions();
        model.addAttribute("users",users);
//        取得所有員工列表
        return "/user/userList";
    }

    @PostMapping("/updatePermission")
    public ResponseEntity<?> updatePermission(@RequestBody UserPermissionChangeDto userPermissionChangeDto, Model model){
        log.info("開始執行權限更新：{}", userPermissionChangeDto);
        try {
            permissionService.updatePermissionById(userPermissionChangeDto);

            return userService.findById(userPermissionChangeDto.getUserId())
                    .map(userDto -> {
                        updateAuthentication(userDto);
                        List<UserPermission> userPermissions = userPermissionRepository.findAllByUserUserId(userDto.getUserId());
                        System.out.println("最新權限");
                        for (UserPermission userPermission: userPermissions){
                            System.out.println(userPermission.getPermission().getPermissionName());
                        }
                        log.info("權限更新成功，用戶ID: {}", userDto.getUserId());
                        return ResponseEntity.ok().body(Map.of("success", true, "message", "權限更新成功"));
                    })
                    .orElseGet(() -> {
                        log.error("用戶未找到, ID: {}", userPermissionChangeDto.getUserId());
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "用戶未找到"));
                    });
        } catch (Exception e) {
            log.error("權限更新失敗", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "權限更新失敗: " + e.getMessage()));
        }
    }
//    取得當前用戶資料
    @GetMapping("/basic")
    public String getUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            Object principal = authentication.getPrincipal();
            if(principal instanceof UserDetails){
                UserDetails userDetails = (UserDetails) principal;
                UserDto userDto = userService.getUserByUsername(userDetails.getUsername());
                model.addAttribute("user", userDetails);
                model.addAttribute("userId", userDto.getUserId());
            }
            return "/user/basic";

        } else {
            model.addAttribute("error", "no authenticated user found");
            return "/error";
        }
    }
//    更新用戶資料
    @PostMapping("/update")
    public String updateUser(@ModelAttribute UserDto userDto, Model model){
        try {
            UserDto updatedUser = userService.updateUser(userDto); // 假設有一個更新用戶的方法
            log.info("用戶資料已更新");

            // 更新 SecurityContextHolder 中的 UserDetails
            updateAuthentication(updatedUser);


            model.addAttribute("message", "用戶資料已更新");

            return "redirect:/user/basic";
        } catch (Exception e) {
            model.addAttribute("error", "更新失敗: " + e.getMessage());
            return "error";
        }
    }

    private void updateAuthentication(UserDto userDto){
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails newUserDetails = customUserDetailService.loadUserByUsername(userDto.getUsername());

        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                newUserDetails, null, newUserDetails.getAuthorities());

        newAuth.setDetails(currentAuth.getDetails());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
