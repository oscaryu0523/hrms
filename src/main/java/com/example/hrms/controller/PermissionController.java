package com.example.hrms.controller;

import com.example.hrms.dto.DeptDto;
import com.example.hrms.dto.PermissionDto;
import com.example.hrms.entity.Dept;
import com.example.hrms.entity.Permission;
import com.example.hrms.service.PermissionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    private static final Logger log= LoggerFactory.getLogger(PermissionController.class);
    @Autowired
    private PermissionService permissionService;
    //    權限總列表
    @GetMapping
    public String getPermissions(Model model) {
        model.addAttribute("permissions",permissionService.getPermissions());
        return "/permission/permissionList";
    }
    //    進入新增頁面
    @GetMapping("/add")
    public String addPermission(Model model){
        model.addAttribute("permissionDto", new PermissionDto());
        return "/permission/permissionAdd";
    }
    //    新增一筆權限資料  導回至權限列表
    @PostMapping("/save")
    public String savePermission(Model model,@Valid @ModelAttribute("permissionDto") PermissionDto permissionDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            model.addAttribute("permissionDto", permissionDto);
            return "/permission/permissionAdd";
        }
        return permissionService.savePermission(permissionDto)
                .map(permission -> {
                    log.info("Created new permission with ID: {}", permission.getPermissionId());
                    return "redirect:/permission";
                }).orElseGet(() -> {
                        model.addAttribute("error","該權限名稱已存在");
                        return "error";
                });
    }
//    進入編輯頁面
    @GetMapping("/edit/{permissionId}")
    public String editPermission(@PathVariable("permissionId")Integer permissionId, Model model){
        if (!model.containsAttribute("permissionDto")) {
            return permissionService.findPermissionById(permissionId)
                    .map(permissionDto -> {
                        model.addAttribute("permissionDto", permissionDto);
                        return "/permission/permissionEdit";
                    }).orElseGet(() -> {
                        log.warn("No permission found with ID: {}", permissionId);
                        model.addAttribute("error", "No Permission found.");
                        return "error";
                    });
        }
        return "/permission/permissionEdit";
    }
    //    更新一筆部門資料 導回部門列表頁面
    @PostMapping("/update")
    public String updatePermission(@Valid @ModelAttribute("permissionDto")PermissionDto permissionDto, BindingResult bindingResult, Model model){
//        如果驗證錯誤，反回編輯頁面
        if (bindingResult.hasErrors()) {
            model.addAttribute("permissionDto", permissionDto);
            return "/permission/permissionEdit";
        }
//        如果更新成功，導回權限列表
        if(permissionService.updatePermission(permissionDto).isPresent()){
            return "redirect:/permission";
        }else{
            model.addAttribute("error","更新失敗");
            return "error";
        }
    }
    //    刪除一筆權限資料，導回權限列表頁面
    @GetMapping("/delete/{permissionId}")
    public String deleteEmp(@PathVariable("permissionId") Integer permissionId) {
        return permissionService.deletePermission(permissionId)
                .map(success -> {
                    log.info("Deleted permission with ID: {}", permissionId);
                    return "redirect:/permission";
                })
                .orElse("error");
    }


}
