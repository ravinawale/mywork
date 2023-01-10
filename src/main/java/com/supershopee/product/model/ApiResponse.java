package com.supershopee.product.model;

public class ApiResponse<T> {

	int status;
	String message;
	T result;
	
	public ApiResponse(int status, String message, T result) {
		this.status = status;
		this.message = message;
		this.result = result;
	}
	
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public T getResult() {
		return result;
	}
}
