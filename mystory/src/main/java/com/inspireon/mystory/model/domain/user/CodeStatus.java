package com.inspireon.mystory.model.domain.user;

import com.inspireon.mystory.model.domain.shared.ValueObject;

public enum CodeStatus implements ValueObject<Status>{
	
	NEW, SENT, TAKEN;
	
	@Override
	public boolean sameValueAs(final Status other) {		
		return this.equals(other);
	}
}
