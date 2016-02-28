package com.inspireon.mystory.persistence.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.inspireon.mystory.model.domain.story.Story;
import com.inspireon.mystory.model.domain.story.StoryRepo;
import com.inspireon.mystory.model.domain.story.Tag;
import com.inspireon.mystory.persistence.BaseRepoImpl;
import com.inspireon.mystory.web.rest.home.viewing.HomeViewCondition;

@Repository
public class StoryRepoImpl extends BaseRepoImpl<Story, String> implements StoryRepo {

	@Override
	public List<Story> findByOriginalStoryId(String originalStoryId) {
		
		// this function was not nicely implemented because of one bug of Mongo & Spring Data (orOperator)
		// this function will be revise later
		List<Story> siblingStories = new ArrayList<Story>();
		
		Story originalStory = this.find(originalStoryId);
		
		if (originalStory != null) {
			siblingStories.add(originalStory);
		}
		
		siblingStories.addAll(getTemplate().find(new Query()
							.addCriteria(Criteria.where("originalStoryId").is(originalStoryId))
//							.orOperator(Criteria.where("id").is(originalStoryId)))
							.with(new Sort(Sort.Direction.ASC, "submittedDate")), Story.class));
		
		return siblingStories; 
	}
	
	@Override
	public List<Story> findByAuthor(HomeViewCondition condition, String author) {
		return getTemplate().find(new Query()
							.addCriteria(Criteria.where("author").is(author))
							.with(new Sort(Sort.Direction.DESC, "submittedDate"))
							.with(condition), Story.class);
	}
	
	@Override
	public List<Story> findStoriesEligibleToBeOriginalChapter(String byAuthor) {
		return getTemplate().find(new Query()
							.addCriteria(Criteria.where("author").is(byAuthor)
							.andOperator(Criteria.where("originalStoryId").is(StringUtils.EMPTY)))
							.with(new Sort(Sort.Direction.DESC, "submittedDate")), Story.class);
	}

	@Override
	public List<Story> findTopNew(HomeViewCondition condition) {
		if (Tag.ALL.equals(condition.getTag())) {
			return getTemplate().find(new Query()
								.with(new Sort(Sort.Direction.DESC, "submittedDate"))
								.with(condition), Story.class);
		} else {
			return getTemplate().find(new Query()
								.addCriteria(Criteria.where("tag").is(condition.getTag()))
								.with(new Sort(Sort.Direction.DESC, "submittedDate"))
								.with(condition), Story.class);
		}
	}
	
	@Override
	public List<Story> findTopLastCommented(HomeViewCondition condition) {
		if (Tag.ALL.equals(condition.getTag())) {
			return getTemplate().find(new Query()
								.with(new Sort(Sort.Direction.DESC, "lastCommentedTime"))
								.with(condition), Story.class);
		} else {
			return getTemplate().find(new Query()
								.addCriteria(Criteria.where("tag").is(condition.getTag()))
								.with(new Sort(Sort.Direction.DESC, "lastCommentedTime"))
								.with(condition), Story.class);
		}
	}
	
	@Override
	public List<Story> findTopLegend(HomeViewCondition condition) {
		if (Tag.ALL.equals(condition.getTag())) {
			return getTemplate().find(new Query()
								.with(new Sort(Sort.Direction.DESC, "point")) 
								.with(condition), Story.class);
				
		} else {
			return getTemplate().find(new Query()
								.addCriteria(Criteria.where("tag").is(condition.getTag()))
								.with(new Sort(Sort.Direction.DESC, "point")) 
								.with(condition), Story.class);
		}
	}
	
	@Override
	public List<Story> findTopCommented(HomeViewCondition condition) {
		if (Tag.ALL.equals(condition.getTag())) {
			return getTemplate().find(new Query()
								.with(new Sort(Sort.Direction.DESC, "commentCount")) 
								.with(condition), Story.class);
				
		} else {
			return getTemplate().find(new Query()
								.addCriteria(Criteria.where("tag").is(condition.getTag()))
								.with(new Sort(Sort.Direction.DESC, "commentCount")) 
								.with(condition), Story.class);
		}
	}

	@Override
	public List<Story> findRecentStories(HomeViewCondition condition) {
		
		if (Tag.ALL.equals(condition.getTag())) {
			return getTemplate().find(new Query()
								.addCriteria(Criteria.where("submittedDate").gte(condition.getStartDate()).lt(condition.getEndDate())), Story.class);
		} else {
			return getTemplate().find(new Query()
								.addCriteria(Criteria.where("submittedDate").gte(condition.getStartDate()).lt(condition.getEndDate()) 
								.andOperator(Criteria.where("tag").is(condition.getTag()))), Story.class);
		}
	}

	@Override
	public List<Story> findRecentStoriesByAuthor(String author, Date cutOffTime) {	
		
		Criteria criteria = Criteria.where("submittedDate").gte(cutOffTime).and("author").is(author);
		
		return getTemplate().find(new Query().addCriteria(criteria), Story.class);
	}
}
