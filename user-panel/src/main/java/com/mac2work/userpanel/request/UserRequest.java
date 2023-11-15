package com.mac2work.userpanel.request;

import com.mac2work.userpanel.model.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
	
	@Size(max = 45)
    @NotBlank(message = "firstName is obligatory")
	private String firstName;
	@Size(max = 45)
    @NotBlank(message = "lastName is obligatory")
	private String lastName;
	@Email
    @NotBlank(message = "email is obligatory")
	private String email;
	@Size(min = 8)
    @NotBlank(message = "password is obligatory")
	private String password;
    @NotNull(message = "role is obligatory")
	@Enumerated(EnumType.STRING)
	private Role role;

}
