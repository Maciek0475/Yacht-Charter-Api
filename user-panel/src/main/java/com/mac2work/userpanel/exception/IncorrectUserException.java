package com.mac2work.userpanel.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncorrectUserException extends RuntimeException {

	private static final long serialVersionUID = -2387159589904268266L;
	
	private String resource;
	private String message;
	
	public IncorrectUserException(String resource) {
		this.resource = resource;
		formatMessage();
	}
	
	private void formatMessage() {
		this.message = String.format("You have no rights to modify this %s", resource);
	}

}
