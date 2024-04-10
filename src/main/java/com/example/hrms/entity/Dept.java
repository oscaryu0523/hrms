package com.example.hrms.entity;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class Dept {
	private Integer deptNo;
	private String deptName;
	private String location;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
