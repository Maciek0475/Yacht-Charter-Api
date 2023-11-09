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
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
	
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

}
