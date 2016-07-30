package com.inspireon.chuyentrolinhtinh.model.domain.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inspireon.chuyentrolinhtinh.model.domain.shared.ValueObject;

public class BookmarkPost implements ValueObject<BookmarkPost> {

	private static final long serialVersionUID = -4866158531584580676L;
	
	private String storyId;
	
	public BookmarkPost(String storyId) {
		this.storyId = storyId;
	}

	public String storyId() {
		return storyId;
	}
	
	// ------------------------------ Common logic ----------------------------------- //
	@Override
	public boolean sameValueAs(BookmarkPost other) {
		return other != null && new EqualsBuilder()
				.append(this.storyId, other.storyId)
			    .isEquals();
	}		
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(storyId)
				.toHashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(BookmarkPost.class.getSimpleName());
		
		builder .append("[storyId = ").append(storyId)
				.append("]");

		return builder.toString();
	}	
}
