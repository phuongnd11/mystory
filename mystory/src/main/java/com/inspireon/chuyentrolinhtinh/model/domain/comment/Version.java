package com.inspireon.chuyentrolinhtinh.model.domain.comment;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inspireon.chuyentrolinhtinh.model.domain.shared.ValueObject;

public class Version implements ValueObject<Version>{
	
	private static final long serialVersionUID = -7657326546989384862L;

	// ------------------------------ Properties ----------------------------- //
	private String editedBy;
	
	private Date editedTime;
	
	private String content;
	
	// ---------------------------------- Constructors --------------------------------- //
	public Version(String content, String editedBy, Date editedTime) {		
		this.editedBy = editedBy;
		this.editedTime = editedTime;
		this.content = content;
	}

	// ------------------------------ Business logic --------------------------------- //
	public String editedBy() {
		return editedBy;
	}

	public Date editedTime() {
		return editedTime;
	}

	public String content() {
		return content;
	}
	
	// ------------------------------ Common logic ----------------------------------- //
	@Override
	public boolean sameValueAs(Version other) {
		return other != null && new EqualsBuilder()
				.append(this.editedBy, other.editedBy)
				.append(this.editedTime, other.editedTime)
			    .append(this.content, other.content)
			    .isEquals();
	}		
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(editedBy)
				.append(editedTime)
				.append(content)
				.toHashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(Version.class.getSimpleName());
		
		builder .append("[editedBy = ").append(editedBy)
				.append(", editedTime = ").append(editedTime)
				.append(", content = ").append(content)
				.append("]");

		return builder.toString();
	}	
}
