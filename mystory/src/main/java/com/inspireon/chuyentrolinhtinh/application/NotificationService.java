package com.inspireon.chuyentrolinhtinh.application;

import java.util.List;

import com.inspireon.chuyentrolinhtinh.model.domain.notification.Notification;

public interface NotificationService {

	List<Notification> getNewNotificationForUser(String userId);

}
