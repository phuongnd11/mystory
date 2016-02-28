package com.inspireon.mystory.exception;

public class InvalidPasswordException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6465275950351893973L;

	
	@Override
	public String getMessage(){
		return "Invalid password exception";
	}
}
