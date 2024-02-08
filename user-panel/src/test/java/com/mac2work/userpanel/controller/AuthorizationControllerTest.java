package com.mac2work.userpanel.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.mac2work.userpanel.service.AuthorizationService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = AuthorizationController.class)
class AuthorizationControllerTest {

	@Autowired
	private MockMvc mockMvc;
		
	@MockBean
	private AuthorizationService authorizationService;
	
	private String token;
	private Long id;
	
	@BeforeEach
	void setUp() throws Exception {
		id = 1L;
		token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
	}

	@Test
	final void authorizationController_isAdmin_ReturnTrue() throws Exception {
		String path = "path";
		String mapping = "GET";
		when(authorizationService.isAdmin(token, path, mapping)).thenReturn(Boolean.TRUE);
		
		ResultActions response = mockMvc.perform(get("/user/authorization/is-admin/" + path + "/" + mapping)
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token));
		
		response.andExpect(status().isOk())
		.andExpect(jsonPath("$", CoreMatchers.is(Boolean.TRUE)));
	}

	@Test
	final void authorizationController_isCorrectUser_ReturnTrue() throws Exception {
		String resource = "resource";
		when(authorizationService.isCorrectUser(token, id, resource)).thenReturn(Boolean.TRUE);
		
		ResultActions response = mockMvc.perform(get("/user/authorization/is-correct-user/" + id + "/" + resource)
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token));
		
		response.andExpect(status().isOk())
		.andExpect(jsonPath("$", CoreMatchers.is(Boolean.TRUE)));
	}

	@Test
	final void authorizationController_getLoggedInUserId_ReturnId() throws Exception {
		when(authorizationService.getLoggedInUserId(token)).thenReturn(id);
		
		ResultActions response = mockMvc.perform(get("/user/authorization/id")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$", CoreMatchers.is(id.intValue())));
	}

}
