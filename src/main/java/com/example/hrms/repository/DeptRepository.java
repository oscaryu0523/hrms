package com.example.hrms.repository;

import com.example.hrms.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeptRepository extends JpaRepository<Dept, Integer> {
    boolean existsByDname(String dname);

    Optional<Dept> findByDname(String dname);
}
