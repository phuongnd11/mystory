package com.inspireon.mystory.model.domain.newsfeed;

import com.inspireon.mystory.persistence.BaseRepo;

public interface NewsFeedRepo extends BaseRepo<NewsFeed, String> {
		
	NewsFeed findCurrentNewsFeed(String username);
	
	NewsFeed findNewsFeedInSpecifyMonth(String username, String month);
}
