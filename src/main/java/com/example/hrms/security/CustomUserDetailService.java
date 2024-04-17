package com.example.hrms.security;

import com.example.hrms.dto.UserDto;

import com.example.hrms.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

import java.util.stream.Collectors;
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Resource
    private UserService userService;

//    登入時會加載一個用戶的詳情
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        根據用戶名取得用戶資料
        UserDto userDto =userService.getUserByUsername(username);
//        用戶資料為空拋出異常
        if(userDto == null) {
            throw new UsernameNotFoundException(username);
        }
        System.out.println(userDto.getUsername());
//        用戶資料不為空，返回security的User類型資料(用戶名稱，用戶密碼，授權名稱列表)
        return new User(userDto.getUsername(),userDto.getPassword(),getAuthorities(userDto));
    }
//    將從資料庫中加載的用戶權限轉換成 Spring Security 能理解的格式
    private Collection<? extends GrantedAuthority> getAuthorities(UserDto userDto) {
        return userDto.getPermissionDtos()
                .stream()
                .map( permissionDto -> new SimpleGrantedAuthority(permissionDto.getPermissionName()))
                .collect(Collectors.toList());
    }
}
