package com.mac2work.userpanel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.userpanel.request.AuthenticationRequest;
import com.mac2work.userpanel.request.RegisterRequest;
import com.mac2work.userpanel.response.AuthenticationResponse;
import com.mac2work.userpanel.service.AuthenticationService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
		return new ResponseEntity<>(authenticationService.register(registerRequest), HttpStatus.CREATED);
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
		return new ResponseEntity<>(authenticationService.authenticate(authenticationRequest), HttpStatus.OK);
	}

}
