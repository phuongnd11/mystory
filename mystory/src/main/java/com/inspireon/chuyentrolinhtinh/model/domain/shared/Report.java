package com.inspireon.chuyentrolinhtinh.model.domain.shared;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Report implements ValueObject<Report> {
	
	private static final long serialVersionUID = -3374569704479092815L;

	// ------------------------------ Properties ----------------------------- //
	private String reporter;
	
	private String message;
	
	// ---------------------------------- Constructors --------------------------------- //
	public Report(String reporter, String message) {
		this.reporter = reporter;
		this.message = message;
	}
	
	// ------------------------------ Business logic --------------------------------- //
	public String reporter() {
		return reporter;
	}

	public String message() {
		return message;
	}
	
	// ------------------------------ Common logic ----------------------------------- //
	@Override
	public boolean sameValueAs(Report other) {
		return other != null && new EqualsBuilder()
				.append(this.reporter, other.reporter)
				.append(this.message, other.message)
			    .isEquals();
	}		

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(reporter)
				.append(message)
				.toHashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(Report.class.getSimpleName());
		
		builder .append("[reporter = ").append(reporter)
				.append(", message = ").append(message)
				.append("]");

		return builder.toString();
	}
}
