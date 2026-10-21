package com.project.JobPortal.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.FieldError;
import com.project.JobPortal.dto.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {

	    Map<String, String> errors = new HashMap<>();

	    for (FieldError error : ex.getBindingResult().getFieldErrors()) 
	    {
	        errors.put(error.getField(), error.getDefaultMessage());
	    }

	    return ResponseEntity.badRequest().body(new ErrorResponseDTO(400, "Validation failed", errors));
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) 
	{
	    return ResponseEntity.badRequest().body(new ErrorResponseDTO(400, ex.getMessage(), null));
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(
	        AccessDeniedException ex) {

	    return ResponseEntity
	            .status(403)
	            .body(new ErrorResponseDTO(403, ex.getMessage(), null));
	}
}
