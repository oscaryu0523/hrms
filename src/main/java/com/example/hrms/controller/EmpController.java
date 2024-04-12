package com.example.hrms.controller;


import com.example.hrms.controller.api.ApiEmpController;
import com.example.hrms.dto.EmpDto;
import com.example.hrms.entity.Dept;
import com.example.hrms.entity.Emp;
import com.example.hrms.service.EmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/emp")
public class EmpController {

    private static final Logger log = LoggerFactory.getLogger(ApiEmpController.class);

    @Autowired
    private EmpService empService;

//    //    根據部門編號查詢員工列表
//    @GetMapping("/dept/{deptNo}")
//    public String getEmpsByDept(@PathVariable Integer deptNo, Model model) {
//        try {
//            List<Emp> emps = empService.getEmpsByDept(deptNo);
//            if (emps.isEmpty()) {
//                model.addAttribute("message", "該部門編號 " + deptNo + " 沒有找到員工。");
//                return "emp/noEmployees";  // 假設有一個noEmployees.html模板
//            }
//            model.addAttribute("emps", emps);
//            return "empList";
//        } catch (Exception e) {
//            log.error("發生意外錯誤", e);
//            model.addAttribute("errorMessage", "發生意外錯誤：" + e.getMessage());
//            return "error/generalError";
//        }
//    }
//
////    新增一筆員工資料
//    @PostMapping
//    public String addEmp(@RequestBody EmpDto empRequest, Model model){
//        try {
//            Emp savedEmp = empService.addEmp(empRequest).stream().findFirst().orElse(null);
//            return "redirect:/emp/empList.html";
//        }catch(Exception e){
//            log.error("");
//            return "redirect:/emp/empList.html";
//        }
//    }
////    更新一筆員工資料 進入員工列表
//    @PostMapping("/update/{empNo}")
//    public String updateEmp(@RequestBody EmpDto empDto, @PathVariable Integer empNo, Model model){
//        try {
//            Emp emp = empService.updateEmp(empDto, empNo).stream().findFirst().orElse(null);
//            return "redirect:/emp/empList";
//        }catch (Exception e){
//            log.error("");
//        }
//    }




    //    進入編輯頁面  查詢一筆員工資料
    @GetMapping("/edit/{empNo}")
    public String getEmp(@PathVariable Integer empNo, Model model) {
        try {
//        用員工編號查詢一筆員工物件，如果查得到返回物件，查不到返回null
            List<Dept> depts = empService.getDepts();
            Emp emp = empService.findById(empNo).orElse(null);
            if (emp == null) {
                log.warn("No employee found with ID: {}", empNo);
                return "emp/notFound";
            }
//        將物件存入model中
            model.addAttribute("emp", emp);
            model.addAttribute("depts",depts);
//        跳轉至detail頁面
            return "emp/empEdit";
        } catch (DataAccessException e) {
            log.error("Database access error while retrieving employee", e);
            return "error/databaseError";  // 假設有一個databaseError.html模板
        }
    }

//    進入新增頁面
    @GetMapping("/add")
    public String addEmp(Model model) {
        List<Dept> depts = empService.getDepts();
        model.addAttribute("depts",depts);
        EmpDto empDto = new EmpDto();
        model.addAttribute("empDto", empDto);
        return "emp/empAdd";
    }

    //    員工總列表 完成
    @GetMapping
    public String getEmps(Model model) {
        try {
            List<Emp> emps = empService.getEmps();
            List<Dept> depts = empService.getDepts();
            model.addAttribute("emps",emps);
            model.addAttribute("depts",depts);
            return "/emp/empList";
        }catch(Exception e){
            log.error("發生意外錯誤", e);
            model.addAttribute("errorMessage","發生意外錯誤"+e.getMessage());
        }
        return "error";
    }

}
