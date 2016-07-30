package com.inspireon.chuyentrolinhtinh.model.domain.user;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inspireon.chuyentrolinhtinh.model.domain.post.Tag;
import com.inspireon.chuyentrolinhtinh.model.domain.shared.ValueObject;

public class TagSetting implements ValueObject<TagSetting> {
	
	private static final long serialVersionUID = -8536281457508402615L;

	private static final TagSetting DEFAULT = new TagSetting(Tag.defaultDisplayTags());
	
	// ------------------------------ Properties ----------------------------- //
	private List<Tag> tags;
    
	// ---------------------------------- Constructors --------------------------------- //
	public TagSetting(final List<Tag> tags) {
		this.tags = tags;
	}
	
	// ---------------------------------- Business logic --------------------------------- //
	public static TagSetting defaultTagSetting() {
		return DEFAULT;
	}

	public List<Tag> tags() {
		return tags;
	}

	// ---------------------------------- Common logic --------------------------------- //
	@Override
	public boolean sameValueAs(TagSetting other) {
		return other != null && new EqualsBuilder()
				.append(this.tags, other.tags)
				.isEquals();
			
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		final TagSetting other = (TagSetting) obj;

		return sameValueAs(other);
	}


	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(tags)
				.toHashCode();
	}
	
	@Override
	public String toString() {
		return new StringBuilder(TagSetting.class.getSimpleName())
				.append("[")
				.append("tags = ").append(tags)
				.append("]")
				.toString();
	}
}
