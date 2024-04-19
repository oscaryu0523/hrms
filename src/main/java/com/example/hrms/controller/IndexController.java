package com.example.hrms.controller;

import com.example.hrms.dto.LoginDto;
import com.example.hrms.dto.RegisterDto;
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

@Controller
public class IndexController {
    @Autowired
    private AuthenticationManager authenticationManager;
    //    登入後跳轉回首頁
    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto loginDto, Model model) {
        System.out.println("進入login方法");
        if (loginDto != null) {
            // 使用 Spring Security 的 AuthenticationManager 來設定用戶的認證資訊
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            System.out.println("成功");
            return "redirect:/"; // 登入成功後跳轉的頁面
        } else {
            System.out.println("錯誤");
            model.addAttribute("error", "Invalid username or password");
            return "error"; // 登入失敗，重新載入登入頁面並顯示錯誤訊息
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
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
