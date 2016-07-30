package com.inspireon.chuyentrolinhtinh.web.rest.post.reading;

import com.inspireon.chuyentrolinhtinh.model.domain.shared.ValueObject;

public enum CommentSortType implements ValueObject<CommentSortType> {
	TOP, ALL;
	
	@Override
	public boolean sameValueAs(final CommentSortType other) {
		return this.equals(other);
	}
}
