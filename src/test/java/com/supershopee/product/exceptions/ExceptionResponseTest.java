package com.supershopee.product.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExceptionResponseTest {

	@InjectMocks
	ExceptionResponse exceptionResponse;
	
	@Test
	void testGetMessage() {
		exceptionResponse.setMessage("Test");
		assertThat(exceptionResponse.getMessage()).isEqualTo("Test");
	}

	@Test
	void testSetMessage() {
		exceptionResponse.setMessage("Test");
		assertThat(exceptionResponse.getMessage()).isEqualTo("Test");
	}

	@Test
	void testGetDateTime() {
		LocalDateTime dt =LocalDateTime.now();
		exceptionResponse.setDateTime(dt);
		assertThat(exceptionResponse.getDateTime().getYear()).isEqualTo(dt.getYear());
	}

	@Test
	void testSetDateTime() {
		LocalDateTime dt =LocalDateTime.now();
		exceptionResponse.setDateTime(dt);
		assertThat(exceptionResponse.getDateTime().getYear()).isEqualTo(dt.getYear());
	}

}
