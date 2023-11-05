package com.mac2work.orders.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.orders.request.OrderRequest;
import com.mac2work.orders.response.ApiResponse;
import com.mac2work.orders.response.OrderResponse;
import com.mac2work.orders.service.OrdersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {
	private final OrdersService ordersService;
	
	@GetMapping
	public ResponseEntity<List<OrderResponse>> getUserOrders(){
		//get current user userId by feign or something
		Long userId = (long) 1;
		List<OrderResponse> orders = ordersService.getUserOrders(userId);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderResponse> getUserOrderById(@PathVariable Long id){
		//for test
		Long userId = (long) 1;
		OrderResponse order = ordersService.getUserOrderById(userId);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	
	@GetMapping("/archival")
	public ResponseEntity<List<OrderResponse>> getUserArchivalOrders(){
		//for test
		Long userId = (long) 1;
		List<OrderResponse> orders = ordersService.getUserArchivalOrders(userId);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@GetMapping("/archival/{id}")
	public ResponseEntity<OrderResponse> getUserArchivalOrderById(@PathVariable Long id){
		//for test
		Long userId = (long) 1;
		OrderResponse order = ordersService.getUserArchivalOrderById(id, userId);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest){
		OrderResponse orderResponse = ordersService.createOrder(orderRequest);
		return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OrderResponse> updateOrder(@RequestBody OrderRequest orderRequest, @PathVariable Long id){
		OrderResponse orderResponse = ordersService.updateOrder(orderRequest, id);
		return new ResponseEntity<>(orderResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long id){
		ApiResponse apiResponse = ordersService.deleteOrder(id);
		return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
	}
	

}
