package com.mac2work.search.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YachtNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -5096171345641475066L;

	private String fieldName;
	private Object fieldValue;
	private String message;
	
	public YachtNotFoundException( String fieldName, Object fieldValue) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		formatMessage();		
	}
	
	private void formatMessage() {
		this.message = String.format("Yacht not found with %s: %s", fieldName, fieldValue);
	}
}
