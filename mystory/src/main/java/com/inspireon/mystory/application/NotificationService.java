package com.inspireon.mystory.application;

import java.util.List;

import com.inspireon.mystory.model.domain.notification.Notification;

public interface NotificationService {

	List<Notification> getNewNotificationForUser(String userId);

}
