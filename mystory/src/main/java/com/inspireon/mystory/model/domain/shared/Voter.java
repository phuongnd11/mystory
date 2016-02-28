package com.inspireon.mystory.model.domain.shared;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Voter implements ValueObject<Voter> {

	private static final long serialVersionUID = -2520031868730197229L;

	// ------------------------------ Properties ----------------------------- //
	// username of the voter
	private String voter;
	
	// ---------------------------------- Constructors --------------------------------- //
	public Voter(String voter) {
		Validate.notEmpty(voter);
		this.voter = voter;
	}
	
	// ------------------------------ Business logic --------------------------------- //
	public String voter() {
		return voter;
	}
	
	// ------------------------------ Common logic ----------------------------------- //
	@Override
	public boolean sameValueAs(Voter other) {
		return other != null && new EqualsBuilder()
				.append(this.voter, other.voter)
			    .isEquals();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final Voter other = (Voter) object;
		return sameValueAs(other);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(voter)
				.toHashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(Voter.class.getSimpleName());
		
		builder .append(", voter = ").append(voter)
				.append("]");

		return builder.toString();
	}
}
