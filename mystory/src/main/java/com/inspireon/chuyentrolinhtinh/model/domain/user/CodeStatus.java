package com.inspireon.chuyentrolinhtinh.model.domain.user;

import com.inspireon.chuyentrolinhtinh.model.domain.shared.ValueObject;

public enum CodeStatus implements ValueObject<Status>{
	
	NEW, SENT, TAKEN;
	
	@Override
	public boolean sameValueAs(final Status other) {		
		return this.equals(other);
	}
}
