package com.mac2work.orders.service;

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
		ResponseEntity<YachtResponse> entity = searchServiceProxy.getYachtById(orderRequest.getYachtId());
		YachtResponse yacht = entity.getBody();
		return OrderResponse.builder()
				.yachtModel(yacht.getModel())
				.days(orderRequest.getDays())
				.price(orderRequest.getPrice())
				.build();
	}

}
