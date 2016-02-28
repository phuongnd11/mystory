package com.inspireon.mystory.exception;

public class UserAlreadyVoteCommentException extends Exception {

	private static final long serialVersionUID = 8420443771402104811L;
	
	private final String voter;
	private final String commentId;
	
	public UserAlreadyVoteCommentException(final String voter, final String commentId) {
		this.voter = voter;
		this.commentId = commentId;
	}

	@Override
	public String getMessage() {
		return "Comment with comment id: " + commentId + "was already voted by user: " + voter;
	}
}
