package com.example.hrms.dto;

import java.util.List;

public class UserPermissionDto {
    private Integer userId;
    private String username;
//    private List<PermissionDto> permissionDtoList;
    private List<Boolean> permissionsPresence;
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

//    public List<PermissionDto> getPermissionDtoList() {
//        return permissionDtoList;
//    }
//

    public List<Boolean> getPermissionsPresence() {
        return permissionsPresence;
    }

    public void setPermissionsPresence(List<Boolean> permissionsPresence) {
        this.permissionsPresence = permissionsPresence;
    }
//    public void setPermissionDtoList(List<PermissionDto> permissionDtoList) {
//        this.permissionDtoList = permissionDtoList;
//    }
}
