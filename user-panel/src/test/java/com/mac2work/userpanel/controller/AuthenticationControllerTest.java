package com.mac2work.userpanel.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac2work.userpanel.request.AuthenticationRequest;
import com.mac2work.userpanel.request.RegisterRequest;
import com.mac2work.userpanel.response.AuthenticationResponse;
import com.mac2work.userpanel.service.AuthenticationService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = AuthenticationController.class)
class AuthenticationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AuthenticationService authenticationService;
	
	private RegisterRequest registerRequest;
	private AuthenticationRequest authenticationRequest;
	private AuthenticationResponse authenticationResponse;
	
	@BeforeEach
	void setUp() throws Exception {
		registerRequest = RegisterRequest.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("maciekjurczak123@gmail.com")
				.password("P@ssword123")
				.build();
		authenticationRequest = AuthenticationRequest.builder()
				.email("maciekjurczak123@gmail.com")
				.password("P@ssword123")
				.build();
		authenticationResponse = AuthenticationResponse.builder()
				.token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
				.build();
	}

	@Test
	final void authenticationController_register_ReturnAuthenticationResponse() throws Exception {
		when(authenticationService.register(registerRequest)).thenReturn(authenticationResponse);
		
		ResultActions response = mockMvc.perform(post("/user/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerRequest)));
		
		response.andExpect(status().isCreated())
				.andExpect(jsonPath("$.token", CoreMatchers.is(authenticationResponse.getToken())));
	}

	@Test
	final void authenticationController_authenticate_ReturnAuthenticationResponse() throws Exception {
		when(authenticationService.authenticate(authenticationRequest)).thenReturn(authenticationResponse);
		
		ResultActions response = mockMvc.perform(post("/user/auth/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(authenticationRequest)));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.token", CoreMatchers.is(authenticationResponse.getToken())));	}

}
