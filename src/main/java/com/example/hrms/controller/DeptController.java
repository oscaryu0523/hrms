package com.example.hrms.controller;

import com.example.hrms.dto.DeptDto;
import com.example.hrms.entity.Dept;
import com.example.hrms.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dept")
public class DeptController {
    private static final Logger log= LoggerFactory.getLogger(DeptController.class);
    @Autowired
    private DeptService deptService;

//    部門總列表
    @GetMapping
    public String getDepts(Model model) {
        model.addAttribute("depts",deptService.getDepts());
        return "/dept/deptList";
    }
//    進入新增頁面
    @GetMapping("/add")
    public String addDept(Model model){
        model.addAttribute("deptDto", new DeptDto());
        return "/dept/deptAdd";
    }
//    新增一筆部門資料  跳轉至部門列表
    @PostMapping("/save")
    public String saveDept(@ModelAttribute("deptDto") DeptDto deptDto, Model model){
        return deptService.saveDept(deptDto)
                .map(dept -> {
                    log.info("Created new dept with ID: {}", dept.getDeptNo());
                    return "redirect:/dept";
                })
                .orElseGet(() -> {
                    model.addAttribute("error","存在重複的部門編號或部門名稱，請檢查");
                    return "error";
                });
    }
//    進入編輯頁面
    @GetMapping("/edit/{deptNo}")
    public String editDept(@PathVariable Integer deptNo, Model model){
        return deptService.findById(deptNo)
                .map(dept -> {
                    model.addAttribute("dept", dept);
                    return "dept/deptEdit";
                })
                .orElseGet(() -> {
                    log.warn("No dept found with ID: {}", deptNo);
                    model.addAttribute("error", "No dept found.");
                    return "error";
                });
    }
//    更新一筆部門資料 導回部門列表頁面
    @PostMapping("/update")
    public String updateDept(@ModelAttribute("deptDto") Dept dept, Model model){
        return deptService.updateDept(dept)
                .map(dept2 -> "redirect:/dept")
                .orElseGet(() -> {
                    model.addAttribute("error","存在重複的部門名稱，請檢查");
                    return "error";
                });
    }

    //    刪除一筆部門資料，導回部門列表頁面
    @GetMapping("/delete/{deptNo}")
    public String deleteEmp(@PathVariable("deptNo") Integer deptNo) {
        return deptService.deleteDept(deptNo)
                .map(e -> {
                    log.info("Deleted employee with ID: {}", deptNo);
                    return "redirect:/dept";
                })
                .orElse("error");
    }
}
