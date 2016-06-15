package com.inspireon.chuyentrolinhtinh.model.domain.story;

import java.util.Date;
import java.util.List;

import com.inspireon.chuyentrolinhtinh.persistence.BaseRepo;
import com.inspireon.chuyentrolinhtinh.web.rest.home.viewing.HomeViewCondition;

public interface StoryRepo extends BaseRepo<Story, String>{
	
	List<Story> findByOriginalStoryId(String originalStoryId);
	
	List<Story> findByAuthor(HomeViewCondition condition, String author);
	
	List<Story> findStoriesEligibleToBeOriginalChapter(String byAuthor);
	
	List<Story> findTopNew(HomeViewCondition condition);
	
	List<Story> findTopLegend(HomeViewCondition condition);
	
	List<Story> findTopCommented(HomeViewCondition condition);
	
	List<Story> findRecentStories(HomeViewCondition condition);
	
	List<Story> findRecentStoriesByAuthor(String author, Date cutOffTime);
	
	List<Story> findTopLastCommented(HomeViewCondition condition);
	
	Story findByFriendlyUrl(String url);
}
