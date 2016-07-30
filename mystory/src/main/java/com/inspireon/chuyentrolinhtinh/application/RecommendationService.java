package com.inspireon.chuyentrolinhtinh.application;

import java.util.List;

import com.inspireon.chuyentrolinhtinh.model.domain.post.Post;

public interface RecommendationService {
		
	List<Post> recommendStories(List<Post> excludedStories);
	
}
