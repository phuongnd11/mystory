package com.inspireon.mystory.exception;

public class MessageOpenFailureException extends Exception {

	private static final long serialVersionUID = 4896368838810704806L;

	@Override
	public String getMessage() {
		return "Can not open this message.";
	}
}
