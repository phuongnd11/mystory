package com.inspireon.mystory.application.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inspireon.mystory.application.RecommendationService;
import com.inspireon.mystory.application.StoryRankingService;
import com.inspireon.mystory.model.domain.story.Story;
import com.inspireon.mystory.model.domain.story.StoryRepo;
import com.inspireon.mystory.web.rest.home.viewing.HomeViewCondition;

@Service
public class RecommendationServiceImpl implements RecommendationService {
	
	@Autowired
	public StoryRepo storyRepo;
	
	@Autowired
	public StoryRankingService storyRankingService; 
	
	private static final int START_INDEX = 0;
	
	private static final int END_INDEX = 5;
	
	public List<Story> recommendStories(List<Story> excludedStories) {
		
		HomeViewCondition condition = new HomeViewCondition(HomeViewCondition.DEFAULT_TYPE, HomeViewCondition.NO_TAG, HomeViewCondition.FIRST_PAGE);
		
		List<Story> topHotStories = storyRankingService.getTopHot(condition).subList(START_INDEX, END_INDEX);
		
		List<Story> topLegendStories = storyRepo.findTopLegend(condition).subList(START_INDEX, END_INDEX);
		
		List<Story> topNewStories = storyRepo.findTopLastCommented(condition).subList(START_INDEX, END_INDEX);
		
		List<Story> topCommentedStories = storyRepo.findTopCommented(condition).subList(START_INDEX, END_INDEX);
		
		Set<Story> recommendedSet = new HashSet<Story>(20);
		
		recommendedSet.addAll(topHotStories);
		recommendedSet.addAll(topLegendStories);
		recommendedSet.addAll(topNewStories);
		recommendedSet.addAll(topCommentedStories);
		recommendedSet.removeAll(excludedStories);
		
		List<Story> recommendedStories = new ArrayList<Story>(recommendedSet);
		
		Collections.shuffle(recommendedStories);
		
		return recommendedStories.subList(START_INDEX, END_INDEX);
	}
}
