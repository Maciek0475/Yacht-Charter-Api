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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {
	private final OrdersService ordersService;
	
	@GetMapping
	public ResponseEntity<List<OrderResponse>> getUserActualOrders(){
		List<OrderResponse> orderResponses = ordersService.getUserActualOrders();
		return new ResponseEntity<>(orderResponses, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderResponse> getUserActualOrderById(@PathVariable Long id){
		OrderResponse orderResponse = ordersService.getUserActualOrderById(id);
		return new ResponseEntity<>(orderResponse, HttpStatus.OK);
	}
	
	@GetMapping("/archival")
	public ResponseEntity<List<OrderResponse>> getUserArchivalOrders(){
		List<OrderResponse> orderResponses = ordersService.getUserArchivalOrders();
		return new ResponseEntity<>(orderResponses, HttpStatus.OK);
	}
	
	@GetMapping("/archival/{id}")
	public ResponseEntity<OrderResponse> getUserArchivalOrderById(@PathVariable Long id){
		OrderResponse orderResponse = ordersService.getUserArchivalOrderById(id);
		return new ResponseEntity<>(orderResponse, HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest){
		OrderResponse orderResponse = ordersService.createOrder(orderRequest);
		return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OrderResponse> updateOrder(@Valid @RequestBody OrderRequest orderRequest, @PathVariable Long id){
		OrderResponse orderResponse = ordersService.updateOrder(orderRequest, id);
		return new ResponseEntity<>(orderResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long id){
		ApiResponse apiResponse = ordersService.deleteOrder(id);
		return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
	}
	

}
