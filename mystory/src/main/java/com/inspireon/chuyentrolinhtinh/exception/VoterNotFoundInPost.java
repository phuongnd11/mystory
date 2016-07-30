package com.inspireon.chuyentrolinhtinh.exception;

public class VoterNotFoundInPost extends Exception {
	
	private static final long serialVersionUID = 5855467986751053088L;
	
	private final String voter;
	private final String storyId;
	
	public VoterNotFoundInPost(final String voter, final String storyId) {
		this.voter = voter;
		this.storyId = storyId;
	}

	@Override
	public String getMessage() {
		return "User:" + voter + " have not voted for story: " + storyId;
	}
}
