package com.inspireon.mystory.exception;

public class EmailAlreadyInUseException extends Exception{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2337894881136383173L;
	
	private final String email;
	
	public EmailAlreadyInUseException(final String email){
		this.email = email;
	}
	
	@Override
	public String getMessage(){
		return "Email: " + email + "already in use";
	}
}
