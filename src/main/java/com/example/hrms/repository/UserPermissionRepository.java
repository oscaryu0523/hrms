package com.example.hrms.repository;

import com.example.hrms.entity.UserPermission;
import com.example.hrms.entity.UserPermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPermissionRepository extends JpaRepository<UserPermission, UserPermissionId> {
    List<UserPermission> findAllByUserId(Integer userId);
}
