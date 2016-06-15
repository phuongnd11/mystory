package com.inspireon.chuyentrolinhtinh.application;

import java.util.List;

import com.inspireon.chuyentrolinhtinh.model.domain.story.Story;
import com.inspireon.chuyentrolinhtinh.web.rest.home.viewing.HomeViewCondition;

public interface StoryRankingService {
	
	List<Story> getTopHot(HomeViewCondition condition);
	
	List<Story> getTopControversial(HomeViewCondition condition);
}
