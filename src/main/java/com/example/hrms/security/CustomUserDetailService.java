package com.example.hrms.security;

import com.example.hrms.dto.UserDto;

import com.example.hrms.entity.Permission;
import com.example.hrms.repository.UserRepository;
import com.example.hrms.service.PermissionService;
import com.example.hrms.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CustomUserDetailService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionService permissionService;


    //    登入時會加載一個用戶的詳情
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("執行代碼loadUserByUsername");
//        根據用戶名取得用戶資料
        com.example.hrms.entity.User user = userRepository.findOneByUsername(username);
//        用戶資料為空拋出異常
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        System.out.println(user.getUsername());
//        用戶資料不為空，返回security的User類型資料(用戶名稱，用戶密碼，授權名稱列表)
        return new User(user.getUsername(),user.getPassword(),getAuthorities(user));
    }
//    將從資料庫中加載的用戶權限轉換成 Spring Security 能理解的格式
    private Collection<? extends GrantedAuthority> getAuthorities(com.example.hrms.entity.User  user) {
        List<String> permissionNames = user.getUserPermissions().stream().map(userPermission -> userPermission.getPermission().getPermissionName()).collect(Collectors.toList());
        for (String permissionName : permissionNames) {
            System.out.println(permissionName);
        }

        return user.getUserPermissions()
                .stream()
                .map( userPermission -> new SimpleGrantedAuthority(userPermission.getPermission().getPermissionName()))
                .collect(Collectors.toList());
    }
}
