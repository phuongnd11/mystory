package com.inspireon.mystory.persistence.impl;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.inspireon.mystory.common.util.DateUtils;
import com.inspireon.mystory.model.domain.wall.Wall;
import com.inspireon.mystory.model.domain.wall.WallRepo;
import com.inspireon.mystory.persistence.BaseRepoImpl;

@Repository
public class WallRepoImpl extends BaseRepoImpl<Wall, String> implements WallRepo{

	@Override
	public Wall findCurrentWall(String username) {
		return getTemplate().findOne(new Query()
							.addCriteria(Criteria.where("month").is(DateUtils.dateToMonthFormat(new Date()))
							.andOperator(Criteria.where("username").is(username))), Wall.class);
	}
	
	@Override
	public List<Wall> findWallsInSpecifyMonth(String username, String month) {
		return getTemplate().find(new Query()
							.addCriteria(Criteria.where("month").lte(month)
							.andOperator(Criteria.where("username").is(username)))
							.with(new Sort(Sort.Direction.DESC, "month")), Wall.class);
	}
}

