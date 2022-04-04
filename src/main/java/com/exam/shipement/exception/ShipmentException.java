package com.exam.shipement.exception;

public class ShipmentException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;

	public ShipmentException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShipmentException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
	
	
	

}
