package com.inspireon.mystory.model.domain.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inspireon.mystory.model.domain.shared.ValueObject;

public class Follower implements ValueObject<Follower>{

	private static final long serialVersionUID = 3185741979462505293L;
	
	// ------------------------------ Properties ----------------------------- //
	// username of the follower
	private String name;
	
	// ---------------------------------- Constructors --------------------------------- //
	public Follower(String name) {
		this.name = name;
	}
	
	// ------------------------------ Business logic --------------------------------- //
	public String name() {
		return name;
	}
	
	// ------------------------------ Common logic ----------------------------------- //
	@Override
	public boolean sameValueAs(Follower other) {
		return other != null && new EqualsBuilder()
				.append(this.name, other.name)
			    .isEquals();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final Follower other = (Follower) object;
		return sameValueAs(other);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(name)
				.toHashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(Follower.class.getSimpleName());
		
		builder .append(", follower = ").append(name)
				.append("]");

		return builder.toString();
	}
}
