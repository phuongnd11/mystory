package com.inspireon.chuyentrolinhtinh.persistence.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.inspireon.chuyentrolinhtinh.model.domain.post.Post;
import com.inspireon.chuyentrolinhtinh.model.domain.post.PostRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.post.Tag;
import com.inspireon.chuyentrolinhtinh.persistence.BaseRepoImpl;
import com.inspireon.chuyentrolinhtinh.web.rest.home.viewing.HomeViewCondition;

@Repository
public class PostRepoImpl extends BaseRepoImpl<Post, String> implements PostRepo {

	@Override
	public List<Post> findByOriginalStoryId(String originalStoryId) {
		
		// this function was not nicely implemented because of one bug of Mongo & Spring Data (orOperator)
		// this function will be revise later
		List<Post> siblingStories = new ArrayList<Post>();
		
		Post originalStory = this.find(originalStoryId);
		
		if (originalStory != null) {
			siblingStories.add(originalStory);
		}
		
		siblingStories.addAll(getTemplate().find(new Query()
							.addCriteria(Criteria.where("originalStoryId").is(originalStoryId))
//							.orOperator(Criteria.where("id").is(originalStoryId)))
							.with(new Sort(Sort.Direction.ASC, "submittedDate")), Post.class));
		
		return siblingStories; 
	}
	
	@Override
	public List<Post> findByAuthor(HomeViewCondition condition, String author) {
		return getTemplate().find(new Query()
							.addCriteria(Criteria.where("author").is(author))
							.with(new Sort(Sort.Direction.DESC, "submittedDate"))
							.with(condition), Post.class);
	}
	
	@Override
	public List<Post> findStoriesEligibleToBeOriginalChapter(String byAuthor) {
		return getTemplate().find(new Query()
							.addCriteria(Criteria.where("author").is(byAuthor)
							.andOperator(Criteria.where("originalStoryId").is(StringUtils.EMPTY)))
							.with(new Sort(Sort.Direction.DESC, "submittedDate")), Post.class);
	}

	@Override
	public List<Post> findTopNew(HomeViewCondition condition) {
		if (Tag.ALL.equals(condition.getTag())) {
			return getTemplate().find(new Query()
								.with(new Sort(Sort.Direction.DESC, "submittedDate"))
								.with(condition), Post.class);
		} else {
			return getTemplate().find(new Query()
								.addCriteria(Criteria.where("tag").is(condition.getTag()))
								.with(new Sort(Sort.Direction.DESC, "submittedDate"))
								.with(condition), Post.class);
		}
	}
	
	@Override
	public List<Post> findTopLastCommented(HomeViewCondition condition) {
		if (Tag.ALL.equals(condition.getTag())) {
			return getTemplate().find(new Query()
								.with(new Sort(Sort.Direction.DESC, "lastCommentedTime"))
								.with(condition), Post.class);
		} else {
			return getTemplate().find(new Query()
								.addCriteria(Criteria.where("tag").is(condition.getTag()))
								.with(new Sort(Sort.Direction.DESC, "lastCommentedTime"))
								.with(condition), Post.class);
		}
	}
	
	@Override
	public List<Post> findTopLegend(HomeViewCondition condition) {
		if (Tag.ALL.equals(condition.getTag())) {
			return getTemplate().find(new Query()
								.with(new Sort(Sort.Direction.DESC, "point")) 
								.with(condition), Post.class);
				
		} else {
			return getTemplate().find(new Query()
								.addCriteria(Criteria.where("tag").is(condition.getTag()))
								.with(new Sort(Sort.Direction.DESC, "point")) 
								.with(condition), Post.class);
		}
	}
	
	@Override
	public List<Post> findTopCommented(HomeViewCondition condition) {
		if (Tag.ALL.equals(condition.getTag())) {
			return getTemplate().find(new Query()
								.with(new Sort(Sort.Direction.DESC, "commentCount")) 
								.with(condition), Post.class);
				
		} else {
			return getTemplate().find(new Query()
								.addCriteria(Criteria.where("tag").is(condition.getTag()))
								.with(new Sort(Sort.Direction.DESC, "commentCount")) 
								.with(condition), Post.class);
		}
	}

	@Override
	public List<Post> findRecentStories(HomeViewCondition condition) {
		
		if (Tag.ALL.equals(condition.getTag())) {
			return getTemplate().find(new Query()
								.addCriteria(Criteria.where("submittedDate").gte(condition.getStartDate()).lt(condition.getEndDate())), Post.class);
		} else {
			return getTemplate().find(new Query()
								.addCriteria(Criteria.where("submittedDate").gte(condition.getStartDate()).lt(condition.getEndDate()) 
								.andOperator(Criteria.where("tag").is(condition.getTag()))), Post.class);
		}
	}

	@Override
	public List<Post> findRecentStoriesByAuthor(String author, Date cutOffTime) {	
		
		Criteria criteria = Criteria.where("submittedDate").gte(cutOffTime).and("author").is(author);
		
		return getTemplate().find(new Query().addCriteria(criteria), Post.class);
	}

	@Override
	public Post findByFriendlyUrl(String url) {
		return getTemplate().findOne(new Query().addCriteria(Criteria.where("friendlyUrl").is(url)), Post.class);
	}
}
