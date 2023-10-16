package com.mac2work.orders.service;

import org.springframework.stereotype.Service;

import com.mac2work.orders.model.Order;
import com.mac2work.orders.repository.OrderRepository;
import com.mac2work.orders.request.OrderRequest;
import com.mac2work.orders.response.OrderResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersService {
	private final OrderRepository orderRepository;

	public OrderResponse createOrder(OrderRequest orderRequest) {
		Order order = Order.builder()
				.build();
		orderRepository.save(order);
		
		return OrderResponse.builder()
				.build();
	}

}
