package com.example.hrms.dto;

import com.example.hrms.entity.Permission;
import com.example.hrms.entity.UserPermission;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public class UserDto {
    private Integer userId;
    @NotBlank
    @Size(max=20, message="用戶名稱的長度必須介於1到20之間")
    private String username;

    private String password;
    private List<PermissionDto> permissionDtos;

    public List<PermissionDto> getPermissionDtos() {
        return permissionDtos;
    }

    public void setPermissionDtos(List<PermissionDto> permissionDtos) {
        this.permissionDtos = permissionDtos;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
