package com.inspireon.mystory.model.domain.notification;

import java.util.List;

import com.inspireon.mystory.persistence.BaseRepo;

public interface NotificationRepo extends BaseRepo<Notification, String>{

	
	List<Notification> findAllNotification(String userId);
	
}
