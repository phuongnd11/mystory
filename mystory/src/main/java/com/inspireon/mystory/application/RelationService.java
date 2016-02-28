package com.inspireon.mystory.application;

public interface RelationService {
		
	void followAStory(String userId, String storyId);
	
	void followAStoryTeller(String userId, String followeeId);
	
	void removeRelation(String userId, String ObjectId);
}
