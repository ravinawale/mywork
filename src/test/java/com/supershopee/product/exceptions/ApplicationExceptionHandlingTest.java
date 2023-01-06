package com.supershopee.product.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ApplicationExceptionHandlingTest {

	@InjectMocks
	ApplicationExceptionHandling applicationExceptionHandling;
	
	@Test
	void testHandleInvalidInput() {
		
		InvalidInputException exception = assertThrows(
				InvalidInputException.class, 
		        () -> { throw new InvalidInputException("a message"); }
		);
		
		ResponseEntity<Object> result = applicationExceptionHandling.handleInvalidInput(exception);
		assertThat(result.getStatusCodeValue()).isEqualTo(400);
	}

	@Test
	void testHandleProductNotFound() {
		ProductNotFoundException exception = assertThrows(
				ProductNotFoundException.class, 
		    () -> { throw new ProductNotFoundException("a message"); }
		);
		
		ResponseEntity<Object> result = applicationExceptionHandling.handleProductNotFound(exception);
		assertThat(result.getStatusCodeValue()).isEqualTo(404);
	}

	@Test
	void testInternalApplicationException() {
		InternalApplicationException exception = assertThrows(
				InternalApplicationException.class, 
		    () -> { throw new InternalApplicationException("a message"); }
		);
		
		ResponseEntity<Object> result = applicationExceptionHandling.internalApplicationException(exception);
		assertThat(result.getStatusCodeValue()).isEqualTo(500);
	}

}
