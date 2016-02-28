/*
 * a class for user's contribution and awards 
 */
package com.inspireon.mystory.model.domain.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inspireon.mystory.model.domain.shared.ValueObject;

public class Contribution implements ValueObject<Contribution> {
	
	private static final long serialVersionUID = -8176417813473830499L;

	private static final Contribution ZERO = new Contribution(0, 0, 0);
	
	// ------------------------------ Properties ----------------------------- //
	private int numberOfStories;
	private int numberOfComments;
    private int points;
    
	// ---------------------------------- Constructors --------------------------------- //
	public Contribution(int numberOfStories, int numberOfComments, int points) {
		this.numberOfStories = numberOfStories;
		this.numberOfComments = numberOfComments;
		this.points = points;
	}
    
	// ---------------------------------- Business logic --------------------------------- //

	public static Contribution zeroContribution() {
		return ZERO;
	}

	public int numberOfStories() {
		return numberOfStories;
	}

	public int numberOfComments() {
		return numberOfComments;
	}

	public int points() {
		return points;
	}

	// ---------------------------------- Common logic --------------------------------- //
	@Override
	public boolean sameValueAs(Contribution other) {
		return other != null && new EqualsBuilder()
				.append(this.numberOfStories, other.numberOfStories)
				.append(this.numberOfComments, other.numberOfComments)
				.append(this.points, other.points)
				.isEquals();
			
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		final Contribution other = (Contribution) obj;

		return sameValueAs(other);
	}


	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(numberOfStories)
				.append(numberOfComments)
				.append(points)
				.toHashCode();
	}
	
	@Override
	public String toString() {
		return new StringBuilder(Contribution.class.getSimpleName())
				.append("[")
				.append("numberOfStories = ").append(numberOfStories)
				.append(", numberOfComments = ").append(numberOfComments)
				.append(", points = ").append(points)
				.append("]")
				.toString();
	}
}
