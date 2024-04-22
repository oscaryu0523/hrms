package com.example.hrms.controller;


import com.example.hrms.dto.EmpDto;
import com.example.hrms.entity.Emp;
import com.example.hrms.search.EmpSearchKey;
import com.example.hrms.service.EmpService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;


import java.util.List;
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
                    return "redirect:/emp/search";
                }) // 如果存在且成功刪除，重導至員工列表頁
                .orElse("error"); // 如果無法刪除（例如員工不存在），則返回錯誤頁面
    }
    //  更新一筆員工資料，重導至員工列表
    @PostMapping("/update")
    public String updateEmp(Model model,@Valid @ModelAttribute("empDto") EmpDto empDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            model.addAttribute("empDto", empDto);
            model.addAttribute("depts",empService.getDepts());
            return "/emp/empEdit";
        }
        if(empService.updateEmp(empDto).isPresent()){
            return "redirect:/emp/search";
        }
        model.addAttribute("error","更新失敗或郵箱已被使用");
        return  "error";
    }

    //    進入編輯頁面  查詢一筆員工資料
    // 編輯員工資料
    @GetMapping("/edit/{empNo}")
    public String editEmp(@PathVariable Integer empNo, Model model) {
        return empService.findById(empNo)
                .map(empDto -> {//empService.findById(empNo)不為空的情況才執行
                    model.addAttribute("empDto", empDto);
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
    public String saveEmp(Model model, @Valid @ModelAttribute("empDto") EmpDto empDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for(ObjectError error : allErrors){
                System.out.println(error.getDefaultMessage());
            }
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("empDto", empDto);
            model.addAttribute("depts",empService.getDepts());
            System.out.println("這裡");
            return "/emp/empAdd";
        }
        return empService.saveEmp(empDto)
                .map( emp -> {
                    log.info("Created new employee with ID: {}", emp.getEmpNo());
                    System.out.println("那裏");
                    return "redirect:/emp/search";
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


    @GetMapping("/search")
    public String getOrSearchEmps(@ModelAttribute("key") EmpSearchKey key,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  Model model) {
        System.out.println("進入controller");
        // 如果前端沒有設定 size，則使用預設值 10
        int size = key.getSize() != null ? key.getSize() : 10;

        // 處理日期欄位的空值情況
        if (key.getStartDate() == null ) {
            key.setStartDate(null);
        }
        if (key.getEndDate() == null) {
            key.setEndDate(null);
        }
        System.out.println(key.getStartDate());
        System.out.println(key.getEndDate());


        System.out.println("查詢前");
        List<EmpDto> emps = empService.searchEmps(key, page, size);
        long totalItems = empService.countEmps(key);  // 獲取符合條件的總數量
        int totalPages = (int) Math.ceil((double) totalItems / size);
        System.out.println("查詢完畢");
        model.addAttribute("emps", emps);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("key", key);
        model.addAttribute("depts", empService.getDepts()); // 假設getDepts()提供所有部門列表

        return "emp/empList";
    }


}
