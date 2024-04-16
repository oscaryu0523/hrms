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

import java.util.Collection;

import java.util.stream.Collectors;

public class CustomUserDetailService implements UserDetailsService {

    @Resource
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto =userService.getUserByUsername(username);
        return new User(userDto.getUsername(),userDto.getPassword(),getAuthorities(userDto));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserDto userDto) {
        return userDto.getPermissionDtos()
                .stream()
                .map( permissionDto -> new SimpleGrantedAuthority(permissionDto.getPermissionName()))
                .collect(Collectors.toList());
    }
}
