package com.mac2work.orders.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ExceptionMessage {
	    private String timestamp;
	    private int status;
	    private String error;
	    private String message;
	    private String path;
	    private HttpStatus httpStatus;
}
