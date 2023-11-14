package com.mac2work.orders.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -6654839284388587514L;

	private String fieldName;
	private Object fieldValue;
	private String message;
	
	public OrderNotFoundException( String fieldName, Object fieldValue) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		formatMessage();		
	}
	
	private void formatMessage() {
		this.message = String.format("Order not found with %s: %s", fieldName, fieldValue);
	}
}
