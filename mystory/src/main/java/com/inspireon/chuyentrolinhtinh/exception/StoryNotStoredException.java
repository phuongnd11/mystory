package com.inspireon.chuyentrolinhtinh.exception;

public class StoryNotStoredException extends Exception {

	private static final long serialVersionUID = 1176512273724706037L;

	private final String author;
	private final String title;
	
	public StoryNotStoredException(final String author, final String title) {
		this.title = title;
		this.author = author;
	}

	@Override
	public String getMessage() {
		return "Story with title: \"" + title + "\" by: " + author + " cannot be stored into the system.";
	}
}
