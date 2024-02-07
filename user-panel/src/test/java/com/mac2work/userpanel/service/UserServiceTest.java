package com.mac2work.userpanel.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mac2work.userpanel.model.Role;
import com.mac2work.userpanel.model.User;
import com.mac2work.userpanel.repository.UserRepository;
import com.mac2work.userpanel.request.UserRequest;
import com.mac2work.userpanel.response.ApiResponse;
import com.mac2work.userpanel.response.UserResponse;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private AuthorizationService authorizationService;
	
	@InjectMocks
	private UserService userService;
	
	private User user;
	private User user2;
	private UserRequest userRequest;
	private UserResponse userResponse;
	private UserResponse userResponse2;
	private ApiResponse apiResponse;
	private String token;
	
	private Long id;

	@BeforeEach
	void setUp() throws Exception {
		
		id = 1L;
		
		user = User.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("maciekjurczak123@gmail.com")
				.password("P@ssword123")
				.role(Role.ADMIN)
				.build();
		user2 = User.builder()
				.firstName("Maciej")
				.lastName("Kowalski")
				.email("maciekkowalski123@gmail.com")
				.password("P@ssword123")
				.role(Role.ADMIN)
				.build();
		userRequest = UserRequest.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("maciekjurczak123@gmail.com")
				.password("P@ssword123")
				.role(Role.ADMIN)
				.build();
		userResponse = UserResponse.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("maciekjurczak123@gmail.com")
				.role(Role.ADMIN)
				.build();
		userResponse2 = UserResponse.builder()
				.firstName("Maciej")
				.lastName("Kowalski")
				.email("maciekkowalski123@gmail.com")
				.role(Role.ADMIN)
				.build();
		apiResponse = ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.message("User successfully deleted")
				.httpStatus(HttpStatus.NO_CONTENT)
				.build();
		token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
	}

	@Test
	final void userService_getUsers_ReturnListOfUserResponses() {
		List<User> users = List.of(user, user2);
		List<UserResponse> expectedUserResponses = List.of(userResponse, userResponse2);
		when(authorizationService.isAdmin(token, "/users", "GET")).thenReturn(Boolean.TRUE);
		when(userRepository.findAll()).thenReturn(users);
		
		List<UserResponse> actualUserResponses = userService.getUsers(token);
		
		assertThat(actualUserResponses).isEqualTo(expectedUserResponses);
	}

	@Test
	final void userService_getUserByid_ReturnUserResponse() {
		when(authorizationService.isCorrectUser(token, id, "user")).thenReturn(Boolean.TRUE);
		when(userRepository.findById(id)).thenReturn(Optional.of(user));
		
		UserResponse userResponse = userService.getUserByid(token, id);
		
		assertThat(userResponse).isEqualTo(this.userResponse);
	}

	@Test
	final void userService_updateUser_ReturnUserResponse() {
		id = 2L;
		when(authorizationService.isAdmin(token, "/users", "PUT")).thenReturn(Boolean.TRUE);
		when(userRepository.findById(id)).thenReturn(Optional.of(user2));
		when(userRepository.save(user)).thenReturn(user);
		when(passwordEncoder.encode(userRequest.getPassword())).thenReturn(user.getPassword());
		
		UserResponse userResponse = userService.updateUser(token, id, userRequest);
		
		assertThat(userResponse).isEqualTo(this.userResponse);	}

	@Test
	final void userService_deleteUser_ReturnApiResponse() {
		when(authorizationService.isAdmin(token, "/users", "DELETE")).thenReturn(Boolean.TRUE);
		when(userRepository.findById(id)).thenReturn(Optional.of(user));
		doNothing().when(userRepository).delete(user);
		
		ApiResponse apiResponse = userService.deleteUser(token, id);
		
		assertThat(apiResponse).isEqualTo(this.apiResponse);
	}

}
