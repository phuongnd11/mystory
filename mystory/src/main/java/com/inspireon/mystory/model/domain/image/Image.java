package com.inspireon.mystory.model.domain.image;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inspireon.mystory.model.domain.shared.ValueObject;
import com.inspireon.mystory.model.domain.user.MailBox;

public class Image implements ValueObject<Image>{
	
	private static final long serialVersionUID = -7660864373287323345L;
	
	// ------------------------------ Properties ------------------------------------- //
	private String name;
	private String path;
	private Size size;
	
	// ------------------------------ Constructors ----------------------------------- //
	public Image(String name, String path, Size size) {
		this.name = name;
		this.path = path;
		this.size = size;
	}
	
	// ------------------------------ Business logic --------------------------------- //	
	public String name() {
		return name;
	}	
	
	public String path() {
		return path;
	}
	
	public Size size() {
		return size;
	}	
			
	// -------------------------------- Common method ---------------------------------//
	@Override
	public boolean sameValueAs(Image other) {
		return other != null && new EqualsBuilder()
		.append(this.name, other.name)
		.append(this.path, other.path)		
		.isEquals();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final Image other = (Image) object;
		return sameValueAs(other);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(name)
			.append(path)
			.toHashCode();
	}

	@Override
	public String toString() {
		return new StringBuilder(MailBox.class.getSimpleName())
			.append("[")
			.append("name = ").append(name)
			.append(", path = ").append(path)
			.append(", size = ").append(size)
			.append("]")
			.toString();
	}
}
