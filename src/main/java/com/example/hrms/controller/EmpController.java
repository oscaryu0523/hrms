package com.example.hrms.controller;


import com.example.hrms.dto.EmpDto;
import com.example.hrms.entity.Emp;
import com.example.hrms.service.EmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@Controller
@RequestMapping("/emp")
public class EmpController {

    private static final Logger log = LoggerFactory.getLogger(EmpController.class);

    @Autowired
    private EmpService empService;

//    刪除一筆員工資料，重導至員工列表
    @GetMapping("/delete/{empNo}")
    public String deleteEmp(@PathVariable("empNo") Integer empNo) {
        return empService.deleteEmp(empNo)
                .map(e -> {
                    log.info("Deleted employee with ID: {}", empNo);
                    return "redirect:/emp";
                }) // 如果存在且成功刪除，重導至員工列表頁
                .orElse("error"); // 如果無法刪除（例如員工不存在），則返回錯誤頁面
    }
    //  更新一筆員工資料，重導至員工列表
    @PostMapping("/update")
    public String updateEmp(@ModelAttribute("empDto") Emp emp, Model model){
        if(empService.updateEmp(emp).isPresent()){
            return "redirect:/emp";
        }
        model.addAttribute("error","更新失敗或郵箱已被使用");
        return  "error";
    }

    //    進入編輯頁面  查詢一筆員工資料
    // 編輯員工資料
    @GetMapping("/edit/{empNo}")
    public String editEmp(@PathVariable Integer empNo, Model model) {
        return empService.findById(empNo)
                .map(emp -> {//empService.findById(empNo)不為空的情況才執行
                    model.addAttribute("emp", emp);
                    model.addAttribute("depts", empService.getDepts());
                    return "emp/empEdit";
                })
                .orElseGet(() -> {//empService.findById(empNo)為空的情況執行
                    log.warn("No employee found with ID: {}", empNo);
                    model.addAttribute("error", "No employee found.");
                    return "error";
                });
    }


//  新增一筆員工資料，重導至員工列表
    @PostMapping("/save")
    public String saveEmp(@ModelAttribute("empDto") EmpDto empDto, Model model) {
        return empService.saveEmp(empDto)
                .map( emp -> {
                    log.info("Created new employee with ID: {}", emp.getEmpNo());
                    return "redirect:/emp";
                })
                .orElseGet(() -> {
                    model.addAttribute("error","存在重複的電子郵件或部門不存在，請檢查");
                    return "redirect:/error";
                });

    }


//    進入新增頁面
    @GetMapping("/add")
    public String addEmp(Model model) {
        model.addAttribute("depts",empService.getDepts());
        model.addAttribute("empDto", new EmpDto());
        return "emp/empAdd";
    }

    //    員工總列表 完成
    @GetMapping
    public String getEmps(Model model) {

            model.addAttribute("emps",empService.getEmps());
            model.addAttribute("depts",empService.getDepts());
            return "/emp/empList";
    }

    //    員工複合查詢  員工列表 完成
    @PostMapping("/search")
    public String searchEmps(@RequestParam("deptNo") Optional<Integer> deptNo,
                             @RequestParam("keyword") Optional<String> keyword,
                             Model model) {
        try {
            model.addAttribute("emps",empService.searchEmps(deptNo, keyword));
            model.addAttribute("depts",empService.getDepts());
            return "/emp/empList";
        }catch(Exception e){
            log.error("發生意外錯誤", e);
            model.addAttribute("errorMessage","發生意外錯誤"+e.getMessage());
        }
        return "emp/error";
    }

}
