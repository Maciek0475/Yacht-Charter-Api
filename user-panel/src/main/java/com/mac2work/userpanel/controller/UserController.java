package com.mac2work.userpanel.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.userpanel.request.UserRequest;
import com.mac2work.userpanel.response.ApiResponse;
import com.mac2work.userpanel.response.UserResponse;
import com.mac2work.userpanel.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;

	@GetMapping
	public ResponseEntity<List<UserResponse>> getUsers(){
		List<UserResponse> users = userService.getUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
		UserResponse user = userService.getUserByid(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> UpdateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest){
		UserResponse user = userService.updateUser(id, userRequest);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
 	@DeleteMapping("/{id}")
 	public ResponseEntity<ApiResponse> DeleteUser(@PathVariable Long id){
 		ApiResponse apiResponse = userService.deleteUser(id);
 		return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
 	}
	
	

}
