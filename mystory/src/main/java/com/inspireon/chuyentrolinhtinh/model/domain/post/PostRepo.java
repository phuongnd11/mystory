package com.inspireon.chuyentrolinhtinh.model.domain.post;

import java.util.Date;
import java.util.List;

import com.inspireon.chuyentrolinhtinh.persistence.BaseRepo;
import com.inspireon.chuyentrolinhtinh.web.rest.home.viewing.HomeViewCondition;

public interface PostRepo extends BaseRepo<Post, String>{
	
	List<Post> findByOriginalStoryId(String originalStoryId);
	
	List<Post> findByAuthor(HomeViewCondition condition, String author);
	
	List<Post> findStoriesEligibleToBeOriginalChapter(String byAuthor);
	
	List<Post> findTopNew(HomeViewCondition condition);
	
	List<Post> findTopLegend(HomeViewCondition condition);
	
	List<Post> findTopCommented(HomeViewCondition condition);
	
	List<Post> findRecentStories(HomeViewCondition condition);
	
	List<Post> findRecentStoriesByAuthor(String author, Date cutOffTime);
	
	List<Post> findTopLastCommented(HomeViewCondition condition);
	
	Post findByFriendlyUrl(String url);
}
