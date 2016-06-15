package com.inspireon.chuyentrolinhtinh.application;

import java.util.List;

import com.inspireon.chuyentrolinhtinh.model.domain.story.Story;

public interface RecommendationService {
		
	List<Story> recommendStories(List<Story> excludedStories);
	
}
