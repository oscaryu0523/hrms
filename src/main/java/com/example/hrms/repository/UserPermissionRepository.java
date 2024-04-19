package com.example.hrms.repository;

import com.example.hrms.entity.UserPermission;
import com.example.hrms.entity.UserPermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission, UserPermissionId> {
    List<UserPermission> findAllByUserUserId(Integer userId);
}
