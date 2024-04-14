package com.example.hrms.repository;

import com.example.hrms.entity.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepository extends JpaRepository<Emp, Integer> {
    List<Emp> findByDept_DeptNo(Integer deptNo);


    List<Emp> findByEnameContaining(String keyword);

    List<Emp> findByDept_DeptNoAndEnameContaining(Integer deptNo, String nameKeyword);

    boolean existsByEmail(String email);
    boolean existsByEmailAndEmpNoNot(String email, Integer empNo);

}
