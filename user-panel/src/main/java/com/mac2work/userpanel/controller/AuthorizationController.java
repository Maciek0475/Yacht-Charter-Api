package com.mac2work.userpanel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.userpanel.service.AuthorizationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/authorization")
public class AuthorizationController {

	private final AuthorizationService authorizationService;
	
	@GetMapping("/is-admin")
	public boolean isAdmin(@RequestHeader (name="Authorization") String token, String path, String mappingMethod) {
		return authorizationService.isAdmin(token, path, mappingMethod);
	}
	
	@GetMapping("/is-correct-user")
	public boolean isCorrectUser(@RequestHeader (name="Authorization") String token, Long id, String resource) {
		return authorizationService.isCorrectUser(token, id ,resource);
	}
	
	@GetMapping("/id")
	public Long getLoggedInUserId(@RequestHeader (name="Authorization") String token) {
		return authorizationService.getLoggedInUserId(token);
	}
	
}
