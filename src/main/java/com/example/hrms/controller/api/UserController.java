package com.example.hrms.controller.api;

import com.example.hrms.dto.UserDto;
import com.example.hrms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    public String register(UserDto userDto){
        return "index.html";
    }
}
