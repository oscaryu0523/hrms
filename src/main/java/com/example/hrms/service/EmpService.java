package com.example.hrms.service;

import com.example.hrms.dto.EmpDto;
import com.example.hrms.entity.Dept;
import com.example.hrms.entity.Emp;
import com.example.hrms.repository.DeptRepository;
import com.example.hrms.repository.EmpRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpService {

    @Autowired
    private EmpRepository empRepository;
    @Autowired
    private DeptRepository deptRepository;
//  新增一筆員工資料
    @Transactional
    public Optional<Emp> addEmp(EmpDto empDto) {
//        取得
        Dept dept = deptRepository.findById(empDto.getDeptNo()).orElse(null);
        if(dept==null){
            return Optional.empty();
        }
        Emp emp = new Emp();
        emp.setEname(empDto.getEname());
        emp.setDept(dept);
        emp.setJob(empDto.getJob());
        emp.setEmail(empDto.getEmail());
        emp.setPhoneNumber(empDto.getPhoneNumber());
        emp.setAddress(empDto.getAddress());
        emp.setHiredate(empDto.getHiredate());
        emp.setSal(empDto.getSal());
        return Optional.of(empRepository.save(emp));
    }



//    更新一筆員工資料
    @Transactional
    public Optional<Emp> updateEmp(EmpDto empDto, Integer empNo) {
        Dept dept = deptRepository.findById(empDto.getDeptNo()).orElse(null);
        if(dept==null){
            return Optional.empty();
        }
        Emp emp = new Emp();
        emp.setEmpNo(empNo);
        emp.setEname(empDto.getEname());
        emp.setDept(dept);
        emp.setJob(empDto.getJob());
        emp.setEmail(empDto.getEmail());
        emp.setPhoneNumber(empDto.getPhoneNumber());
        emp.setAddress(empDto.getAddress());
        emp.setHiredate(empDto.getHiredate());
        emp.setSal(empDto.getSal());
        return Optional.of(empRepository.save(emp));
    }
//    查詢該部門底下所有員工
    public List<Emp>getEmpsByDept(Integer deptNo) {
        return empRepository.findByDept_DeptNo(deptNo);
    }
//  根據員工編號查詢一筆員工資料
    public Emp getEmp(Integer empNo) {
        return empRepository.findById(empNo).orElse(null);
    }
//  根據
    public Optional<Emp> findById(Integer empNo) {
        return empRepository.findById(empNo);
    }

    public boolean existsById(Integer empNo) {
        return empRepository.existsById(empNo);
    }

    public List<Emp> getEmps() {
        return empRepository.findAll();
    }
    public List<Dept> getDepts() {
        return deptRepository.findAll();
    }

    @Transactional
    public void deleteEmp(Integer empNo) {
        empRepository.deleteById(empNo);
    }
}
