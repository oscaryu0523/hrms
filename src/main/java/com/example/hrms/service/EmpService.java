package com.example.hrms.service;

import com.example.hrms.dto.DeptDto;
import com.example.hrms.dto.EmpDto;
import com.example.hrms.entity.Dept;
import com.example.hrms.entity.Emp;
import com.example.hrms.repository.DeptRepository;
import com.example.hrms.repository.EmpRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpService {
    private static final Logger log = LoggerFactory.getLogger(EmpService.class);

    @Autowired
    private EmpRepository empRepository;
    @Autowired
    private DeptRepository deptRepository;
//  刪除一筆員工資料
    @Transactional
    public Optional<Boolean> deleteEmp(Integer empNo) {
        return empRepository.findById(empNo)
                .map(emp -> {
                    empRepository.delete(emp);
                    return true;
                });
    }

    //    更新一筆員工資料
    @Transactional
    public Optional<Emp> updateEmp(EmpDto empDto) {
//        檢查員工編號是否存在
        return empRepository.findById(empDto.getEmpNo())
                .map(existingEmp -> {
//            檢查mail是否已被使用
                    if(empRepository.existsByEmailAndEmpNoNot(empDto.getEmail(),empDto.getEmpNo())){
                        log.warn("郵箱{}已被使用", empDto.getEmail());
                        return null;
                    }
                    return empRepository.save(dtoToEntity(empDto));
                });

    }



//  新增一筆員工資料
    @Transactional
    public Optional<Emp> saveEmp(EmpDto empDto) {
//        先檢查電子郵件是否已被其他員工使用
        if(empRepository.existsByEmail(empDto.getEmail())){
            log.warn("郵箱{}已被使用", empDto.getEmail());
            return Optional.empty();
        }
//        檢查部門是否存在
        return deptRepository.findById(empDto.getDeptNo())
                .map(dept -> {
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
                })
                .orElseGet(() -> {
                    log.warn("該部門編號{}不存在", empDto.getDeptNo());
                    return Optional.empty();
                });

    }


//    員工複合查詢(部門編號 加 關鍵字)
    public List<Emp>searchEmps(Optional<Integer> deptNo, Optional<String> keyword) {
        if(deptNo.isPresent() && keyword.isPresent()){
            return empRepository.findByDept_DeptNoAndEnameContaining(deptNo.get(),keyword.get());
        }else if(deptNo.isPresent()){
            return empRepository.findByDept_DeptNo(deptNo.get());
        }else if(keyword.isPresent()){
            return empRepository.findByEnameContaining(keyword.get());
        }else{
            return empRepository.findAll();
        }
    }
//    用員工編號取得一筆員工資料
    public Optional<EmpDto> findById(Integer empNo) {
        return empRepository.findById(empNo)
                .map(this::entityToDto);
    }


//    獲取所有員工列表
    public List<EmpDto> getEmps() {
        return empRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
//    獲取所有部門列表
    public List<DeptDto> getDepts() {
        return deptRepository.findAll()
                .stream()
                .map(this::deptEntityToDto)
                .collect(Collectors.toList());
    }

    private Emp dtoToEntity(EmpDto empDto) {
        Emp emp = new Emp();
        emp.setEmpNo(empDto.getEmpNo());
        emp.setEname(empDto.getEname());
        emp.setDept(deptRepository.findById(empDto.getDeptNo()).get());
        emp.setJob(empDto.getJob());
        emp.setEmail(empDto.getEmail());
        emp.setPhoneNumber(empDto.getPhoneNumber());
        emp.setAddress(empDto.getAddress());
        emp.setSal(empDto.getSal());
        emp.setHiredate(empDto.getHiredate());
        return emp;
    }
    private EmpDto entityToDto(Emp emp){
        EmpDto empDto = new EmpDto();
        empDto.setEmpNo(emp.getEmpNo());
        empDto.setEname(emp.getEname());
        empDto.setDname(emp.getDept().getDname());
        empDto.setDeptNo(emp.getDept().getDeptNo());
        empDto.setJob(emp.getJob());
        empDto.setEmail(emp.getEmail());
        empDto.setPhoneNumber(emp.getPhoneNumber());
        empDto.setAddress(emp.getAddress());
        empDto.setSal(emp.getSal());
        empDto.setHiredate(emp.getHiredate());
        return empDto;
    }
    private DeptDto deptEntityToDto(Dept dept){
        DeptDto deptDto = new DeptDto();
        deptDto.setDeptNo(dept.getDeptNo());
        deptDto.setDname(dept.getDname());
        deptDto.setLocation(dept.getLocation());
        return deptDto;
    }


}
