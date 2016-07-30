package com.inspireon.chuyentrolinhtinh.application;

import com.inspireon.chuyentrolinhtinh.model.domain.image.ImageGroup;
import com.inspireon.chuyentrolinhtinh.model.domain.post.Post;
import com.inspireon.chuyentrolinhtinh.model.domain.post.Tag;

public interface PostTellingService {
	String tellNewStory(String teller, String title, String content,
			String originalStoryId, ImageGroup featuredImage, Tag tag) throws Exception;
	
	void editStory(String storyId, String editor, String newTitle, String newContent,
			String newOriginalStoryId, ImageGroup newFeaturedImage, Tag newTag) throws Exception;
	
	void closeStory(Post story) throws Exception;
}