package com.example.hrms.security;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private AuthEntryPoint authEntryPoint;

    @Autowired
    private CustomUserDetailService customUserDetailService;
    // 定義安全過濾器鏈
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .csrf(csrf -> csrf.disable()) // 禁用CSRF保護，CSRF（跨站請求偽造）是一種攻擊方式，但在一些REST API場景下可能會禁用以避免不必要的複雜性。
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authEntryPoint)//用戶為認證時執行的操作
                        .accessDeniedHandler(new AccessDeniedHandler() {
                            @Override
                            public void handle(HttpServletRequest request, HttpServletResponse response,
                                               AccessDeniedException accessDeniedException) throws IOException, ServletException {
                                // 用戶已認證，但權限不足時執行的操作
                                try {
                                    String refererHeader = request.getHeader("Referer");
                                    String redirectUrl = "/user/index";  // Default redirect

                                    if (refererHeader != null) {
                                        // Remove query parameters
                                        URI uri = new URI(refererHeader);
                                        redirectUrl = uri.getPath();
                                    }
                                    String encodedMessage = URLEncoder.encode("權限不足", StandardCharsets.UTF_8.toString());
                                    response.sendRedirect(redirectUrl + "?error=" + encodedMessage); // 重定向並添加錯誤信息
                                } catch (URISyntaxException e) {
                                    // Log error or handle syntax error
                                    response.sendRedirect("/user/index");
                                }
                            }
                        })// 自定義身份驗證失敗的處理點
                )
                // 配置URL的安全訪問規則：
                .authorizeHttpRequests(authorize -> authorize

                                // 允許對以下URL無需驗證即可訪問，包括登入頁、首頁等靜態資源。
                                .requestMatchers("/user/loginPage","/user/index","/favicon.ico","css/**","js/**","/images/**").permitAll() // 允許無需驗證就可以訪問的路徑
                                // 允許無需驗證即可執行的POST請求，通常用於登入和註冊。
                                .requestMatchers(HttpMethod.POST,"/user/login", "/user/register").permitAll() // 允許登錄接口
                                // 對特定功能設置權限要求，如員工管理、部門管理等，需具有相應的權限才能訪問。
                                .requestMatchers("/emp/**").hasAuthority("員工管理")  // 員工管理
                        .requestMatchers("/dept/**").hasAuthority("部門管理") // 部門管理
                        .requestMatchers("/permission/**").hasAuthority("權限管理") // 權限管理
                        .requestMatchers("/user/list").hasAuthority("用戶權限管理") // 用戶權限管理
                        .requestMatchers("/user/basic").hasAuthority("基本權限")
//                        // 其他所有請求都需要驗證
                        .anyRequest().authenticated() // 其他所有請求都需要驗證
                )
                // 配置表單登入：
                .formLogin(formLogin -> formLogin
                        // 指定登入頁面的URL。
                        .loginPage("/user/login")
                        // 登入成功後的重定向URL，這裡是用戶首頁。
                        .defaultSuccessUrl("/user/index", true)
                        // 登入失敗後的重定向URL，帶上錯誤訊息。
                        .failureUrl("/error")
                        // 允許所有用戶訪問登入頁面。
                        .permitAll()
                )
                // 配置登出行為：
                .logout( logout -> logout
                        // 登出的URL。
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                        // 登出成功後的重定向URL。
                        .logoutSuccessUrl("/user/loginPage")
                        // 登出時使session無效。
                        .invalidateHttpSession(true)
                        // 登出時刪除特定的cookies。
                        .deleteCookies("JSESSIONID")
                        // 允許所有用戶登出。
                        .permitAll()
                )
                // 會話管理配置，用於控制會話的創建和使用方式：
                .sessionManagement(sessionManagement -> sessionManagement
                        // 設置會話創建策略，這裡是在需要時創建會話。
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        // 設置每個帳號同時只能有一個有效會話，如果超過則使舊會話失效。
                        .maximumSessions(1)
                        // 會話過期後的重定向URL。
                        .expiredUrl("/login?expired=true")
                ).csrf(Customizer.withDefaults());;

        return http.build(); // 建立並返回安全過濾器鏈


    }
////    // 身份驗證管理器
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Arrays.asList(authenticationProvider()));
    }


    // 密碼編碼器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();// 使用 BCrypt 強哈希算法
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }




}
