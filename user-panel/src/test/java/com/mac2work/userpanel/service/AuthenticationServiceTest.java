package com.mac2work.userpanel.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mac2work.userpanel.model.Role;
import com.mac2work.userpanel.model.User;
import com.mac2work.userpanel.repository.UserRepository;
import com.mac2work.userpanel.request.AuthenticationRequest;
import com.mac2work.userpanel.request.RegisterRequest;
import com.mac2work.userpanel.response.AuthenticationResponse;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
	
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private UserRepository userRepository;
	@Mock
	private JwtService jwtService;
	@Mock
	private AuthenticationManager authenticationManager;
	
	@InjectMocks
	private AuthenticationService authenticationService;
	
	private User user;
	private RegisterRequest registerRequest;
	private AuthenticationRequest authenticationRequest;
	private AuthenticationResponse authenticationResponse;
	private String token;

	@BeforeEach
	void setUp() throws Exception {
		user = User.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("maciekjurczak123@gmail.com")
				.password("P@ssword123")
				.role(Role.USER)
				.build();
		registerRequest = RegisterRequest.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("maciekjurczak123@gmail.com")
				.password("P@ssword123")
				.build();
		authenticationRequest = AuthenticationRequest.builder()
				.email("maciekjurczak123@gmail.com")
				.password("P@ssword123")
				.build();
		authenticationResponse = AuthenticationResponse.builder()
				.token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
				.build();
		token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
	}

	@Test
	final void authenticationService_register_ReturnAuthenticationResponse() {
		when(userRepository.save(user)).thenReturn(user);
		when(jwtService.generateToken(user)).thenReturn(token);
		when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
		
		AuthenticationResponse authenticationResponse =  authenticationService.register(registerRequest);
		
		assertThat(authenticationResponse).isEqualTo(this.authenticationResponse);
 	}

	@Test
	final void authenticationService_authenticate_ReturnAuthenticationResponse() {
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
		when(userRepository.findByEmail(user.getUsername())).thenReturn(Optional.of(user));
		when(jwtService.generateToken(user)).thenReturn(token);
		
		AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
		
		assertThat(authenticationResponse).isEqualTo(this.authenticationResponse);
	}

}
