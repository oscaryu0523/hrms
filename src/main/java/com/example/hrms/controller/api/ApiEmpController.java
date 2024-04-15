package com.example.hrms.controller.api;

import com.example.hrms.service.DeptService;
import com.example.hrms.service.EmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/emp")
public class ApiEmpController {

    private static final Logger log = LoggerFactory.getLogger(ApiEmpController.class);

    @Autowired
    private EmpService empService;

    @Autowired
    private DeptService deptService;
////    新增一筆員工資料
//    @PostMapping
//    public ResponseEntity<Emp> addEmp(@RequestBody Emp emp) {
//        if(!deptService.existsById(emp.getDept().getDeptNo())){
//            System.out.println("該部門資料不存在");
//            return ResponseEntity.notFound().build();
//        }
//        Emp savedEmp = empService.addEmp(emp);
//        return new ResponseEntity<>(savedEmp, HttpStatus.CREATED);
//    }
//    刪除一筆員工資料
//    @DeleteMapping("/{empNo}")
//    public ResponseEntity<Void> deleteEmp(@PathVariable Integer empNo) {
//        if(!empService.existsById(empNo)){
//            log.warn("該員工資料不存在{}",empNo);
//            return ResponseEntity.notFound().build();
//        }
//        empService.deleteEmp(empNo);
//        return ResponseEntity.noContent().build();
//    }
////    更新一筆員工資料
//    @PutMapping("")
//    public ResponseEntity<Emp> updateEmp(@RequestBody Emp emp) {
//        Emp updatedEmp = empService.updateEmp(emp);
//        return new ResponseEntity<>(updatedEmp, HttpStatus.OK);
//    }
////    查詢一筆員工資料
//    @GetMapping("/{empNo}")
//    public ResponseEntity<Emp> getEmp2(@PathVariable Integer empNo) {
//        return empService.findById(empNo).stream()
//                .map(emp -> new ResponseEntity<>(emp, HttpStatus.OK))
//                .findFirst()
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//
//    }


//    查詢員工資料列表
//    @GetMapping("/dept/{deptNo}")
//    public ResponseEntity<List<Emp>> getEmpsByDept(@PathVariable Integer deptNo) {
//        List<Emp> emps = empService.getEmpsByDept(deptNo);
//        return new ResponseEntity<>(emps, HttpStatus.OK);
//    }
}
