package com.example.hrms.service;

import com.example.hrms.dto.DeptDto;
import com.example.hrms.entity.Dept;
import com.example.hrms.repository.DeptRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeptService {
    private static final Logger log = LoggerFactory.getLogger(DeptService.class);

    @Autowired
    private DeptRepository deptRepository;

    public boolean existsById(Integer deptNo) {
        return deptRepository.existsById(deptNo);
    }

    public List<DeptDto> getDepts() {
        return deptRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());

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

        Dept dept = dtoToEntity(deptDto);
        log.info("創建新部門：{}，位置：{}", dept.getDname(), dept.getLocation());
        return Optional.of(deptRepository.save(dept));
    }
//    查出一筆部門資料
    public Optional<DeptDto> findById(Integer deptNo) {
        return deptRepository.findById(deptNo)
                .map(this::entityToDto);
    }
//  更新一筆部門資料
    public Optional<DeptDto> updateDept(DeptDto deptDto) {
        // 檢查是否有其他部門使用了相同的部門名稱且部門編號不同
        Optional<Dept> existingDeptByName = deptRepository.findByDname(deptDto.getDname());
        if (existingDeptByName.isPresent() && !existingDeptByName.get().getDeptNo().equals(deptDto.getDeptNo())) {
            return Optional.empty(); // 如果名稱已被使用，且不是同一部門編號，則返回空
        }

        // 查找並更新現有的部門資料
        return deptRepository.findById(deptDto.getDeptNo())
                .map(existingDept -> {
                    existingDept.setDname(deptDto.getDname());
                    existingDept.setLocation(deptDto.getLocation());
                    return entityToDto(deptRepository.save(existingDept)); // 保存並返回更新後的部門
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
    private Dept dtoToEntity(DeptDto deptDto){
        Dept dept = new Dept();
        dept.setDeptNo(deptDto.getDeptNo());
        dept.setDname(deptDto.getDname());
        dept.setLocation(deptDto.getLocation());
        return dept;
    }
    private DeptDto entityToDto(Dept dept){
        DeptDto deptDto = new DeptDto();
        deptDto.setDeptNo(dept.getDeptNo());
        deptDto.setDname(dept.getDname());
        deptDto.setLocation(dept.getLocation());
        return deptDto;
    }
}
