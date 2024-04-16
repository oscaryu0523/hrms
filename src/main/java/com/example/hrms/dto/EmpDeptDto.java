package com.example.hrms.dto;

import java.sql.Date;

public class EmpDeptDto {
    private Integer empNo;
    private String ename;
    private Integer deptNo;
    private String job;
    private String email;
    private String phoneNumber;
    private String address;
    private Date hiredate;
    private Integer sal;
    private String dname;
    private String location;

    public EmpDeptDto(Integer empNo, String ename, Integer deptNo, String job, String email, String phoneNumber, String address, Date hiredate, Integer sal, String dname, String location) {
        this.empNo = empNo;
        this.ename = ename;
        this.deptNo = deptNo;
        this.job = job;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.hiredate = hiredate;
        this.sal = sal;
        this.dname = dname;
        this.location = location;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Integer getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(Integer deptNo) {
        this.deptNo = deptNo;
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

    public Integer getSal() {
        return sal;
    }

    public void setSal(Integer sal) {
        this.sal = sal;
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
