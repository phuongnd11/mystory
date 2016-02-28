package com.inspireon.mystory.exception;

public class DuplicateUsernameException extends Exception{

	private static final long serialVersionUID = -1585200685366939429L;
	
	private final String username;
	
	public DuplicateUsernameException(final String username){
		this.username = username;
	}
	
	@Override
	public String getMessage() {
		return "Duplicate username: \"" + username;
	}
}
