package com.example.dto;

import java.util.Set;

import com.example.dto.CreateUser;
import com.example.entity.Role;

import lombok.Builder;

@Builder
public record CreateUser(
		String firstName,
		String lastName,
		Integer age,
		String username,
		String password,
		Set<Role> authorities
		) {	
	
	
}
