package com.inspireon.mystory.persistence.impl;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.inspireon.mystory.common.util.DateUtils;
import com.inspireon.mystory.model.domain.home.Home;
import com.inspireon.mystory.model.domain.home.HomeRepo;
import com.inspireon.mystory.persistence.BaseRepoImpl;

@Repository
public class HomeRepoImpl extends BaseRepoImpl<Home, String> implements HomeRepo {

	@Override
	public List<Home> findRecentHomes() {
		
		Date currentDate = new Date();
		
		String currentDay = DateUtils.dateToDayFormat(currentDate);
		
		String startComparedDay = DateUtils.dateToDayFormat(DateUtils.substractDate(currentDate, 7));
		
		return getTemplate().find(new Query()
							.addCriteria(Criteria.where("day").gte(startComparedDay).and("startDate").lte(currentDay)), Home.class);
	}

	@Override
	public Home findHomeOfToday() {
		return getTemplate().findOne(new Query()
							.addCriteria(Criteria.where("day").is(DateUtils.dateToDayFormat(new Date()))), Home.class);
	}
}
