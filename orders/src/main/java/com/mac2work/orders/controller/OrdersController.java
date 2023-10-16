package com.mac2work.orders.controller;

import org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.orders.request.OrderRequest;
import com.mac2work.orders.response.OrderResponse;
import com.mac2work.orders.service.OrdersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {
	private final OrdersService ordersService;
	
	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest){
		OrderResponse orderResponse = ordersService.createOrder(orderRequest);
		return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
	}

}
