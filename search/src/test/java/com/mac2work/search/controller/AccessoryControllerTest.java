package com.mac2work.search.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.mac2work.search.request.AccessoryRequest;
import com.mac2work.search.response.AccessoryResponse;
import com.mac2work.search.response.ApiResponse;
import com.mac2work.search.service.AccessoryService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = AccessoryController.class)
class AccessoryControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private AccessoryService accessoryService;
	
	private AccessoryRequest accessoryRequest;
	private AccessoryResponse accessoryResponse;
	private AccessoryResponse accessoryResponse2;
	private ApiResponse apiResponse;
	
	Long id;

	@BeforeEach
	void setUp() throws Exception {
		id = 1L;
		
		accessoryRequest = AccessoryRequest.builder()
				.name("tent")
				.build();
		accessoryResponse = AccessoryResponse.builder()
				.name("tent")
				.build();
		accessoryResponse2 = AccessoryResponse.builder()
				.name("sink")
				.build();
		apiResponse = ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.message("Accessory delted successfully")
				.httpStatus(HttpStatus.NO_CONTENT)
				.build();
	}

	@Test
	final void accessoryController_getAccessories_ReturnListOfAccessoryResponses() throws Exception {
		List<AccessoryResponse> accessoryResponses = List.of(accessoryResponse, accessoryResponse2);
		when(accessoryService.getAccessories()).thenReturn(accessoryResponses);
		
		ResultActions response = mockMvc.perform(get("/search/accessory/")
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", CoreMatchers.is(accessoryResponses.size())));
	}
	
	@Test
	final void accessoryController_getAccessoryById_ReturnAccessoryResponse() throws Exception {
		when(accessoryService.getAccessoryById(id)).thenReturn(accessoryResponse);
		
		ResultActions response = mockMvc.perform(get("/search/accessory/"+id)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", CoreMatchers.is(accessoryResponse.getName())));
	}

	@Test
	final void accessoryController_addAccessory_ReturnAccessoryResponse() throws Exception {
		when(accessoryService.addAccessory(accessoryRequest)).thenReturn(accessoryResponse);
		
		ResultActions response = mockMvc.perform(post("/search/accessory/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(accessoryRequest)));
		
		response.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", CoreMatchers.is(accessoryResponse.getName())));
	}

	@Test
	final void accessoryController_updateAccessory_ReturnAccessoryResponse() throws Exception {
		Long id = 2L;
		when(accessoryService.updateAccessory(accessoryRequest, id)).thenReturn(accessoryResponse);
		
		ResultActions response = mockMvc.perform(put("/search/accessory/"+id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(accessoryRequest)));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", CoreMatchers.is(accessoryResponse.getName())));
	}

	@Test
	final void accessoryController_deleteAccessory_ReturnApiResponse() throws Exception {
		when(accessoryService.deleteAccessory(id)).thenReturn(apiResponse);
		
		ResultActions response = mockMvc.perform(delete("/search/accessory/"+id)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isNoContent())
				.andExpect(jsonPath("$.isSuccess", CoreMatchers.is(apiResponse.getIsSuccess())))
				.andExpect(jsonPath("$.message", CoreMatchers.is(apiResponse.getMessage())))
				.andExpect(jsonPath("$.httpStatus", CoreMatchers.is(apiResponse.getHttpStatus().name())));
	}

}
