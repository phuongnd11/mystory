package com.inspireon.mystory.web.rest.category;

import java.io.Serializable;

import com.inspireon.mystory.model.domain.story.Tag;

public class CategoryViewAdapter implements Serializable{
	
	private static final long serialVersionUID = -6491403549561970691L;
	
	private Tag tag;
	
	private Tag selectedTag;
	
	public CategoryViewAdapter(Tag tag, Tag selectedTag) {
		this.tag = tag;
		this.selectedTag = selectedTag;
	}
	
	public boolean isSelected() {
		return tag.equals(selectedTag);
	}
	
	public String getName() {
		return tag.name().toLowerCase();
	}
	
	public String getDisplayName() {
		return tag.displayName();
	}
	
	public String getDescription() {
		return tag.description();
	}
}
