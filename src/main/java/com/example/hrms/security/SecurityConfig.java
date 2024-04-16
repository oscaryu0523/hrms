package com.example.hrms.security;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private AuthEntryPoint authEntryPoint;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(exceptionhandle -> exceptionhandle
                        .authenticationEntryPoint(authEntryPoint)
                )
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .anyRequest().authenticated() // 配置授權，要求所有進來的請求都必須被驗證
                )
                .httpBasic(httpBasic -> httpBasic
                        .realmName("user") // 設置 HTTP 基本認證的realm名稱
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 配置session管理策略為無狀態
                );
        return http.build(); // 建立並返回安全過濾器鏈
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
