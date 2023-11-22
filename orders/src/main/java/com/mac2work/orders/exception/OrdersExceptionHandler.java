package com.mac2work.orders.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrdersExceptionHandler {
	

	@ExceptionHandler(MethodArgumentNotValidException.class)
	 public ResponseEntity<Map<String, Map<String, String>>> handleMethodArgumentNotValid(MethodArgumentNotValidException exc) {
        Map<String, String> errorMap = new HashMap<>();
        exc.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        Map<String, Map<String, String>> errorResponse = new HashMap<>();
        errorResponse.put("validation errors", errorMap);
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<OrdersApiError> handleOrderNotFound(OrderNotFoundException exc){
		OrdersApiError ordersApiError = new OrdersApiError(HttpStatus.NOT_FOUND, exc.getMessage());
		return new ResponseEntity<>(ordersApiError, ordersApiError.getHttpStatus());	
	}
	
	@ExceptionHandler(UserPanelProxyException.class)
	public ResponseEntity<OrdersApiError> handleNoAccess(UserPanelProxyException exc){
		OrdersApiError ordersApiError = new OrdersApiError(exc.getHttpStatus(), exc.getMessage());
		return new ResponseEntity<>(ordersApiError, ordersApiError.getHttpStatus());	
	}
	
}
