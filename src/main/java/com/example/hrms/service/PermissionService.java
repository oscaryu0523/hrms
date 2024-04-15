package com.example.hrms.service;

import com.example.hrms.dto.PermissionDto;
import com.example.hrms.entity.Dept;
import com.example.hrms.entity.Permission;
import com.example.hrms.repository.PermissionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private static final Logger log = LoggerFactory.getLogger(PermissionService.class);

    @Autowired
    private PermissionRepository permissionRepository;
//    取得所有權限資料
    public List<PermissionDto> getPermissions() {
        return permissionRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
//    新增一筆權限資料
    @Transactional
    public Optional<PermissionDto> savePermission(PermissionDto permissionDto) {
        if (permissionRepository.existsByPermissionName(permissionDto.getPermissionName())) {
            log.warn("權限名稱{}已被使用，無法創建新權限。", permissionDto.getPermissionName());
            return Optional.empty();
        }
        Permission permission = dtoToEntity(permissionDto);

        return Optional.of(entityToDto(permissionRepository.save(permission)));
    }

    public Optional<PermissionDto> findPermissionById(Integer permissionId) {
        return permissionRepository.findById(permissionId)
                .map(this::entityToDto);
    }
//    更新一筆權限資料
    @Transactional
    public Optional<Object> updatePermission(PermissionDto permissionDto) {
        return permissionRepository.findById(permissionDto.getPermissionId())
                .map(existingPermission -> {
                    if (!existingPermission.getPermissionName().equals(permissionDto.getPermissionName())) {
                        existingPermission.setPermissionName(permissionDto.getPermissionName());
                        log.info("Updating permission ID {}: new name '{}'", permissionDto.getPermissionId(), permissionDto.getPermissionName());
                        return permissionRepository.save(existingPermission);
                    }
                    log.info("No changes detected for permission ID {}", permissionDto.getPermissionId());
                    return existingPermission;
                });
    }
//    刪除一筆權限資料
    @Transactional
    public Optional<Boolean> deletePermission(Integer permissionId) {
        return permissionRepository.findById(permissionId)
                .map(dept -> {
                    permissionRepository.delete(dept);
                    return true;
                });
    }
    private Permission dtoToEntity(PermissionDto dto) {
        Permission permission = new Permission();
        permission.setPermissionName(dto.getPermissionName());
        return permission;
    }
    private PermissionDto entityToDto(Permission permission) {
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setPermissionId(permission.getPermissionId());
        permissionDto.setPermissionName(permission.getPermissionName());
        return permissionDto;
    }
}
