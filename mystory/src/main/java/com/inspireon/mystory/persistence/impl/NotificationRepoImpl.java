package com.inspireon.mystory.persistence.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.inspireon.mystory.model.domain.notification.Notification;
import com.inspireon.mystory.model.domain.notification.NotificationRepo;
import com.inspireon.mystory.persistence.BaseRepoImpl;

@Repository
public class NotificationRepoImpl extends BaseRepoImpl<Notification, String> implements NotificationRepo{

	@Override
	public List<Notification> findAllNotification(String userId) {
		
		Criteria criteria = Criteria.where("userId").is(userId);
		
		return getTemplate().find(new Query().addCriteria(criteria), Notification.class);
		
	}
	
	
}
