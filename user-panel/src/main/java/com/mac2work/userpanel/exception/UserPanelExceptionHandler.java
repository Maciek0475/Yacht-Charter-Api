package com.mac2work.userpanel.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserPanelExceptionHandler {
	
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
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<UserApiError> handleUserNotFound(UserNotFoundException exc){
		UserApiError userApiError = new UserApiError(HttpStatus.NOT_FOUND, exc.getMessage());
		return new ResponseEntity<>(userApiError, userApiError.getHttpStatus());
	}
	
	@ExceptionHandler(IncorrectUserException.class)
	public ResponseEntity<UserApiError> handleIncorrectUser(IncorrectUserException exc){
		UserApiError userApiError = new UserApiError(HttpStatus.UNAUTHORIZED, exc.getMessage());
		return new ResponseEntity<>(userApiError, userApiError.getHttpStatus());
	}
	@ExceptionHandler(NoAccessException.class)
	public ResponseEntity<UserApiError> handleNoAccess(NoAccessException exc){
		UserApiError userApiError = new UserApiError(HttpStatus.UNAUTHORIZED, exc.getMessage());
		return new ResponseEntity<>(userApiError, userApiError.getHttpStatus());
	}

}
