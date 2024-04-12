package com.bike.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

	private int id;
	@Column(name = "name")
	@NotEmpty(message = "name field is required")
	@Size(min = 2, max = 20, message = "name length must be between 2 to 15 characters")
	private String name;
	@Column(name = "email")
	@NotEmpty(message = "email field is required")
	@Size(min = 2, max = 20, message = "email length must be between 2 to 20 characters")
	private String email;
	@Column(name = "phone")
	@NotEmpty(message = "phone field is required")
	@Size(min = 2, max = 20, message = "phone length must be between 2 to 10 characters")
	private String phone;
	@Column(name = "password")
	@NotEmpty(message = "password field is required")
	@Size(min = 2, max = 20, message = "password length must be between 2 to 20 characters")
	private String password;
	
	private String role;
}
