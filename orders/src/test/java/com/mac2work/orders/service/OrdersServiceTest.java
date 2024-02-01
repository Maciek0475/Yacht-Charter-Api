package com.mac2work.orders.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

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
	private OrderRequest orderRequest;
	private OrderResponse orderResponse;
	private OrderResponse orderResponse2;

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
	}

	@Test
	final void testCreateOrder() {
		when(orderRepository.save(any(Order.class))).thenReturn(order);
		when(searchServiceProxy.getYachtModel(order.getYachtId())).thenReturn(new ResponseEntity<>("Sasanka", HttpStatus.OK));
		
		OrderResponse orderResponse = ordersService.createOrder(orderRequest);
		
		assertThat(orderResponse).isEqualTo(this.orderResponse);
	}

	@Test
	final void testGetUserActualOrders() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetUserActualOrderById() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetUserArchivalOrders() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetUserArchivalOrderById() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testUpdateOrder() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testDeleteOrder() {
		fail("Not yet implemented"); // TODO
	}

}
