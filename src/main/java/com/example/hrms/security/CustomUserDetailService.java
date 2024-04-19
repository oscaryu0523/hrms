package com.example.hrms.security;

import com.example.hrms.repository.UserPermissionRepository;
import com.example.hrms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPermissionRepository userPermissionRepository;


    //    登入時會自動加載一個用戶的詳情、先改用戶名稱、用戶權限時也需要手動加載
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("執行代碼loadUserByUsername");
//        根據用戶名取得用戶資料
        com.example.hrms.entity.User user = userRepository.findOneByUsername(username);
//        用戶資料為空拋出異常
        if(user == null) {
            throw new UsernameNotFoundException("用戶未找到" + username);
        }
        System.out.println(user.getUsername());
//        用戶資料不為空，返回security的User類型資料(用戶名稱，用戶密碼，授權名稱列表)
        return new User(user.getUsername(),user.getPassword(),getAuthorities(user));
    }
//    將從資料庫中加載的用戶權限轉換成 Spring Security 能理解的格式
    private Collection<? extends GrantedAuthority> getAuthorities(com.example.hrms.entity.User  user) {
        return userPermissionRepository
                .findAllByUserUserId(user.getUserId())
                .stream()
                .map( userPermission -> new SimpleGrantedAuthority(userPermission.getPermission().getPermissionName()))
                .collect(Collectors.toList());


    }
}
