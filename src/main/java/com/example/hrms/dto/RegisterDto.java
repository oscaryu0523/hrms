package com.example.hrms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterDto {

    @NotBlank(message = "用戶名稱不能為空")
    @Size(min=2,max=10,message = "用戶名稱長度必須在2-10之間")
    private String username;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,20}$", message = "密碼必須是8至20位，且包含至少一個英文字母和一個數字")
    @NotBlank(message = "密碼不能為空")
    private String password;


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
