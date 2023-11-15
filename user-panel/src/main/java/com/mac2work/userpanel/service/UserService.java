package com.mac2work.userpanel.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mac2work.userpanel.exception.UserNotFoundException;
import com.mac2work.userpanel.model.User;
import com.mac2work.userpanel.repository.UserRepository;
import com.mac2work.userpanel.request.UserRequest;
import com.mac2work.userpanel.response.ApiResponse;
import com.mac2work.userpanel.response.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
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
		User user = userRepository.findById(id).orElseThrow( () -> new UserNotFoundException("id", id));
		return mapToUserResponse(user);
	}

	public UserResponse updateUser(Long id, UserRequest userRequest) {
		User user = userRepository.findById(id).orElseThrow( () -> new UserNotFoundException("id", id));
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setEmail(userRequest.getEmail());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setRole(userRequest.getRole());
		userRepository.save(user);
		
		User updatedUser = userRepository.findById(id).orElseThrow( () -> new UserNotFoundException("id", id));
		return mapToUserResponse(updatedUser);
	}

	public ApiResponse deleteUser(Long id) {
		User user = userRepository.findById(id).orElseThrow( () -> new UserNotFoundException("id", id));
		userRepository.delete(user);

		return ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.message("User successfully deleted")
				.httpStatus(HttpStatus.NO_CONTENT)
				.build();
	}

	

}
