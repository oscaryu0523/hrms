package com.example.hrms.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id", nullable = false)
	private Integer userId;

	@Column(name="username", nullable = false)
	private String username;

	@Column(name="password", nullable = false)
	private String password;

	@Column(name="created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name="updated_at", nullable = false)
	private LocalDateTime updatedAt;

	public Set<UserPermission> getUserPermissions() {
		return userPermissions;
	}

	public void setUserPermissions(Set<UserPermission> userPermissions) {
		this.userPermissions = userPermissions;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<UserPermission> userPermissions = new HashSet<>();

	@PrePersist
	protected void onCreate() {
		updatedAt = createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public User() {
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		return "User{" +
				"userId=" + userId +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				'}';
	}
}
