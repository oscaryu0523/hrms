package com.example.hrms.dto;

import jakarta.validation.constraints.*;

public class DeptDto {
    @NotNull
    @Min(10)
    @Max(9999)
    private Integer deptNo;
    @NotBlank(message = "部門名稱不能為空")
    @Size(max=15,message="長度不能大於15")
    private String dname;

    @NotBlank(message = "地址名稱不能為空")
    @Size(max=30,message="長度不能大於30")
    private String location;

    public DeptDto() {
    }

    public Integer getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(Integer deptNo) {
        this.deptNo = deptNo;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
