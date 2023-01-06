package com.supershopee.product.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ProductNotFoundExceptionTest {

	@Test
	void test() {
		ProductNotFoundException exception = assertThrows(
				ProductNotFoundException.class, 
		    () -> { throw new ProductNotFoundException("Product Not Exception"); }
		);
		
		assertEquals("Product Not Exception", exception.getMessage());
	}

}
