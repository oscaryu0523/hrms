package com.example.hrms.service;

import com.example.hrms.dto.*;
import com.example.hrms.entity.Permission;
import com.example.hrms.entity.User;
import com.example.hrms.entity.UserPermission;
import com.example.hrms.entity.UserPermissionId;
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
import java.util.Optional;
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


  public List<PermissionDto> getAllPermissions() {
      return permissionRepository.findAll()
              .stream()
              .map(this:: permissionToDto)
              .collect(Collectors.toList());
  }

//    註冊
    @Transactional
    public UserDto register(RegisterDto registerDto) {
        User existingUser = userRepository.findOneByUsername(registerDto.getUsername());
        if (existingUser != null) {
            throw new IllegalStateException("用戶名已存在");
        }

        User newUser = registerDtoToUser(registerDto);
        User user = userRepository.save(newUser);

        // 添加基本權限
        assignDefaultPermission(user);

        return userToDto(user);
    }


//    登入驗證
    // 登入檢查和載入用戶
    public UserDto loginCheck(LoginDto loginDto) {
        User user = userRepository.findOneByUsername(loginDto.getUsername());
        if (user != null && passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return getUserDetails(user);
        }
        return null;
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
    private User registerDtoToUser(RegisterDto registerDto){
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        return user;
    }

    public List<UserPermissionDto> getAllUsersWithPermissions() {
        return userRepository.findAll()
                .stream()
                .map(user -> {
                    UserPermissionDto userPermissionDto = new UserPermissionDto();
                    userPermissionDto.setUserId(user.getUserId());
                    userPermissionDto.setUsername(user.getUsername());
                    // 找出所有权限并转换为DTO
                    List<PermissionDto> permissionDtos = user.getUserPermissions()
                            .stream()
                            .map(userPermission -> permissionToDto(userPermission.getPermission()))
                            .collect(Collectors.toList());
                    userPermissionDto.setPermissionDtoList(permissionDtos);
                    return userPermissionDto; // 确保返回 UserPermissionDto 对象
                })
                .collect(Collectors.toList());
    }

    // 根據用戶名取得一筆用戶資料，包括權限
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findOneByUsername(username);
        return user != null ? getUserDetails(user) : null;
    }

    // 輔助方法：獲取用戶的詳細資料和權限
    private UserDto getUserDetails(User user) {
        List<PermissionDto> permissions = getPermissionsForUser(user.getUserId());
        UserDto userDto = userToDto(user);
        userDto.setPermissionDtos(permissions);
        return userDto;
    }
    // 給新用戶分配默認權限
    private void assignDefaultPermission(User user) {
        Permission defaultPermission = permissionRepository.findByPermissionName("基本權限")
                .orElseThrow(() -> new IllegalStateException("基本權限未找到，無法分配權限"));

        UserPermission userPermission = new UserPermission();
        userPermission.setUser(user);
        userPermission.setPermission(defaultPermission);
        UserPermissionId userPermissionId = new UserPermissionId(user.getUserId(), defaultPermission.getPermissionId());
        userPermission.setId(userPermissionId);
        userPermissionRepository.save(userPermission);
    }

    // 根據用戶ID取得所有權限的 DTO 列表
    private List<PermissionDto> getPermissionsForUser(Integer userId) {
        return userPermissionRepository.findAllByUserUserId(userId).stream()
                .map(UserPermission::getPermission)
                .distinct()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

//
    private PermissionDto permissionToDto(Permission permission) {
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setPermissionId(permission.getPermissionId());
        permissionDto.setPermissionName(permission.getPermissionName());
        return permissionDto;
    }
}
