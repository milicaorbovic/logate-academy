package com.logate.lacademy.web.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.logate.lacademy.web.exceptions.ValidationException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RESTExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Object> validationHandler(ValidationException e)
	{
		Errors errors = e.getErrors();
		Map<String, Object> validationErrors = new HashMap<>();
		
		final List<Map<String, Object>> mappedErrors = new ArrayList<>();
		for (FieldError fieldError : errors.getFieldErrors())
		{
			Map<String, Object> mapValue = new HashMap<>();
			mapValue.put("field", fieldError.getField());
			mapValue.put("message", fieldError.getDefaultMessage());
			
			mappedErrors.add(mapValue);
		}
		validationErrors.put("validationErrors", mappedErrors);
		return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
	}
	
}
