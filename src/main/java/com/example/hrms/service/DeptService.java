package com.example.hrms.service;

import com.example.hrms.entity.Dept;
import com.example.hrms.repository.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptService {
    @Autowired
    private DeptRepository deptRepository;

    public boolean existsById(Integer deptNo){
        return deptRepository.existsById(deptNo);
    }

    public Dept addDept(Dept dept) {
        return deptRepository.save(dept);
    }
}
