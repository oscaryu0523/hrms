package com.example.hrms.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name="emp")
public class Emp {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="emp_no", nullable = false)
	private Integer empNo;
	@Column(name="ename", nullable = false)
	private String ename;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_no", nullable = false)
	@JsonBackReference
	private Dept dept; // 正確關聯到Dept類

	@Column(name="job", nullable = false)
	private String job;
	@Column(name="email", nullable = false)
	private String email;
	@Column(name="phone_number", nullable = false)
	private String phoneNumber;
	@Column(name="address", nullable = false)
	private String address;
	@Column(name="hiredate", nullable = false)
	private Date hiredate;
	@Column(name="sal", nullable = false)
	private Integer sal;
	@Column(name="created_at", nullable = false,updatable = false)
	private LocalDateTime createdAt;
	@Column(name="updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public Emp() {
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

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Emp{" +
				"empNo=" + empNo +
				", ename='" + ename + '\'' +
				", deptNo=" + (dept!=null? dept.getDeptNo():"null") +
				", job='" + job + '\'' +
				", email='" + email + '\'' +
				", phoneNumber=" + phoneNumber +
				", address='" + address + '\'' +
				", hiredate=" + hiredate +
				", sal=" + sal +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				'}';
	}
}
