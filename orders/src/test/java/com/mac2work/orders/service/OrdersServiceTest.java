package com.mac2work.orders.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mac2work.orders.model.Order;
import com.mac2work.orders.proxy.SearchServiceProxy;
import com.mac2work.orders.proxy.UserPanelProxy;
import com.mac2work.orders.repository.OrderRepository;
import com.mac2work.orders.request.OrderRequest;
import com.mac2work.orders.response.ApiResponse;
import com.mac2work.orders.response.OrderResponse;

@ExtendWith(MockitoExtension.class)
class OrdersServiceTest {
	
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private SearchServiceProxy searchServiceProxy;
	@Mock
	private UserPanelProxy userPanelProxy;
	
	@InjectMocks
	private OrdersService ordersService;
	
	private Order order;
	private Order order2;
	private OrderRequest orderRequest;
	private OrderResponse orderResponse;
	private OrderResponse orderResponse2;
	private ApiResponse apiResponse;

	Long userId;
	
	LocalDate from;
	LocalDate to;

	@BeforeEach
	void setUp() throws Exception {
		userId = 1L;
		
		from = LocalDate.of(2024, 4, 15);
		to = LocalDate.of(2024, 4, 25);
		
		order = Order.builder()
				.userId(userId)
				.yachtId(1L)
				.days(10)
				.dateFrom(from)
				.dateTo(to)
				.price(1722.5)
				.build();
		order2 = Order.builder()
				.userId(userId)
				.yachtId(2L)
				.days(10)
				.dateFrom(from)
				.dateTo(to)
				.price(1722.5)
				.build();
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
				.httpStatus(HttpStatus.NO_CONTENT)
				.build();
	}

	@Test
	final void ordersService_createOrder_ReturnOrderResponse() {
		when(orderRepository.save(any(Order.class))).thenReturn(order);
		when(searchServiceProxy.getYachtModel(order.getYachtId())).thenReturn(new ResponseEntity<>("Sasanka", HttpStatus.OK));
		
		OrderResponse orderResponse = ordersService.createOrder(orderRequest);
		
		assertThat(orderResponse).isEqualTo(this.orderResponse);
	}

	@Test
	final void ordersService_getUserActualOrders_ReturnListOfOrderResponses() {
		List<Order> orders = List.of(order, order2);
		List<OrderResponse> expectedOrderResponses = List.of(orderResponse, orderResponse2);
		when(searchServiceProxy.getYachtModel(order.getYachtId())).thenReturn(new ResponseEntity<>("Sasanka", HttpStatus.OK));
		when(searchServiceProxy.getYachtModel(order2.getYachtId())).thenReturn(new ResponseEntity<>("Orion", HttpStatus.OK));
		when(userPanelProxy.getLoggedInUserId()).thenReturn(userId);
		when(orderRepository.findAllActualByUserId(userId, LocalDate.now())).thenReturn(orders);
		
		List<OrderResponse> actualOrderResponses = ordersService.getUserActualOrders();
		
		assertThat(actualOrderResponses).isEqualTo(expectedOrderResponses);
	}

	@Test
	final void ordersService_getUserActualOrderById_ReturnOrderResponse() {
		when(searchServiceProxy.getYachtModel(order.getYachtId())).thenReturn(new ResponseEntity<>("Sasanka", HttpStatus.OK));
		when(userPanelProxy.getLoggedInUserId()).thenReturn(userId);
		when(orderRepository.findActualByUserId(1L, userId, LocalDate.now())).thenReturn(order);
		
		OrderResponse orderResponse = ordersService.getUserActualOrderById(1L);
		
		assertThat(orderResponse).isEqualTo(this.orderResponse);	}

	@Test
	final void ordersService_getUserArchivalOrders_ReturnListOfOrderResponses() {
		this.orderResponse.setDateFrom(LocalDate.of(2023, 4, 15));
		this.orderResponse.setDateTo(LocalDate.of(2023, 4, 25));
		this.orderResponse2.setDateFrom(LocalDate.of(2023, 4, 15));
		this.orderResponse2.setDateTo(LocalDate.of(2023, 4, 25));
		order.setDateFrom(LocalDate.of(2023, 4, 15));
		order.setDateTo(LocalDate.of(2023, 4, 25));
		order2.setDateFrom(LocalDate.of(2023, 4, 15));
		order2.setDateTo(LocalDate.of(2023, 4, 25));
		List<Order> orders = List.of(order, order2);
		List<OrderResponse> expectedOrderResponses = List.of(orderResponse, orderResponse2);
		when(searchServiceProxy.getYachtModel(order.getYachtId())).thenReturn(new ResponseEntity<>("Sasanka", HttpStatus.OK));
		when(searchServiceProxy.getYachtModel(order2.getYachtId())).thenReturn(new ResponseEntity<>("Orion", HttpStatus.OK));
		when(userPanelProxy.getLoggedInUserId()).thenReturn(userId);
		when(orderRepository.findAllArchivalByUserId(userId, LocalDate.now())).thenReturn(orders);
		
		List<OrderResponse> actualOrderResponses = ordersService.getUserArchivalOrders();
		
		assertThat(actualOrderResponses).isEqualTo(expectedOrderResponses);
	}

	@Test
	final void ordersService_getUserArchivalOrderById_ReturnOrderResponse() {
		this.orderResponse.setDateFrom(LocalDate.of(2023, 4, 15));
		this.orderResponse.setDateTo(LocalDate.of(2023, 4, 25));
		order.setDateFrom(LocalDate.of(2023, 4, 15));
		order.setDateTo(LocalDate.of(2023, 4, 25));
		when(searchServiceProxy.getYachtModel(order.getYachtId())).thenReturn(new ResponseEntity<>("Sasanka", HttpStatus.OK));
		when(userPanelProxy.getLoggedInUserId()).thenReturn(userId);
		when(orderRepository.findActualByUserId(1L, userId, LocalDate.now())).thenReturn(order);
		
		OrderResponse orderResponse = ordersService.getUserActualOrderById(1L);
		
		assertThat(orderResponse).isEqualTo(this.orderResponse);
	}

	@Test
	final void ordersService_updateOrder_ReturnOrderResponse() {
		when(searchServiceProxy.getYachtModel(order.getYachtId())).thenReturn(new ResponseEntity<>("Sasanka", HttpStatus.OK));
		when(userPanelProxy.isAdmin("orders", "put")).thenReturn(Boolean.TRUE);
		when(orderRepository.findById(2L)).thenReturn(Optional.of(order2));
		when(orderRepository.save(any(Order.class))).thenReturn(order);	
		
		OrderResponse orderResponse = ordersService.updateOrder(orderRequest, 2L);
		
		assertThat(orderResponse).isEqualTo(this.orderResponse);
	}

	@Test
	final void ordersService_deleteOrder_ReturnApiResponse() {
		when(userPanelProxy.isAdmin("orders", "delete")).thenReturn(Boolean.TRUE);
		when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
		doNothing().when(orderRepository).delete(order);
		
		ApiResponse apiResponse = ordersService.deleteOrder(1L);
		
		assertThat(apiResponse).isEqualTo(this.apiResponse);
	}
}