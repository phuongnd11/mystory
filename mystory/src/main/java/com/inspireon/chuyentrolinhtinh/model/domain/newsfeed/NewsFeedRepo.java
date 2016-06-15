package com.inspireon.chuyentrolinhtinh.model.domain.newsfeed;

import com.inspireon.chuyentrolinhtinh.persistence.BaseRepo;

public interface NewsFeedRepo extends BaseRepo<NewsFeed, String> {
		
	NewsFeed findCurrentNewsFeed(String username);
	
	NewsFeed findNewsFeedInSpecifyMonth(String username, String month);
}
