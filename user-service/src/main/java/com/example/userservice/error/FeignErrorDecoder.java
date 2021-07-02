package com.example.userservice.error;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecoder  implements ErrorDecoder{

	Environment env;
	
	public FeignErrorDecoder(Environment env) {
		super();
		this.env = env;
	}

	@Override
	public Exception decode(String methodKey, Response response) {
		
		switch(response.status()) {
			case 400:
				break;
			case 404:
				if(methodKey.contains("getOrders")) {
					return new ResponseStatusException(HttpStatus.valueOf(response.status()),
							env.getProperty("order_service.exception.orders_is_empty"));
							
				}
				break;
			default:
				return new Exception(response.reason());
		
		}
		return null;
	}
}
