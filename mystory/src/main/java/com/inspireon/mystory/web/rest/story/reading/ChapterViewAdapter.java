package com.inspireon.mystory.web.rest.story.reading;

import com.inspireon.mystory.model.domain.story.Story;

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