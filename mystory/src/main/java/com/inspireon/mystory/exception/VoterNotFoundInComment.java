package com.inspireon.mystory.exception;

public class VoterNotFoundInComment extends Exception {
	
	private static final long serialVersionUID = -1761971285540919699L;
	
	private final String voter;
	private final String commentId;
	
	public VoterNotFoundInComment(final String voter, final String commentId) {
		this.voter = voter;
		this.commentId = commentId;
	}

	@Override
	public String getMessage() {
		return "User:" + voter + " have not voted for comment: " + commentId;
	}
}
