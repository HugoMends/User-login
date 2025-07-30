package com.hugodev.user_login.controllers.handlers;

import java.time.Instant;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hugodev.user_login.dto.CustomError;
import com.hugodev.user_login.dto.ValidationError;
import com.hugodev.user_login.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		CustomError error = new CustomError(Instant.now(), status.value() , e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError error = new ValidationError(Instant.now(), status.value(), "Dados inválidos!", request.getRequestURI());
		for(FieldError f : e.getBindingResult().getFieldErrors()) {
			error.addError(f.getField(), f.getDefaultMessage());
		}
		return ResponseEntity.status(status).body(error);
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
		public ResponseEntity<CustomError> dataIntegrityViolation(DataIntegrityViolationException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.CONFLICT;
		String userFriendlyMessage = "Violação de integridade do banco de dados.";
		String exceptionMessage = e.getMessage();
		if(exceptionMessage != null && exceptionMessage.contains("Unique index or primary key violation") && 
				exceptionMessage.contains("TB_USER(EMAIL")) {
			userFriendlyMessage = "Email informado já esta sendo utilizado.";
		}else {userFriendlyMessage = "Erro de integridade de dados: " + e.getMessage();}
		CustomError error = new CustomError(Instant.now(), status.value(), userFriendlyMessage, request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}
	
}
