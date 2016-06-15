package com.inspireon.chuyentrolinhtinh.web.rest.voting;

import com.inspireon.chuyentrolinhtinh.model.domain.shared.ValueObject;

public class VoteCommand {
	
	private String candidateId;
	
	private Type type;
	
	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public enum Type implements ValueObject<Type> {
		UPVOTE_STORY, DOWNVOTE_STORY, UN_UPVOTE_STORY, UN_DOWNVOTE_STORY,
		UPVOTE_COMMENT, DOWNVOTE_COMMENT, UN_UPVOTE_COMMENT, UN_DOWNVOTE_COMMENT;

		@Override
		public boolean sameValueAs(final Type other) {
			return this.equals(other);
		}
	}
}
