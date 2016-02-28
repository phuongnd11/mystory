package com.inspireon.mystory.application;

import java.util.List;

import com.inspireon.mystory.model.domain.story.Story;
import com.inspireon.mystory.web.rest.home.viewing.HomeViewCondition;

public interface StoryRankingService {
	
	List<Story> getTopHot(HomeViewCondition condition);
	
	List<Story> getTopControversial(HomeViewCondition condition);
}
