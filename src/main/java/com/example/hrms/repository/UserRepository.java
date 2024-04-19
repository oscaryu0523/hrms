package com.example.hrms.repository;

import com.example.hrms.dto.UserPermissionDto;
import com.example.hrms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findOneByUsername(String username);

//

    List<User> findAll();
}
