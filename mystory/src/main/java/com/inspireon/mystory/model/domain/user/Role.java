package com.inspireon.mystory.model.domain.user;

import com.inspireon.mystory.model.domain.shared.ValueObject;

public enum Role implements ValueObject<Role> {
	
	GUEST, USER, MOD, ADMIN;

	@Override
	public boolean sameValueAs(final Role other) {		
		return this.equals(other);
	}
	
	public static Role getByKey(String key) {
		for (Role role : values()) {
			if (role.toString().equalsIgnoreCase(key))
				return role;
		}
		return null;
	}
}
