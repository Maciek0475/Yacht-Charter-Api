package com.mac2work.userpanel.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mac2work.userpanel.exception.UserNotFoundException;
import com.mac2work.userpanel.model.Role;
import com.mac2work.userpanel.model.User;
import com.mac2work.userpanel.repository.UserRepository;
import com.mac2work.userpanel.request.AuthenticationRequest;
import com.mac2work.userpanel.request.RegisterRequest;
import com.mac2work.userpanel.response.AuthenticationResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {
		User user = User.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();
		userRepository.save(user);
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(), 
						request.getPassword()
						)
				);
		User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("email", request.getEmail()));
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}
	

	
}
