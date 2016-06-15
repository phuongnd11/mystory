package com.inspireon.chuyentrolinhtinh.persistence.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.inspireon.chuyentrolinhtinh.model.domain.notification.Notification;
import com.inspireon.chuyentrolinhtinh.model.domain.notification.NotificationRepo;
import com.inspireon.chuyentrolinhtinh.persistence.BaseRepoImpl;

@Repository
public class NotificationRepoImpl extends BaseRepoImpl<Notification, String> implements NotificationRepo{

	@Override
	public List<Notification> findAllNotification(String userId) {
		
		Criteria criteria = Criteria.where("userId").is(userId);
		
		return getTemplate().find(new Query().addCriteria(criteria), Notification.class);
		
	}
	
	
}
