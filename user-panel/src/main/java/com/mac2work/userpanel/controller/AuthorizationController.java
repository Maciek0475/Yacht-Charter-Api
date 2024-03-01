package com.mac2work.userpanel.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/is-admin/{path}/{mappingMethod}")
	public boolean isAdmin(@RequestHeader (HttpHeaders.AUTHORIZATION) String token, @PathVariable String path, @PathVariable String mappingMethod) {
		return authorizationService.isAdmin(token, path, mappingMethod);
	}
	
	@GetMapping("/is-correct-user/{id}/{resource}")
	public boolean isCorrectUser(@RequestHeader (HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id, @PathVariable String resource) {
		return authorizationService.isCorrectUser(token, id ,resource);
	}
	
	@GetMapping("/id")
	public Long getLoggedInUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		return authorizationService.getLoggedInUserId(token);
	}
	
}
