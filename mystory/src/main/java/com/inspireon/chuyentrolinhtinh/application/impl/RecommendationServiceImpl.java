package com.inspireon.chuyentrolinhtinh.application.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inspireon.chuyentrolinhtinh.application.RecommendationService;
import com.inspireon.chuyentrolinhtinh.application.PostRankingService;
import com.inspireon.chuyentrolinhtinh.model.domain.post.Post;
import com.inspireon.chuyentrolinhtinh.model.domain.post.PostRepo;
import com.inspireon.chuyentrolinhtinh.web.rest.home.viewing.HomeViewCondition;

@Service
public class RecommendationServiceImpl implements RecommendationService {
	
	@Autowired
	public PostRepo storyRepo;
	
	@Autowired
	public PostRankingService storyRankingService; 
	
	private static final int START_INDEX = 0;
	
	private static final int END_INDEX = 5;
	
	public List<Post> recommendStories(List<Post> excludedStories) {
		
		HomeViewCondition condition = new HomeViewCondition(HomeViewCondition.DEFAULT_TYPE, HomeViewCondition.NO_TAG, HomeViewCondition.FIRST_PAGE);
		
		List<Post> topHotStories = storyRankingService.getTopHot(condition);
		topHotStories.subList(START_INDEX, topHotStories.size() > END_INDEX ? END_INDEX : topHotStories.size());
		
		List<Post> topLegendStories = storyRepo.findTopLegend(condition);
		topLegendStories.subList(START_INDEX, topLegendStories.size() > END_INDEX ? END_INDEX : topLegendStories.size());
		
		List<Post> topNewStories = storyRepo.findTopLastCommented(condition);
		topNewStories.subList(START_INDEX, topNewStories.size() > END_INDEX ? END_INDEX : topNewStories.size());
		
		List<Post> topCommentedStories = storyRepo.findTopCommented(condition);
		topCommentedStories.subList(START_INDEX, topCommentedStories.size() > END_INDEX ? END_INDEX : topCommentedStories.size());
		
		Set<Post> recommendedSet = new HashSet<Post>(20);
		
		recommendedSet.addAll(topHotStories);
		recommendedSet.addAll(topLegendStories);
		recommendedSet.addAll(topNewStories);
		recommendedSet.addAll(topCommentedStories);
		recommendedSet.removeAll(excludedStories);
		
		List<Post> recommendedStories = new ArrayList<Post>(recommendedSet);
		
		Collections.shuffle(recommendedStories);
		
		return recommendedStories.subList(START_INDEX, recommendedStories.size() > END_INDEX ? END_INDEX : recommendedStories.size());
	}
}
