package com.mac2work.orders.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mac2work.orders.exception.OrderNotFoundException;
import com.mac2work.orders.model.Order;
import com.mac2work.orders.proxy.SearchServiceProxy;
import com.mac2work.orders.proxy.UserPanelProxy;
import com.mac2work.orders.repository.OrderRepository;
import com.mac2work.orders.request.OrderRequest;
import com.mac2work.orders.response.ApiResponse;
import com.mac2work.orders.response.OrderResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrdersService {
	private final OrderRepository orderRepository;
	private final SearchServiceProxy searchServiceProxy;
	private final UserPanelProxy userPanelProxy;
	
	private String getYachtModel(Long yachtId) {
		ResponseEntity<String> entity = searchServiceProxy.getYachtModel(yachtId);
		String model = entity.getBody();
		return model;
	}
	
	private OrderResponse mapToOrderResponse(Order order) {
		return OrderResponse.builder()
				.yachtModel(getYachtModel(order.getYachtId()))
				.days(order.getDays())
				.dateFrom(order.getDateFrom())
				.dateTo(order.getDateTo())
				.price(order.getPrice())
				.build();
	}
	

	public OrderResponse createOrder(OrderRequest orderRequest) {
		Order order = Order.builder()
				.userId(orderRequest.getUserId())
				.yachtId(orderRequest.getYachtId())
				.days(orderRequest.getDays())
				.dateFrom(orderRequest.getDateFrom())
				.dateTo(orderRequest.getDateTo())
				.price(orderRequest.getPrice())
				.build();
		order = orderRepository.save(order);
		
		return mapToOrderResponse(order);
	}

	public List<OrderResponse> getUserActualOrders() {
		Long userId = userPanelProxy.getLoggedInUserId();
		List<Order> orders = orderRepository.findAllActualByUserId(userId, LocalDate.now());
		List<OrderResponse> orderResponses = orders.stream().map(order -> mapToOrderResponse(order)).toList();
		return orderResponses;
	}

	public OrderResponse getUserActualOrderById(Long id) {
		Long userId = userPanelProxy.getLoggedInUserId();
		Order order = orderRepository.findActualByUserId(id, userId, LocalDate.now());
		return mapToOrderResponse(order);
	}

	public List<OrderResponse> getUserArchivalOrders() {
		Long userId = userPanelProxy.getLoggedInUserId();
		List<Order> orders = orderRepository.findAllArchivalByUserId(userId, LocalDate.now());
		List<OrderResponse> orderResponses = orders.stream().map(order -> mapToOrderResponse(order)).toList();
		return orderResponses;
	}

	public OrderResponse getUserArchivalOrderById(Long id) {
		Long userId = userPanelProxy.getLoggedInUserId();
		Order order = orderRepository.findArchivalByUserId(id, userId, LocalDate.now());
		return mapToOrderResponse(order);
	}

	public OrderResponse updateOrder(OrderRequest orderRequest, Long id) {
		userPanelProxy.isAdmin("orders", "put");
		Order order = orderRepository.findById(id).orElseThrow( () -> new OrderNotFoundException("id", id));
		order.setUserId(orderRequest.getUserId());
		order.setYachtId(orderRequest.getYachtId());
		order.setDays(orderRequest.getDays());
		order.setDateFrom(orderRequest.getDateFrom());
		order.setDateTo(orderRequest.getDateTo());
		order.setPrice(orderRequest.getPrice());
		
		order = orderRepository.save(order);
		
		return mapToOrderResponse(order);
	}

	public ApiResponse deleteOrder(Long id) {
		userPanelProxy.isAdmin("orders", "delete");
		Order order = orderRepository.findById(id).orElseThrow( () -> new OrderNotFoundException("id", id));
		orderRepository.delete(order);
		
		return ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.message("Order deleted successfully")
				.httpStatus(HttpStatus.NO_CONTENT)
				.build();
	}

}
