package com.inspireon.chuyentrolinhtinh.exception;

public class ImageNotFoundException extends Exception {
	
	private static final long serialVersionUID = -1761971285540919699L;
	
	private final String imageName;
	
	public ImageNotFoundException(final String imageName) {
		this.imageName = imageName;
	}

	@Override
	public String getMessage() {
		return "Image: " + imageName + " not found!";
	}
}
