package com.inspireon.mystory.model.domain.comment;

import com.inspireon.mystory.model.domain.shared.ValueObject;

public enum Status implements ValueObject<Status> {
	// still to be defined
	ACTIVE, HIDE, REPORTED; 

	@Override
	public boolean sameValueAs(final Status other) {		
		return this.equals(other);
	}
}
