package com.example.hrms.dto;

import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

public class EmpDto {
    @NotNull
    private String ename;
    @NotNull
    private Integer deptNo; // 正確關聯到Dept類
    @NotNull
    private String job;
    @NotNull
    private String email;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String address;
    @NotNull
    private LocalDateTime hiredate;

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

    public LocalDateTime getHiredate() {
        return hiredate;
    }

    public void setHiredate(LocalDateTime hiredate) {
        this.hiredate = hiredate;
    }

    @NotNull
    private Integer sal;

}
