package com.example.hrms.controller;

import com.example.hrms.dto.LoginDto;
import com.example.hrms.dto.RegisterDto;
import com.example.hrms.dto.UserDto;
import com.example.hrms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log= LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
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
////    登入後跳轉回首頁
    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto loginDto, Model model) {
        System.out.println("進入login方法");
        if (loginDto != null) {
            // 使用 Spring Security 的 AuthenticationManager 來設定用戶的認證資訊
            System.out.println("開始認證");
            Authentication authenticate = authenticationManager.authenticate(//用於驗證用戶的身分，執行CustomUserDetailService的loadUserByUserName
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())//用於儲存用戶名的認證信息
            );
//            無限遞歸
            System.out.println("認證完畢");
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            System.out.println("成功");
            System.out.println("認證信息: " + SecurityContextHolder.getContext().getAuthentication());
            System.out.println(authenticate.isAuthenticated());
            return "redirect:/user/index"; // 登入成功後跳轉的頁面
        } else {
            System.out.println("錯誤");
            model.addAttribute("error", "Invalid username or password");
            return "error"; // 登入失敗，重新載入登入頁面並顯示錯誤訊息
        }
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/user/loginPage"; // 登出後重定向到登入頁面
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

}
