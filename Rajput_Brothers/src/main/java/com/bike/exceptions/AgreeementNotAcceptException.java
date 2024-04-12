package com.bike.exceptions;

public class AgreeementNotAcceptException extends RuntimeException {

    String message;

	public AgreeementNotAcceptException(String message) {
		super(message);
		this.message = message;
	}
    
	
}
