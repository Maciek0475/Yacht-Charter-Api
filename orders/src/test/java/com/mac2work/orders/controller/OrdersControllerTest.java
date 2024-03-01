package com.mac2work.orders.controller;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac2work.orders.request.OrderRequest;
import com.mac2work.orders.response.ApiResponse;
import com.mac2work.orders.response.OrderResponse;
import com.mac2work.orders.service.OrdersService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = OrdersController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrdersControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private OrdersService ordersService;
	
	private OrderRequest orderRequest;
	private OrderResponse orderResponse;
	private OrderResponse orderResponse2;
	private ApiResponse apiResponse;
	private LocalDate from;
	private LocalDate to;
	
	Long id;
	@BeforeEach
	void setUp() throws Exception {
		id = 1L;
		
		from = LocalDate.of(2024, 4, 15);
		to = LocalDate.of(2024, 4, 25);
		
		
		orderRequest = OrderRequest.builder()
				.userId(1L)
				.yachtId(1L)
				.days(10)
				.dateFrom(from)
				.dateTo(to)
				.price(1722.5)
				.build();
		orderResponse = OrderResponse.builder()
				.yachtModel("Sasanka")
				.days(10)
				.dateFrom(from)
				.dateTo(to)
				.price(1722.5)
				.build();
		orderResponse2 = OrderResponse.builder()
				.yachtModel("Orion")
				.days(10)
				.dateFrom(from)
				.dateTo(to)
				.price(1722.5)
				.build();
		apiResponse = ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.message("Order deleted successfully")
				.httpStatus(HttpStatus.OK)
				.build();
	}

	@Test
	final void ordersController_getUserActualOrders_ReturnListOfOrderResponses() throws Exception {
		List<OrderResponse> orderResponses = List.of(orderResponse, orderResponse2);
		when(ordersService.getUserActualOrders()).thenReturn(orderResponses);
		
		ResultActions response = mockMvc.perform(get("/orders")
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", CoreMatchers.is(orderResponses.size())));
	}

	@Test
	final void ordersController_getUserActualOrderById_ReturnOrderResponse() throws Exception {
		when(ordersService.getUserActualOrderById(id)).thenReturn(orderResponse);
		
		ResultActions response = mockMvc.perform(get("/orders/"+id)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.yachtModel", CoreMatchers.is(orderResponse.getYachtModel())))
				.andExpect(jsonPath("$.days", CoreMatchers.is(orderResponse.getDays())))
				.andExpect(jsonPath("$.dateFrom", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(orderResponse.getDateFrom()), Object.class))))
				.andExpect(jsonPath("$.dateTo", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(orderResponse.getDateTo()), Object.class))))
				.andExpect(jsonPath("$.price", CoreMatchers.is(orderResponse.getPrice())));
	}

	@Test
	final void ordersController_getUserArchivalOrders_ReturnListOfOrderResponses() throws Exception {
		orderResponse.setDateFrom(LocalDate.of(2023, 4, 15));
		orderResponse.setDateTo(LocalDate.of(2023, 4, 25));
		orderResponse2.setDateFrom(LocalDate.of(2023, 4, 15));
		orderResponse2.setDateTo(LocalDate.of(2023, 4, 25));
		List<OrderResponse> orderResponses = List.of(orderResponse, orderResponse2);
		when(ordersService.getUserArchivalOrders()).thenReturn(orderResponses);
		
		ResultActions response = mockMvc.perform(get("/orders/archival")
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", CoreMatchers.is(orderResponses.size())));	}

	@Test
	final void ordersController_getUserArchivalOrderById_ReturnOrderResponse() throws Exception {
		orderResponse.setDateFrom(LocalDate.of(2023, 4, 15));
		orderResponse.setDateTo(LocalDate.of(2023, 4, 25));
		when(ordersService.getUserArchivalOrderById(id)).thenReturn(orderResponse);
		
		ResultActions response = mockMvc.perform(get("/orders/archival/"+id)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.yachtModel", CoreMatchers.is(orderResponse.getYachtModel())))
				.andExpect(jsonPath("$.days", CoreMatchers.is(orderResponse.getDays())))
				.andExpect(jsonPath("$.dateFrom", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(orderResponse.getDateFrom()), Object.class))))
				.andExpect(jsonPath("$.dateTo", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(orderResponse.getDateTo()), Object.class))))
				.andExpect(jsonPath("$.price", CoreMatchers.is(orderResponse.getPrice())));	}

	@Test
	final void ordersController_createOrder_ReturnOrderResponse() throws Exception {
		when(ordersService.createOrder(orderRequest)).thenReturn(orderResponse);
		
		ResultActions response = mockMvc.perform(post("/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(orderRequest)));
		
		response.andExpect(status().isCreated())
				.andExpect(jsonPath("$.yachtModel", CoreMatchers.is(orderResponse.getYachtModel())))
				.andExpect(jsonPath("$.days", CoreMatchers.is(orderResponse.getDays())))
				.andExpect(jsonPath("$.dateFrom", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(orderResponse.getDateFrom()), Object.class))))
				.andExpect(jsonPath("$.dateTo", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(orderResponse.getDateTo()), Object.class))))
				.andExpect(jsonPath("$.price", CoreMatchers.is(orderResponse.getPrice())));	}

	@Test
	final void ordersController_updateOrder_ReturnOrderResponse() throws Exception {
		when(ordersService.updateOrder(orderRequest, id)).thenReturn(orderResponse2);
		
		ResultActions response = mockMvc.perform(put("/orders/"+id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(orderRequest)));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.yachtModel", CoreMatchers.is(orderResponse2.getYachtModel())))
				.andExpect(jsonPath("$.days", CoreMatchers.is(orderResponse2.getDays())))
				.andExpect(jsonPath("$.dateFrom", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(orderResponse2.getDateFrom()), Object.class))))
				.andExpect(jsonPath("$.dateTo", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(orderResponse2.getDateTo()), Object.class))))
				.andExpect(jsonPath("$.price", CoreMatchers.is(orderResponse2.getPrice())));	}

	@Test
	final void ordersController_deleteOrder_ReturnApiResponse() throws Exception {
		when(ordersService.deleteOrder(id)).thenReturn(apiResponse);
		
		ResultActions response = mockMvc.perform(delete("/orders/"+id)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.isSuccess", CoreMatchers.is(apiResponse.getIsSuccess())))
				.andExpect(jsonPath("$.message", CoreMatchers.is(apiResponse.getMessage())))
				.andExpect(jsonPath("$.httpStatus", CoreMatchers.is(apiResponse.getHttpStatus().name())));
	}

}
