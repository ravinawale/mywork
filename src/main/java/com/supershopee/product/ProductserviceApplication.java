package com.supershopee.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:api_error_messages.properties"})
@PropertySource(value = {"classpath:api_response_messages.properties"})
public class ProductserviceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ProductserviceApplication.class, args);
	}
}
