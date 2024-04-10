package com.example.hrms.entity;


import java.time.LocalDateTime;

import lombok.Data;
@Data
public class Role {
	private Integer roleId;
	private String roleName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
