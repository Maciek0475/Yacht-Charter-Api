package com.mac2work.userpanel.service;

import org.springframework.stereotype.Service;

import com.mac2work.userpanel.exception.IncorrectUserException;
import com.mac2work.userpanel.exception.NoAccessException;
import com.mac2work.userpanel.exception.UserNotFoundException;
import com.mac2work.userpanel.model.Role;
import com.mac2work.userpanel.model.User;
import com.mac2work.userpanel.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
	
	private final JwtService jwtService;
	private final UserRepository userRepository;
	
	private User getLoggedInUser(String token) {
		String username = jwtService.extractUsername(token.substring(7));
		User user = userRepository.findByEmail(username).orElseThrow( () -> new UserNotFoundException("email", username));
		return user;
	}
	
	private boolean isAdmin(Long id) {
		User user = userRepository.findById(id).orElseThrow( () -> new UserNotFoundException("id", id));
		boolean isAdmin = user.getRole().equals(Role.ADMIN);
		return isAdmin;
	}
	
	public boolean isAdmin(String token, String path, String mappingMethod) {
		User user = getLoggedInUser(token);
		boolean isAdmin = user.getRole().equals(Role.ADMIN);
		if(!isAdmin)
			throw new NoAccessException(path, mappingMethod, Role.ADMIN);
		return isAdmin;
	}
	
	public boolean isCorrectUser(String token, Long id, String resource) {
		Long userId = getLoggedInUserId(token);
		if(isAdmin(userId))
		return true;
		boolean isCorrectUser = userId == id;
		if(!isCorrectUser)
			throw new IncorrectUserException(resource);
		return isCorrectUser;
		
	}

	public Long getLoggedInUserId(String token) {
		User user = getLoggedInUser(token);
		return user.getId();
	}

	
	
}
