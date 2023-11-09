package com.mac2work.userpanel.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

	@Email
    @NotBlank(message = "email is obligatory")
	private String email;
	@Size(min = 8)
    @NotBlank(message = "password is obligatory")
	private String password;
	
}
