package com.example.hrms.service;

import com.example.hrms.dto.PermissionDto;
import com.example.hrms.dto.UserDto;
import com.example.hrms.entity.Permission;
import com.example.hrms.entity.User;
import com.example.hrms.entity.UserPermission;
import com.example.hrms.repository.PermissionRepository;
import com.example.hrms.repository.UserPermissionRepository;
import com.example.hrms.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PermissionRepository permissionRepository;

//    註冊
    @Transactional
    public void register(UserDto userDto){
        User user=dtoToUser(userDto);
        userRepository.save(user);
//        默認權限
        UserPermission userPermission = new UserPermission();
        userPermission.setUser(user);
        userPermission.setPermission(permissionRepository.findByPermissionName("基本權限").get());
        userPermissionRepository.save(userPermission);

    }

//  根據用戶名取得一筆用戶資料
    public UserDto getUserByUsername(String username){
        User user = userRepository.findOneByUserName(username);
        if (user == null) {
            log.warn("該用戶名稱{}不存在",username); // 或者拋出一個適當的異常，表示用戶未找到
        }

        List<UserPermission> userPermissionList = userPermissionRepository.findAllByUserId(user.getUserId());
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(passwordEncoder.encode(user.getPassword()));

        if (!CollectionUtils.isEmpty(userPermissionList)) {
            List<PermissionDto> permissionDtos = userPermissionList.stream()
                    .map(UserPermission::getPermission) // 直接獲取 Permission 實體
                    .distinct() // 去除重複，避免不必要的多次轉換
                    .map(this::entityToDto)
                    .collect(Collectors.toList());
            userDto.setPermissionDtos(permissionDtos);
        }
        return userDto;

    }



    private User dtoToUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return user;
    }
    private UserDto userToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
    private PermissionDto entityToDto(Permission permission) {
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setPermissionId(permission.getPermissionId());
        permissionDto.setPermissionName(permission.getPermissionName());
        return permissionDto;
    }
}
