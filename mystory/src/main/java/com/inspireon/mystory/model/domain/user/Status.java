package com.inspireon.mystory.model.domain.user;

import com.inspireon.mystory.model.domain.shared.ValueObject;

public enum Status implements ValueObject<Status> {
	// still to be defined
	ACTIVE, INACTIVE, BANNED; 

	@Override
	public boolean sameValueAs(final Status other) {		
		return this.equals(other);
	}
}
