package com.inspireon.chuyentrolinhtinh.web.rest.post.telling;

import com.inspireon.chuyentrolinhtinh.model.domain.post.Tag;

public class PostCommand {
	
	private String id;
	
	private String title;
    
    private String content;
    
    private String author;
    
    private String originalStoryId;
    
    private Tag category;

    private String featuredImg;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOriginalStoryId() {
		return originalStoryId;
	}

	public void setOriginalStoryId(String originalStoryId) {
		this.originalStoryId = originalStoryId;
	}

	public Tag getCategory() {
		return category;
	}

	public void setCategory(Tag category) {
		this.category = category;
	}

	public String getFeaturedImg() {
		return featuredImg;
	}

	public void setFeaturedImg(String featuredImg) {
		this.featuredImg = featuredImg;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
