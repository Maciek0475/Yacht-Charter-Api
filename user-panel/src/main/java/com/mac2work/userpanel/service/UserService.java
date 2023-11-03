package com.mac2work.userpanel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mac2work.userpanel.model.User;
import com.mac2work.userpanel.repository.UserRepository;
import com.mac2work.userpanel.response.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder()
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.role(user.getRole())
				.build();
	}

	public List<UserResponse> getUsers() {
		return userRepository.findAll().stream()
				.map( user -> mapToUserResponse(user)).toList();
	}

	public UserResponse getUserByid(Long id) {
		User user = userRepository.findById(id).orElseThrow();
		return mapToUserResponse(user);
	}

	

}
