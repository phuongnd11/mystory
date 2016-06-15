package com.inspireon.chuyentrolinhtinh.persistence.impl;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.inspireon.chuyentrolinhtinh.model.domain.image.ImageGroup;
import com.inspireon.chuyentrolinhtinh.model.domain.image.ImageGroupRepo;
import com.inspireon.chuyentrolinhtinh.persistence.BaseRepoImpl;

@Repository
public class ImageGroupRepoImpl extends BaseRepoImpl<ImageGroup, String> implements ImageGroupRepo{

	@Override
	public ImageGroup findByFileName(String fileName) {
		return getTemplate().findOne(
				new Query().addCriteria(Criteria.where("name").is(fileName)), ImageGroup.class);
	}
	
}
