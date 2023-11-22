package com.mac2work.search.proxy;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac2work.search.exception.ExceptionMessage;
import com.mac2work.search.exception.UserPanelProxyException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class UserPanelProxyErrorDecoder implements ErrorDecoder {
    private ErrorDecoder errorDecoder = new Default();

	@Override
	public Exception decode(String methodKey, Response response) {
		ExceptionMessage message = null;
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, ExceptionMessage.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
		switch(response.status()) {
		case 401:
			return UserPanelProxyException.builder().message(message.getMessage()).httpStatus(HttpStatus.UNAUTHORIZED).build();
		
		default:
			return errorDecoder.decode(methodKey, response);
		}
	}

	
}
