package com.inspireon.mystory.web.rest.home.viewing;

import java.util.List;

import com.inspireon.mystory.common.util.ImageUtils;
import com.inspireon.mystory.model.domain.user.User;

public class UserPanelViewAdapter {
	
	private User user;
	
	private List<NotificationViewAdapter> notifications;
	
	private int notificationCount;
	
	public UserPanelViewAdapter(User user, List<NotificationViewAdapter> notifications, int notificationCount) {
		this.user = user;
		this.notifications = notifications;
		this.notificationCount = notificationCount;
	}

	public String getName() {
		return user.username();
	}
	
	public int getNumberOfStories() {
		return user.contribution().numberOfStories();
	}

	public int getPoints() {
		return user.contribution().points();
	}
	
	public String getAvatar() {
		return ImageUtils.getFullImageURL(user.avatar().small());
	}
	
	public int getNotificationCount() {
		return notificationCount;
	}
	
	public List<NotificationViewAdapter> getNotifications(){
		return notifications;
	}
	
	public int getNumberOfFollowers() {
		return user.followers().size();
	}
}