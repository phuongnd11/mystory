package com.inspireon.chuyentrolinhtinh.exception;

public class ImageWrittenFailureException extends Exception{

	private static final long serialVersionUID = -1585200685366939429L;
	
	private final String filename;
	
	public ImageWrittenFailureException(final String filename){
		this.filename = filename;
	}
	
	@Override
	public String getMessage() {
		return "Fail to resize image from file: \"" + filename;
	}
}
