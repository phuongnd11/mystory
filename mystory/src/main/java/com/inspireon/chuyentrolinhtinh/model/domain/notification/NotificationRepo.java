package com.inspireon.chuyentrolinhtinh.model.domain.notification;

import java.util.List;

import com.inspireon.chuyentrolinhtinh.persistence.BaseRepo;

public interface NotificationRepo extends BaseRepo<Notification, String>{

	
	List<Notification> findAllNotification(String userId);
	
}
