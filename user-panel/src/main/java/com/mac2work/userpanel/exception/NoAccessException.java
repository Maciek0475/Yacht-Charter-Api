package com.mac2work.userpanel.exception;

import com.mac2work.userpanel.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoAccessException extends RuntimeException {

	private static final long serialVersionUID = -830115983369654147L;
	
	private String path;
	private String mappingMethod;
	private Role requiredRole;
	private String message;
	
	public NoAccessException(String path, String mappingMethod, Role requiredRole) {
		this.path = path;
		this.mappingMethod = mappingMethod.toUpperCase();
		this.requiredRole = requiredRole;
		formatMessage();
	}
	private void formatMessage() {

		this.message = String.format("You have no privileges to access: '/%s' through %s mapping, required privilege is: '%s'", path, mappingMethod, requiredRole);
	}

}
