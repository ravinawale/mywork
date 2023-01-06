package com.supershopee.product.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class InvalidInputExceptionTest {

	@Test
	void testInvalidInputException() {
		InvalidInputException exception = assertThrows(
				InvalidInputException.class, 
		    () -> { throw new InvalidInputException("Invalid Input Exception"); }
		);
		
		assertEquals("Invalid Input Exception", exception.getMessage());
	}

}
