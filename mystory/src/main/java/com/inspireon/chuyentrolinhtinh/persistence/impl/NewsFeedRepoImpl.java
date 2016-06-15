package com.inspireon.chuyentrolinhtinh.persistence.impl;

import java.util.Date;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.inspireon.chuyentrolinhtinh.common.util.DateUtils;
import com.inspireon.chuyentrolinhtinh.model.domain.newsfeed.NewsFeed;
import com.inspireon.chuyentrolinhtinh.model.domain.newsfeed.NewsFeedRepo;
import com.inspireon.chuyentrolinhtinh.persistence.BaseRepoImpl;

@Repository
public class NewsFeedRepoImpl extends BaseRepoImpl<NewsFeed, String> implements NewsFeedRepo{

	@Override
	public NewsFeed findCurrentNewsFeed(String username) {
		
		String currentMonth = DateUtils.dateToMonthFormat(new Date());
		
		return getTemplate().findOne(new Query()
							.addCriteria(Criteria.where("month").is(currentMonth)
							.andOperator(Criteria.where("username").is(username))), NewsFeed.class);
	}

	@Override
	public NewsFeed findNewsFeedInSpecifyMonth(String username, String month) {
		
		return getTemplate().findOne(new Query()
							.addCriteria(Criteria.where("month").is(month)
							.andOperator(Criteria.where("username").is(username))), NewsFeed.class);
	}
}
