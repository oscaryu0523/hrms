package com.example.hrms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
public class Emp {
	private Integer empNo;
	private String name;
	private Integer deptNo;
	private String job;
	private String email;
	private LocalDateTime hiredate;
	private Integer sal;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
}
