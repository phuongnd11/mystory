package com.inspireon.chuyentrolinhtinh.application.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inspireon.chuyentrolinhtinh.application.PostRankingService;
import com.inspireon.chuyentrolinhtinh.model.domain.home.HomeRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.post.Post;
import com.inspireon.chuyentrolinhtinh.model.domain.post.PostRepo;
import com.inspireon.chuyentrolinhtinh.web.rest.home.viewing.HomeViewCondition;

@Service
public class PostRankingServiceImpl implements PostRankingService {

	@Autowired
	PostRepo storyRepo;
	
	@Autowired
	HomeRepo homeRepo;
	
	private List<Post> rankHot(List<Post> stories) {
		
        Collections.sort(stories, Collections.reverseOrder(new Comparator<Post>() {
            public int compare(final Post a, final Post b) {
            	return (a.hotScore() > b.hotScore()) ? 1 : (a.hotScore() < b.hotScore()) ? -1 : 0;
            }
        }));
        
        return stories;
	}
	
	private List<Post> rankControversial(List<Post> stories) {
		
        Collections.sort(stories, Collections.reverseOrder(new Comparator<Post>() {
            public int compare(final Post a, final Post b) {
            	return (a.controversialScore() > b.controversialScore()) ? 1 : (a.controversialScore() < b.controversialScore()) ? -1 : 0;
            }
        }));
        
        return stories;
	}
	
	@SuppressWarnings("unused")
	private List<Post> rankNew(List<Post> stories) {
		
        Collections.sort(stories, Collections.reverseOrder(new Comparator<Post>() {
            public int compare(final Post a, final Post b) {
            	return (a.submittedDate().compareTo(b.submittedDate()));
            }
        }));
        
        return stories;
	}
	
	@SuppressWarnings("unused")
	private List<Post> rankLegend(List<Post> stories) {
		
        Collections.sort(stories, Collections.reverseOrder(new Comparator<Post>() {
            public int compare(final Post a, final Post b) {
            	return (a.point() > b.point()) ? 1 : (a.point() < b.point()) ? -1 : 0;
            }
        }));
        
        return stories;
	}

	@Override
	public List<Post> getTopHot(HomeViewCondition condition) {
		
		List<Post> recentStories = storyRepo.findRecentStories(condition);
		int numberOfStories = recentStories.size();
		int endIndex = condition.getEndIndex();
		
		if (numberOfStories < condition.getStartIndex()) {
			return Collections.emptyList();
		}
			 
		if (numberOfStories < endIndex) {
			endIndex = numberOfStories;
		}
		
		List<Post> recentStoriesSortedByHot = rankHot(recentStories);
				
		return recentStoriesSortedByHot.subList(condition.getStartIndex(), endIndex);
	}

	@Override
	public List<Post> getTopControversial(HomeViewCondition condition) {
		
		List<Post> recentStories = storyRepo.findRecentStories(condition);
		int numberOfStories = recentStories.size();
		int endIndex = condition.getEndIndex();
		
		if (numberOfStories < condition.getStartIndex()) {
			return Collections.emptyList();
		}
			 
		if (numberOfStories < endIndex) {
			endIndex = numberOfStories;
		}
		
		List<Post> recentStoriesSortedByControversial = rankControversial(recentStories);
		
		return recentStoriesSortedByControversial.subList(condition.getStartIndex(), endIndex);
	}
}
