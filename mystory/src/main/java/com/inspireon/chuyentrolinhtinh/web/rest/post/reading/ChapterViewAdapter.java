package com.inspireon.chuyentrolinhtinh.web.rest.post.reading;

import com.inspireon.chuyentrolinhtinh.model.domain.post.Post;

public final class ChapterViewAdapter {
	private Post story;
	
	public ChapterViewAdapter(Post story) {
		this.story = story;
	}
	
	public String getId() {
		return story.id();
	}

	public String getTitle() {
		return story.title();
	}
}