package com.mac2work.orders.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mac2work.orders.model.Order;
import com.mac2work.orders.proxy.SearchServiceProxy;
import com.mac2work.orders.repository.OrderRepository;
import com.mac2work.orders.request.OrderRequest;
import com.mac2work.orders.response.OrderResponse;
import com.mac2work.search.response.YachtResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrdersService {
	private final OrderRepository orderRepository;
	private final SearchServiceProxy searchServiceProxy;

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

	public OrderResponse getUserOrderById(Long userId) {
		Order order = orderRepository.getByUserId(userId);
		
		return mapOrderToOrderResponse(order);
	}

	public List<OrderResponse> getUserArchivalOrders(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public OrderResponse getUserArchivalOrderById(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
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

}
