package com.inspireon.mystory.model.domain.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inspireon.mystory.model.domain.shared.ValueObject;

public class BookmarkStory implements ValueObject<BookmarkStory> {

	private static final long serialVersionUID = -4866158531584580676L;
	
	private String storyId;
	
	public BookmarkStory(String storyId) {
		this.storyId = storyId;
	}

	public String storyId() {
		return storyId;
	}
	
	// ------------------------------ Common logic ----------------------------------- //
	@Override
	public boolean sameValueAs(BookmarkStory other) {
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
		StringBuilder builder = new StringBuilder(BookmarkStory.class.getSimpleName());
		
		builder .append("[storyId = ").append(storyId)
				.append("]");

		return builder.toString();
	}	
}
