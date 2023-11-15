package com.mac2work.userpanel.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 2321638236784417179L;
	
	private String fieldName;
	private Object fieldValue;
	private String message;
	
	public UserNotFoundException( String fieldName, Object fieldValue) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		formatMessage();		
	}
	
	private void formatMessage() {
		this.message = String.format("User not found with %s: %s", fieldName, fieldValue);
	}
}
