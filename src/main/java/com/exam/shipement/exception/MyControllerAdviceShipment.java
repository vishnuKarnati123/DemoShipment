package com.exam.shipement.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

@RestControllerAdvice
public class MyControllerAdviceShipment extends ResponseEntityExceptionHandler{
	

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex)
	{
		return new ResponseEntity<String>("check your input type",HttpStatus.OK);
	}
	
	@ExceptionHandler(ShipmentException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleShipmentException(ShipmentException exception)
	{
		return exception.getMessage();
	}
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public String handleNoSuchElementException(NoSuchElementException exception)
	{
		return exception.getMessage();
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public String handleException(Exception exception)
	{
		return "error occured we will look into it";
	}
	
	  protected ResponseEntity<Object> handleExceptionInternal(
				Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

			if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
				request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
			return new ResponseEntity<Object>("server side error will get back to you",HttpStatus.INTERNAL_SERVER_ERROR);
			}
			if(HttpStatus.NOT_FOUND.equals(status))
			{
				return new ResponseEntity<Object>("unable to found the request method please check url again",HttpStatus.NOT_FOUND);
			}
			else
			{
				return new ResponseEntity<Object>("something worng please check request again",HttpStatus.BAD_REQUEST);
			}
		}

}
