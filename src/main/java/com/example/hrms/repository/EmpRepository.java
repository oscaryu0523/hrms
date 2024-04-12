package com.example.hrms.repository;

import com.example.hrms.entity.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepository extends JpaRepository<Emp, Integer> {
    List<Emp> findByDept_DeptNo(Integer deptNo);
}
