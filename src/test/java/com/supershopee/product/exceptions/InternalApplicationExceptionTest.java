package com.supershopee.product.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class InternalApplicationExceptionTest {

	@Test
	void testInternalApplicationException() {
		InternalApplicationException exception = assertThrows(
				InternalApplicationException.class, 
		    () -> { throw new InternalApplicationException("Internal Application Exception"); }
		);
		
		assertEquals("Internal Application Exception", exception.getMessage());
	}
}
