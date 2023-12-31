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
import com.mac2work.search.response.YachtResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrdersService {
	private final OrderRepository orderRepository;
	private final SearchServiceProxy searchServiceProxy;
	private final UserPanelProxy userPanelProxy;
	
	private YachtResponse getYachtResponse(Long yachtId) {
		ResponseEntity<YachtResponse> entity = searchServiceProxy.getYachtById(yachtId);
		YachtResponse yacht = entity.getBody();
		return yacht;
	}
	
	private OrderResponse mapOrderRequestToOrderResponse(OrderRequest orderRequest) {
		return OrderResponse.builder()
				.yachtModel(getYachtResponse(orderRequest.getYachtId()).getModel())
				.days(orderRequest.getDays())
				.dateFrom(orderRequest.getDateFrom())
				.dateTo(orderRequest.getDateTo())
				.price(orderRequest.getPrice())
				.build();
	}
	
	private OrderResponse mapOrderToOrderResponse(Order order) {
		return OrderResponse.builder()
				.yachtModel(getYachtResponse(order.getYachtId()).getModel())
				.days(order.getDays())
				.dateFrom(order.getDateFrom())
				.dateTo(order.getDateTo())
				.price(order.getPrice())
				.build();
	}
	
	private List<OrderResponse> mapOrdersToOrderResponses(List<Order> orders) {
		return orders.stream().map(
				order -> OrderResponse.builder()
				.yachtModel(
						getYachtResponse(order.getYachtId()).getModel())
				.days(order.getDays())
				.dateFrom(order.getDateFrom())
				.dateTo(order.getDateTo())
				.price(order.getPrice())
				.build()).toList();
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
		orderRepository.save(order);
		
		return mapOrderRequestToOrderResponse(orderRequest);
	}

	public List<OrderResponse> getUserActualOrders() {
		Long userId = userPanelProxy.getLoggedInUserId();
		List<Order> orders = orderRepository.findAllActualByUserId(userId, LocalDate.now());	
		return mapOrdersToOrderResponses(orders);
	}

	public OrderResponse getUserActualOrderById(Long id) {
		Long userId = userPanelProxy.getLoggedInUserId();
		Order order = orderRepository.findActualByUserId(id, userId, LocalDate.now());
		return mapOrderToOrderResponse(order);
	}

	public List<OrderResponse> getUserArchivalOrders() {
		Long userId = userPanelProxy.getLoggedInUserId();
		List<Order> orders = orderRepository.findAllArchivalByUserId(userId, LocalDate.now());
		return mapOrdersToOrderResponses(orders);
	}

	public OrderResponse getUserArchivalOrderById(Long id) {
		Long userId = userPanelProxy.getLoggedInUserId();
		Order order = orderRepository.findArchivalByUserId(id, userId, LocalDate.now());
		return mapOrderToOrderResponse(order);
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
		
		orderRepository.save(order);
		Order updatedOrder = orderRepository.findById(id).orElseThrow( () -> new OrderNotFoundException("id", id));
		
		return mapOrderToOrderResponse(updatedOrder);
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
