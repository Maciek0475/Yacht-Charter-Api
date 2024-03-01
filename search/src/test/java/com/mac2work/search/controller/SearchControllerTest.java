package com.mac2work.search.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac2work.search.model.Accessory;
import com.mac2work.search.model.Propulsion;
import com.mac2work.search.request.YachtRequest;
import com.mac2work.search.response.AccessoryResponse;
import com.mac2work.search.response.ApiResponse;
import com.mac2work.search.response.PriceResponse;
import com.mac2work.search.response.YachtResponse;
import com.mac2work.search.service.SearchService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = SearchController.class)
class SearchControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired 
	private ObjectMapper objectMapper;
	
	@MockBean
	private SearchService searchService;
	
	private YachtRequest yachtRequest;
	private YachtResponse yachtResponse;
	private YachtResponse yachtResponse2;
	private AccessoryResponse accessoryResponse;
	private AccessoryResponse accessoryResponse2;
	private Accessory accessory;
	private Accessory accessory2;
	private PriceResponse priceResponse;
	private ApiResponse apiResponse;
	
	private Long id;

	@BeforeEach
	void setUp() throws Exception {
		id = 1L;

		accessoryResponse = AccessoryResponse.builder()
				.name("tent")
				.build();
		accessoryResponse2 = AccessoryResponse.builder()
				.name("sink")
				.build();
		accessory = Accessory.builder()
				.id(1L)
				.name("tent")
				.build();
		accessory2 = Accessory.builder()
				.id(2L)
				.name("sink")
				.build();
		yachtRequest = YachtRequest.builder()
				.model("Sasanka")
				.propulsion(Propulsion.SAILING)
				.length(6.60)
				.capacity(5)
				.motorPower(8.0)
				.priceFrom(150.0)
				.accessories(List.of(accessory, accessory2))
				.build();
		yachtResponse = YachtResponse.builder()
				.model("Sasanka")
				.propulsion(Propulsion.SAILING)
				.length(6.60)
				.capacity(5)
				.motorPower(8.0)
				.priceFrom(150.0)
				.accessories(List.of(accessoryResponse, accessoryResponse2))
				.build();
		yachtResponse2 = YachtResponse
				.builder()
				.model("Orion")
				.propulsion(Propulsion.SAILING)
				.length(5.93)
				.capacity(5)
				.motorPower(5.0)
				.priceFrom(130.0)
				.accessories(List.of(accessoryResponse, accessoryResponse2))
				.build();
		priceResponse = PriceResponse.builder()
				.yachtModel("Sasanka")
				.days(10)
				.price(1722.5)
				.build();
		apiResponse = ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.message("Yacht deleted successfully")
				.httpStatus(HttpStatus.OK)
				.build();
	}

	@Test
	final void searchController_getYachts_ReturnListOfYachtResponses() throws Exception {
		List<YachtResponse> yachtResponses = List.of(yachtResponse, yachtResponse2);
		when(searchService.getYachts()).thenReturn(yachtResponses);
		
		ResultActions response = mockMvc.perform(get("/search")
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", CoreMatchers.is(yachtResponses.size())));
	}

	@Test
	final void searchController_getMotorYachts_ReturnListOfYachtResponses() throws Exception {
		yachtResponse.setPropulsion(Propulsion.MOTOR);
		yachtResponse2.setPropulsion(Propulsion.MOTOR);
		List<YachtResponse> yachtResponses = List.of(yachtResponse, yachtResponse2);
		when(searchService.getYachtsByPropulsion(Propulsion.MOTOR)).thenReturn(yachtResponses);
	
		ResultActions response = mockMvc.perform(get("/search/motor")
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", CoreMatchers.is(yachtResponses.size())));

	}

	@Test
	final void searchController_getSailingYachts_ReturnListOfYachtResponses() throws Exception {
		List<YachtResponse> yachtResponses = List.of(yachtResponse, yachtResponse2);
		when(searchService.getYachtsByPropulsion(Propulsion.SAILING)).thenReturn(yachtResponses);
	
		ResultActions response = mockMvc.perform(get("/search/sailing")
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", CoreMatchers.is(yachtResponses.size())));
	}

	@Test
	final void searchController_getYachtById_ReturnYachtResponse() throws Exception {
		when(searchService.getYachtById(id)).thenReturn(yachtResponse);	
	
		ResultActions response = mockMvc.perform(get("/search/"+id)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.model", CoreMatchers.is(yachtResponse.getModel())))
				.andExpect(jsonPath("$.propulsion", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(yachtResponse.getPropulsion()), Object.class))))
				.andExpect(jsonPath("$.length", CoreMatchers.is(yachtResponse.getLength())))
				.andExpect(jsonPath("$.capacity", CoreMatchers.is(yachtResponse.getCapacity())))
				.andExpect(jsonPath("$.motorPower", CoreMatchers.is(yachtResponse.getMotorPower())))
				.andExpect(jsonPath("$.priceFrom", CoreMatchers.is(yachtResponse.getPriceFrom())))
				.andExpect(jsonPath("$.accessories", CoreMatchers.is(yachtResponse.getAccessories().stream().map(accessory -> {
					try {
						return objectMapper.readValue(objectMapper.writeValueAsString(accessory), Object.class);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
					return accessory;
				}).toList())));
	}

	@Test
	final void searchController_getYachtPrice_ReturnPriceResponse() throws Exception {
		LocalDate from = LocalDate.of(2024, 4, 15);
		LocalDate to = LocalDate.of(2024, 4, 25);
		when(searchService.getPrice(id, from, to)).thenReturn(priceResponse);	
	
		ResultActions response = mockMvc.perform(get("/search/"+id+"/price/"+from+"/"+to)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.yachtModel", CoreMatchers.is(priceResponse.getYachtModel())))
				.andExpect(jsonPath("$.days", CoreMatchers.is(priceResponse.getDays())))
				.andExpect(jsonPath("$.price", CoreMatchers.is(priceResponse.getPrice())));

	}

	@Test
	final void searchController_addYacht_ReturnYachtResponse() throws Exception {
		when(searchService.addYacht(yachtRequest)).thenReturn(yachtResponse);	
	
		ResultActions response = mockMvc.perform(post("/search")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(yachtRequest)));
		
		response.andExpect(status().isCreated())
		.andExpect(jsonPath("$.model", CoreMatchers.is(yachtResponse.getModel())))
		.andExpect(jsonPath("$.propulsion", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(yachtResponse.getPropulsion()), Object.class))))
		.andExpect(jsonPath("$.length", CoreMatchers.is(yachtResponse.getLength())))
		.andExpect(jsonPath("$.capacity", CoreMatchers.is(yachtResponse.getCapacity())))
		.andExpect(jsonPath("$.motorPower", CoreMatchers.is(yachtResponse.getMotorPower())))
		.andExpect(jsonPath("$.priceFrom", CoreMatchers.is(yachtResponse.getPriceFrom())))
		.andExpect(jsonPath("$.accessories", CoreMatchers.is(yachtResponse.getAccessories().stream().map(accessory -> {
			try {
				return objectMapper.readValue(objectMapper.writeValueAsString(accessory), Object.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return accessory;
		}).toList())));
	}

	@Test
	final void searchController_updateYacht_ReturnYachtResponse() throws Exception {
		when(searchService.updateYacht(yachtRequest, id)).thenReturn(yachtResponse);	
	
		ResultActions response = mockMvc.perform(put("/search/"+id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(yachtRequest)));
		
		response.andExpect(status().isOk())
		.andExpect(jsonPath("$.model", CoreMatchers.is(yachtResponse.getModel())))
		.andExpect(jsonPath("$.propulsion", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(yachtResponse.getPropulsion()), Object.class))))
		.andExpect(jsonPath("$.length", CoreMatchers.is(yachtResponse.getLength())))
		.andExpect(jsonPath("$.capacity", CoreMatchers.is(yachtResponse.getCapacity())))
		.andExpect(jsonPath("$.motorPower", CoreMatchers.is(yachtResponse.getMotorPower())))
		.andExpect(jsonPath("$.priceFrom", CoreMatchers.is(yachtResponse.getPriceFrom())))
		.andExpect(jsonPath("$.accessories", CoreMatchers.is(yachtResponse.getAccessories().stream().map(accessory -> {
			try {
				return objectMapper.readValue(objectMapper.writeValueAsString(accessory), Object.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return accessory;
		}).toList())));
	}

	@Test
	final void searchController_deleteYacht_ReturnApiResponse() throws Exception {
		when(searchService.deleteYacht(id)).thenReturn(apiResponse);	

		ResultActions response = mockMvc.perform(delete("/search/"+id)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.isSuccess", CoreMatchers.is(apiResponse.getIsSuccess())))
				.andExpect(jsonPath("$.message", CoreMatchers.is(apiResponse.getMessage())))
				.andExpect(jsonPath("$.httpStatus", CoreMatchers.is(apiResponse.getHttpStatus().name())));
	}

}
