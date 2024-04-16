package com.example.hrms.dto;


import jakarta.validation.constraints.*;
import java.sql.Date;



public class EmpDto {
    private Integer empNo;
    @NotBlank(message = "員工名稱不能為空")
    @Size(max=20, message="員工名稱的長度必須介於1到20之間")
    private String ename;
    @NotNull
    private Integer deptNo; // 正確關聯到Dept類

    private String dname;
    @Size(max=20, message="職稱的長度必須介於1到20之間")
    @NotBlank(message = "職稱名稱不能為空")
    private String job;
    @Email(message = "email格式有誤")
    @NotBlank(message = "信箱名稱不能為空")
    private String email;
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "無效的電話號碼格式")
    @NotBlank(message = "電話號碼不能為空")
    private String phoneNumber;
    @NotBlank(message = "地址名稱不能為空")
    @Size(max=20, message="地址名稱的長度必須介於1到40之間")
    private String address;
    @PastOrPresent(message = "雇用日期不能是未來的日期")
    private Date hiredate;
    @Max(value = 1000000,message = "不能超過1000000")
    @NotNull(message = "薪水不能為空")
    private Integer sal;

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public Integer getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(Integer deptNo) {
        this.deptNo = deptNo;
    }

    public Integer getSal() {
        return sal;
    }

    public void setSal(Integer sal) {
        this.sal = sal;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }



    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }


}
