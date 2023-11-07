package com.mac2work.apigateway.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {
	
	public static final List<String> unsecApiEndpoints = List.of(
			"/user/auth/authenticate",
			"/user/auth/register",
			"/user/auth/id",
			"/eureka"
			);
	
	 public Predicate<ServerHttpRequest> isSecured =
	            request -> unsecApiEndpoints
	                    .stream()
	                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
