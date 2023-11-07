package com.mac2work.userpanel.response;

import com.mac2work.userpanel.model.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
	
	private String firstName;
	private String lastName;
	private String email;
	@Enumerated(EnumType.STRING)
	private Role role;

}
