package com.mac2work.userpanel.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac2work.userpanel.model.Role;
import com.mac2work.userpanel.request.UserRequest;
import com.mac2work.userpanel.response.ApiResponse;
import com.mac2work.userpanel.response.UserResponse;
import com.mac2work.userpanel.service.UserService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private UserService userService;
	
	private UserRequest userRequest;
	private UserResponse userResponse;
	private UserResponse userResponse2;
	private ApiResponse apiResponse;
	private String token;
	
	private Long id;

	@BeforeEach
	void setUp() throws Exception {
		id = 1L;
		
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
	final void userController_getUsers_ReturnListOfUserResponses() throws Exception {
		List<UserResponse> userResponses = List.of(userResponse, userResponse2);
		when(userService.getUsers(token)).thenReturn(userResponses);
		
		ResultActions response = mockMvc.perform(get("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", CoreMatchers.is(userResponses.size())));
	}

	@Test
	final void userController_getUserById_ReturnUserResponse() throws Exception {
		when(userService.getUserByid(token, id)).thenReturn(userResponse);
		
		ResultActions response = mockMvc.perform(get("/users/"+id)
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", CoreMatchers.is(userResponse.getFirstName())))
				.andExpect(jsonPath("$.lastName", CoreMatchers.is(userResponse.getLastName())))
				.andExpect(jsonPath("$.email", CoreMatchers.is(userResponse.getEmail())))
				.andExpect(jsonPath("$.role", CoreMatchers.is(userResponse.getRole().toString())));
	}

	@Test
	final void userController_updateUser_ReturnUserResponse() throws Exception {
		when(userService.updateUser(token, id, userRequest)).thenReturn(userResponse);
		
		ResultActions response = mockMvc.perform(put("/users/"+id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userRequest))
				.header("Authorization", token));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", CoreMatchers.is(userResponse.getFirstName())))
				.andExpect(jsonPath("$.lastName", CoreMatchers.is(userResponse.getLastName())))
				.andExpect(jsonPath("$.email", CoreMatchers.is(userResponse.getEmail())))
				.andExpect(jsonPath("$.role", CoreMatchers.is(userResponse.getRole().toString())));
	}

	@Test
	final void userController_deleteUser_ReturnApiResponse() throws Exception {
		when(userService.deleteUser(token, id)).thenReturn(apiResponse);
		
		ResultActions response = mockMvc.perform(delete("/users/"+id)
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token));
		
		response.andExpect(status().isNoContent())
				.andExpect(jsonPath("$.isSuccess", CoreMatchers.is(apiResponse.getIsSuccess())))
				.andExpect(jsonPath("$.message", CoreMatchers.is(apiResponse.getMessage())))
				.andExpect(jsonPath("$.httpStatus", CoreMatchers.is(apiResponse.getHttpStatus().name())));
	}

}
