package com.mac2work.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.mac2work.apigateway.util.JwtUtil;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config>{
	
	@Autowired
	private  JwtUtil jwtUtil;
	
	public JwtAuthenticationFilter() {
        super(Config.class);
    }
	
	@Override
	public GatewayFilter apply(Config config) {
		   return ((exchange, chain) -> {
	           
	                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
	                    throw new RuntimeException("missing authorization header");
	                }

	                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
	                if (authHeader != null && authHeader.startsWith("Bearer ")) {
	                    authHeader = authHeader.substring(7);
	                }
	                //TODO: send logged in user username by feign
	                    if(!jwtUtil.isTokenValid(authHeader, ""))
	                    throw new RuntimeException("unauthorized access to application");
	                    
	            return chain.filter(exchange);
	        });
	}
	

	 public static class Config {

	    }
}
