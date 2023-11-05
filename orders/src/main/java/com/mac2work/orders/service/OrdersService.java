package com.mac2work.orders.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mac2work.orders.model.Order;
import com.mac2work.orders.proxy.SearchServiceProxy;
import com.mac2work.orders.repository.OrderRepository;
import com.mac2work.orders.request.OrderRequest;
import com.mac2work.orders.response.ApiResponse;
import com.mac2work.orders.response.OrderResponse;
import com.mac2work.search.response.YachtResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrdersService {
	private final OrderRepository orderRepository;
	private final SearchServiceProxy searchServiceProxy;
	
	private YachtResponse getYachtResponse(Long yachtId) {
		ResponseEntity<YachtResponse> entity = searchServiceProxy.getYachtById(yachtId);
		YachtResponse yacht = entity.getBody();
		return yacht;
	}
	
	private OrderResponse mapOrderRequestToOrderResponse(OrderRequest orderRequest) {
		return OrderResponse.builder()
				.yachtModel(getYachtResponse(orderRequest.getYachtId()).getModel())
				.days(orderRequest.getDays())
				.from(orderRequest.getFrom())
				.to(orderRequest.getTo())
				.price(orderRequest.getPrice())
				.build();
	}
	
	private OrderResponse mapOrderToOrderResponse(Order order) {
		return OrderResponse.builder()
				.yachtModel(getYachtResponse(order.getYachtId()).getModel())
				.days(order.getDays())
				.from(order.getFrom())
				.to(order.getTo())
				.price(order.getPrice())
				.build();
	}
	
	private List<OrderResponse> mapOrdersToOrderResponses(List<Order> orders) {
		return orders.stream().map(
				order -> OrderResponse.builder()
				.yachtModel(
						getYachtResponse(order.getYachtId()).getModel())
				.days(order.getDays())
				.from(order.getFrom())
				.to(order.getTo())
				.price(order.getPrice())
				.build()).toList();
	}

	public OrderResponse createOrder(OrderRequest orderRequest) {
		Order order = Order.builder()
				.userId(orderRequest.getUserId())
				.yachtId(orderRequest.getYachtId())
				.days(orderRequest.getDays())
				.price(orderRequest.getPrice())
				.build();
		
		orderRepository.save(order);
		
		return mapOrderRequestToOrderResponse(orderRequest);
	}

	

	public List<OrderResponse> getUserOrders(Long userId) {
		List<Order> orders = orderRepository.findAllByUserId(userId);	
		
		return mapOrdersToOrderResponses(orders);
	}

	public OrderResponse getUserOrderById(Long userId) {
		Order order = orderRepository.findByUserId(userId);
		return mapOrderToOrderResponse(order);
	}

	public List<OrderResponse> getUserArchivalOrders(Long userId) {
		List<Order> orders = orderRepository.findAllArchivalByUserId(userId, LocalDate.now());
		return mapOrdersToOrderResponses(orders);
	}

	public OrderResponse getUserArchivalOrderById(Long id, Long userId) {
		Order order = orderRepository.findArchivalByUserId(id, userId, LocalDate.now());
		return mapOrderToOrderResponse(order);
	}

	public OrderResponse updateOrder(OrderRequest orderRequest, Long id) {
		Order order = orderRepository.findById(id).orElseThrow();
		order.setUserId(orderRequest.getUserId());
		order.setYachtId(orderRequest.getYachtId());
		order.setDays(orderRequest.getDays());
		order.setFrom(orderRequest.getFrom());
		order.setTo(orderRequest.getTo());
		order.setPrice(orderRequest.getPrice());
		
		orderRepository.save(order);
		Order updatedOrder = orderRepository.findById(id).orElseThrow();
		
		return mapOrderToOrderResponse(updatedOrder);
	}

	public ApiResponse deleteOrder(Long id) {
		Order order = orderRepository.findById(id).orElseThrow();
		orderRepository.delete(order);
		
		return ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.message("Order deleted successfully")
				.httpStatus(HttpStatus.NO_CONTENT)
				.build();
	}

}
