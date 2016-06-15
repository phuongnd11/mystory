package com.inspireon.chuyentrolinhtinh.web.rest.image;

public class FileMeta {

	private String fileType;

	private byte[] bytes;
	
	public FileMeta(String fileType, byte[] bytes) {
		super();
		this.fileType = fileType;
		this.bytes = bytes;
	}

	public String getFileType() {
		return fileType;
	}

	public byte[] getBytes() {
		return bytes;
	}
}
