package com.mac2work.search.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -5096171345641475066L;

	private String fieldName;
	private Object fieldValue;
	private String resourceName;
	private String message;
	
	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.resourceName = resourceName;
		formatMessage();		
	}
	
	private void formatMessage() {
		this.message = String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue);
	}
}
