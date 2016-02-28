package com.inspireon.mystory.model.domain.user;

import com.inspireon.mystory.model.domain.shared.ValueObject;

public enum Permission implements ValueObject<Permission>{
	
	VIEW_POST, SUBMIT_POST, MOVE_POST, HIDE_POST, BAN_USER, UNBAN_USER, SET_ROLE;

	@Override
	public boolean sameValueAs(final Permission other) {
		return this.equals(other);
	}
	
}
