package com.mac2work.orders.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserPanelProxyException extends RuntimeException {

	private static final long serialVersionUID = -2556861811293826602L;
	
	private String message;
	private HttpStatus httpStatus;
	

}
