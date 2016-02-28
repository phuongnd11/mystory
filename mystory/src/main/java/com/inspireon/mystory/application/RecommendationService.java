package com.inspireon.mystory.application;

import java.util.List;

import com.inspireon.mystory.model.domain.story.Story;

public interface RecommendationService {
		
	List<Story> recommendStories(List<Story> excludedStories);
	
}
