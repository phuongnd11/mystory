package com.inspireon.mystory.model.domain.image;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inspireon.mystory.model.domain.shared.ValueObject;
import com.inspireon.mystory.model.domain.user.MailBox;

public class Size implements ValueObject<Size> {
	private static final long serialVersionUID = -2688673660487768580L;
	
	private int height;
	private int width;
	
	public Size(int width, int height) {
		this.height = height;
		this.width = width;
	}
	
	public int height() {
		return height;
	}
	
	public int width() {
		return width;
	}

	@Override
	public boolean sameValueAs(Size other) {
		return other != null && new EqualsBuilder()
		.append(this.width, other.width)
		.append(this.height, other.height)
		.isEquals();
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		final Size other = (Size) obj;

		return sameValueAs(other);

	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(height)
			.append(width)
			.toHashCode();
	}

	@Override
	public String toString() {
		return new StringBuilder(MailBox.class.getSimpleName())
			.append("[")
			.append("height = ").append(height)
			.append(", width = ").append(width)
			.append("]")
			.toString();
	}	
}
