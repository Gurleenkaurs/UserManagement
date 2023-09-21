package com.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.user.model.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ApiResponse>  handleUserNotFoundException(UserNotFoundException ex)
	{
	  String message=	ex.getMessage();
	  ApiResponse apiResponse=new ApiResponse(message);
	  return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
	}
	

}
