package com.inspireon.chuyentrolinhtinh.exception;

public class UserAlreadyVoteStoryException extends Exception {
	
	private static final long serialVersionUID = -7845157391532582974L;
	
	private final String voter;
	private final String storyId;
	
	public UserAlreadyVoteStoryException(final String voter, final String storyId) {
		this.voter = voter;
		this.storyId = storyId;
	}

	@Override
	public String getMessage() {
		return "Story with story id: " + storyId + " was already voted by user: " + voter;
	}
}
