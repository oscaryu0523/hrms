package com.example.hrms.controller.api;

import com.example.hrms.entity.Dept;
import com.example.hrms.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/dept")
public class ApiDeptController {
    @Autowired
    private DeptService deptService;
//    新增一筆部門資料
    @PostMapping
    public ResponseEntity<Dept> addDept(@RequestBody Dept dept){
        if(deptService.existsById(dept.getDeptNo())){
            System.out.println("該部門已存在");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }else{
            Dept saveDept=deptService.addDept(dept);
            return new ResponseEntity<>(saveDept, HttpStatus.CREATED);
        }
    }
//    刪除一筆部門資料
//    @DeleteMapping("/{deptNo}")
//    public ResponseEntity<Void> deleteDept(@PathVariable Integer deptNo){
//        if(!deptService.existsById(deptNo)){
//            System.out.println("該部門不存在");
//            return
//        }
//    }
//    修改一筆部門資料
//    部門列表
}
