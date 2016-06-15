package com.inspireon.chuyentrolinhtinh.web.rest.story.reading;

import com.inspireon.chuyentrolinhtinh.common.util.ImageUtils;
import com.inspireon.chuyentrolinhtinh.model.domain.user.User;

public final class AuthorViewAdapter {
	private User author;
	private String viewer;
	
	public AuthorViewAdapter(User author, String viewer) {
		this.author = author;
		this.viewer = viewer;
	}
	public String getName() {
		return author.username();
	}
	
	public int getNumberOfStories() {
		return author.contribution().numberOfStories();
	}

	public int getNumberOfComments() {
		return author.contribution().numberOfComments();
	}

	public int getPoints() {
		return author.contribution().points();
	}
	
	public String getLargeAvatar() {
		return ImageUtils.getFullImageURL(author.avatar().large());
	}
	
	public String getSmallAvatar() {
		return ImageUtils.getFullImageURL(author.avatar().small());
	}
	
	public int getNumberOfFollowers() {
		return author.followers().size();
	}
	
	public boolean isFollowed() {
		if (viewer != null) {
			return author.isFollowedBy(viewer);
		}
		
		return false;
	} 
}