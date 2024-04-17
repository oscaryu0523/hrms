package com.example.hrms.security;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
    // 定義安全過濾器鏈
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 禁用CSRF保護//CSRF（跨站請求偽造）是一種攻擊方式，
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authEntryPoint)  // 自定義身份驗證失敗的處理點
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/loginPage","/user/index","/favicon.ico","css/**","js/**","/images/**").permitAll() // 允許無需驗證就可以訪問的路徑
                        .requestMatchers(HttpMethod.POST,"/user/login", "/user/register").permitAll() // 允許登錄接口
                        .requestMatchers("/emp/**").hasAuthority("員工管理")  // 員工管理
                        .requestMatchers("/dept/**").hasAuthority("部門管理") // 部門管理
                        .requestMatchers("/permission/**").hasAuthority("權限管理") // 權限管理
                        .requestMatchers("/userPermission/**").hasAuthority("用戶權限管理") // 用戶權限管理
                        .requestMatchers("/basic/**").hasAuthority("基本權限")
//                        .requestMatchers("/**").hasAuthority("超級權限")
                        .anyRequest().authenticated() // 其他所有請求都需要驗證
                )
                .httpBasic(httpBasic -> httpBasic  //realmName("user") 設置了一個基本身份驗證對話框中顯示的“realm”名稱。這是一個提示給用戶的
                        .realmName("user") // 設置 HTTP 基本認證的 realm 名稱
                )
                .sessionManagement(sessionManagement -> sessionManagement  //會話管理 在Spring Security中用於控制會話的創建和使用方式
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 配置 session 管理策略為無狀態
                ).logout(logout -> logout
                .logoutUrl("/user/logout") // 觸發登出的 URL
                .logoutSuccessUrl("/user/loginPage") // 登出成功後重定向的 URL
                .deleteCookies("JSESSIONID") // 登出時刪除 cookies
                .invalidateHttpSession(true) // 登出時使 session 無效
                );
        return http.build(); // 建立並返回安全過濾器鏈
    }
////    // 身份驗證管理器
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 密碼編碼器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();// 使用 BCrypt 強哈希算法
    }
}
