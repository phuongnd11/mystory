package com.inspireon.chuyentrolinhtinh.web.rest.story.reading;

import com.inspireon.chuyentrolinhtinh.model.domain.story.Story;

public final class ChapterViewAdapter {
	private Story story;
	
	public ChapterViewAdapter(Story story) {
		this.story = story;
	}
	
	public String getId() {
		return story.id();
	}

	public String getTitle() {
		return story.title();
	}
}