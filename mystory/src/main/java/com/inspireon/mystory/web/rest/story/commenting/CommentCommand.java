package com.inspireon.mystory.web.rest.story.commenting;

public class CommentCommand {
    
	private String id;
	
    private String content;
    
    private String storyId;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStoryId() {
		return storyId;
	}

	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}
}
