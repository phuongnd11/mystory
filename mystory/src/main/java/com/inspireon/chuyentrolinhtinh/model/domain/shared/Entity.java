package com.inspireon.chuyentrolinhtinh.model.domain.shared;

import org.springframework.data.annotation.Id;

public abstract class Entity<T> {

  /**
   * Entities compare by identity, not by attributes.
   *
   * @param other The other entity.
   * @return true if the identities are the same, regardles of other attributes.
   */
	@Id
	private String id;
	
	public String id() {
		return id;
	}
	
	public abstract boolean sameIdentityAs(T other);
}
