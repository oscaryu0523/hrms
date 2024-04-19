package com.example.hrms.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // commence 方法是當試圖訪問安全受保護的資源但是用戶未通過認證時被調用
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, // 發送一個 401 錯誤響應
                authException.getMessage()); // 使用從 AuthenticationException 獲取的錯誤信息作為響應的描述
    }
}
