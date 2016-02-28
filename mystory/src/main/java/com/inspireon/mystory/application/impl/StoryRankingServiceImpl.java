package com.inspireon.mystory.application.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inspireon.mystory.application.StoryRankingService;
import com.inspireon.mystory.model.domain.home.HomeRepo;
import com.inspireon.mystory.model.domain.story.Story;
import com.inspireon.mystory.model.domain.story.StoryRepo;
import com.inspireon.mystory.web.rest.home.viewing.HomeViewCondition;

@Service
public class StoryRankingServiceImpl implements StoryRankingService {

	@Autowired
	StoryRepo storyRepo;
	
	@Autowired
	HomeRepo homeRepo;
	
	private List<Story> rankHot(List<Story> stories) {
		
        Collections.sort(stories, Collections.reverseOrder(new Comparator<Story>() {
            public int compare(final Story a, final Story b) {
            	return (a.hotScore() > b.hotScore()) ? 1 : (a.hotScore() < b.hotScore()) ? -1 : 0;
            }
        }));
        
        return stories;
	}
	
	private List<Story> rankControversial(List<Story> stories) {
		
        Collections.sort(stories, Collections.reverseOrder(new Comparator<Story>() {
            public int compare(final Story a, final Story b) {
            	return (a.controversialScore() > b.controversialScore()) ? 1 : (a.controversialScore() < b.controversialScore()) ? -1 : 0;
            }
        }));
        
        return stories;
	}
	
	@SuppressWarnings("unused")
	private List<Story> rankNew(List<Story> stories) {
		
        Collections.sort(stories, Collections.reverseOrder(new Comparator<Story>() {
            public int compare(final Story a, final Story b) {
            	return (a.submittedDate().compareTo(b.submittedDate()));
            }
        }));
        
        return stories;
	}
	
	@SuppressWarnings("unused")
	private List<Story> rankLegend(List<Story> stories) {
		
        Collections.sort(stories, Collections.reverseOrder(new Comparator<Story>() {
            public int compare(final Story a, final Story b) {
            	return (a.point() > b.point()) ? 1 : (a.point() < b.point()) ? -1 : 0;
            }
        }));
        
        return stories;
	}

	@Override
	public List<Story> getTopHot(HomeViewCondition condition) {
		
		List<Story> recentStories = storyRepo.findRecentStories(condition);
		int numberOfStories = recentStories.size();
		int endIndex = condition.getEndIndex();
		
		if (numberOfStories < condition.getStartIndex()) {
			return Collections.emptyList();
		}
			 
		if (numberOfStories < endIndex) {
			endIndex = numberOfStories;
		}
		
		List<Story> recentStoriesSortedByHot = rankHot(recentStories);
				
		return recentStoriesSortedByHot.subList(condition.getStartIndex(), endIndex);
	}

	@Override
	public List<Story> getTopControversial(HomeViewCondition condition) {
		
		List<Story> recentStories = storyRepo.findRecentStories(condition);
		int numberOfStories = recentStories.size();
		int endIndex = condition.getEndIndex();
		
		if (numberOfStories < condition.getStartIndex()) {
			return Collections.emptyList();
		}
			 
		if (numberOfStories < endIndex) {
			endIndex = numberOfStories;
		}
		
		List<Story> recentStoriesSortedByControversial = rankControversial(recentStories);
		
		return recentStoriesSortedByControversial.subList(condition.getStartIndex(), endIndex);
	}
}
