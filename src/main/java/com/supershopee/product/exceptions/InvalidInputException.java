package com.supershopee.product.exceptions;

public class InvalidInputException extends Exception{
	
	private static final long serialVersionUID = 5095339184624733624L;

	public InvalidInputException(String message) {
        super(message);
	}
}
