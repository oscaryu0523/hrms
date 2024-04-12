package com.example.hrms.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "dept")
public class Dept {
	@Id
	@Column(name = "dept_no", nullable = false)
	private Integer deptNo;

	@Column(name = "dname", nullable = false)
	private String dname;

	@Column(name = "location", nullable = false)
	private String location;

	@Column(name = "createdAt", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "updatedAt", nullable = false)
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

	@OneToMany(mappedBy = "dept", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Emp> employees=new ArrayList<>();

	// Constructors, getters, and setters

	public Dept() {
	}

	public List<Emp> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Emp> employees) {
		this.employees = employees;
	}

	public Dept(Integer deptNo, String dname, String location, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.deptNo = deptNo;
		this.dname = dname;
		this.location = location;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Getters and Setters

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


}
