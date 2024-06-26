package com.example.hrms.repository;

import com.example.hrms.entity.Dept;
import com.example.hrms.entity.Permission;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    boolean existsByPermissionName(String permissionName);

    Optional<Permission> findByPermissionName(String permissionName);
    @Query("SELECT p FROM Permission p WHERE p.permissionId IN :permissionIds")
    List<Permission> findAllByIdIn(@Param("permissionIds")List<Integer> permissionIds);
}
