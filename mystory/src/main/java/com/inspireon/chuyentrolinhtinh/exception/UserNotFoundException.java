package com.inspireon.chuyentrolinhtinh.exception;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 4896368838810704806L;

	private final String username;
	
	public UserNotFoundException(final String username) {
		this.username = username;
	}

	@Override
	public String getMessage() {
		return "User with username \"" + username + "\" cannot be found in the system";
	}
}
