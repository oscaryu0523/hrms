package com.example.hrms.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class User {
	private Integer userId;
	private String username;
	private String password;
	private Integer roleId;
	private LocalDate createdAt;
	private LocalDate updatedAt;
	
}
