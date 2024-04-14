package com.example.hrms.service;

import com.example.hrms.dto.DeptDto;
import com.example.hrms.entity.Dept;
import com.example.hrms.repository.DeptRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeptService {
    private static final Logger log = LoggerFactory.getLogger(DeptService.class);

    @Autowired
    private DeptRepository deptRepository;

    public boolean existsById(Integer deptNo) {
        return deptRepository.existsById(deptNo);
    }

    public Object getDepts() {
        return deptRepository.findAll();
    }

    //    保存一筆部門資料
    @Transactional
    public Optional<Dept> saveDept(DeptDto deptDto) {
        if (deptRepository.existsById(deptDto.getDeptNo())) {
            log.warn("部門編號{}已被使用，無法創建新部門。", deptDto.getDeptNo());
            return Optional.empty();
        }
        if (deptRepository.existsByDname(deptDto.getDname())) {
            log.warn("部門名稱{}已被使用，無法創建新部門。", deptDto.getDname());
            return Optional.empty();
        }
        Dept dept = new Dept();
        dept.setDeptNo(deptDto.getDeptNo());
        dept.setDname(deptDto.getDname());
        dept.setLocation(deptDto.getLocation());
        log.info("創建新部門：{}，位置：{}", dept.getDname(), dept.getLocation());
        return Optional.of(deptRepository.save(dept));
    }

    public Optional<Dept> findById(Integer deptNo) {
        return deptRepository.findById(deptNo);
    }

    public Optional<Object> updateDept(Dept dept) {
        // 檢查是否有其他部門使用了相同的部門名稱且部門編號不同
        Optional<Dept> existingDeptByName = deptRepository.findByDname(dept.getDname());
        if (existingDeptByName.isPresent() && !existingDeptByName.get().getDeptNo().equals(dept.getDeptNo())) {
            return Optional.empty(); // 如果名稱已被使用，且不是同一部門編號，則返回空
        }

        // 查找並更新現有的部門資料
        return deptRepository.findById(dept.getDeptNo())
                .map(existingDept -> {
                    existingDept.setDname(dept.getDname());
                    existingDept.setLocation(dept.getLocation());
                    return deptRepository.save(existingDept); // 保存並返回更新後的部門
                });
    }

    //  刪除一筆員工資料
    public Optional<Boolean> deleteDept(Integer deptNo) {
        return deptRepository.findById(deptNo)
                .map(dept -> {
                    deptRepository.delete(dept);
                    return true;
                });
    }
}
