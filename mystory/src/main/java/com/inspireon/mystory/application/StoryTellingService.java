package com.inspireon.mystory.application;

import com.inspireon.mystory.model.domain.image.ImageGroup;
import com.inspireon.mystory.model.domain.story.Story;
import com.inspireon.mystory.model.domain.story.Tag;

public interface StoryTellingService {
	String tellNewStory(String teller, String title, String content,
			String originalStoryId, ImageGroup featuredImage, Tag tag) throws Exception;
	
	void editStory(String storyId, String editor, String newTitle, String newContent,
			String newOriginalStoryId, ImageGroup newFeaturedImage, Tag newTag) throws Exception;
	
	void closeStory(Story story) throws Exception;
}
