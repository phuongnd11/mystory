package com.inspireon.chuyentrolinhtinh.model.domain.story;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inspireon.chuyentrolinhtinh.model.domain.shared.ValueObject;

public class ChapterStory implements ValueObject<ChapterStory>{
	
	private static final long serialVersionUID = 5541450970269892457L;

	// ------------------------------ Properties ----------------------------- //
	private String storyId;
	
	private String title;
	
	// ---------------------------------- Constructors --------------------------------- //
	public ChapterStory(Story story) {
		this.storyId = story.id();
		this.title = story.title();
	}
	
	// ------------------------------ Business logic --------------------------------- //
	public String storyId() {
		return storyId;
	}

	public String title() {
		return title;
	}
	
	// ------------------------------ Common logic ----------------------------------- //
	@Override
	public boolean sameValueAs(ChapterStory other) {
		return other != null && new EqualsBuilder()
				.append(this.storyId, other.storyId)
				.append(this.title, other.title)
			    .isEquals();
	}		

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(storyId)
				.append(title)
				.toHashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(ChapterStory.class.getSimpleName());
		
		builder .append("[storyId = ").append(storyId)
				.append(", title = ").append(title)
				.append("]");

		return builder.toString();
	}
}
