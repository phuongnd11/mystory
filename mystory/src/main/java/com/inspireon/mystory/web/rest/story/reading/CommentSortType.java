package com.inspireon.mystory.web.rest.story.reading;

import com.inspireon.mystory.model.domain.shared.ValueObject;

public enum CommentSortType implements ValueObject<CommentSortType> {
	TOP, ALL;
	
	@Override
	public boolean sameValueAs(final CommentSortType other) {
		return this.equals(other);
	}
}
