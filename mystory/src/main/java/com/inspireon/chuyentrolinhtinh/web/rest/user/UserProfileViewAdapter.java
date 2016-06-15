package com.inspireon.chuyentrolinhtinh.web.rest.user;

import java.io.Serializable;


public class UserProfileViewAdapter implements Serializable {
	private static final long serialVersionUID = 3181300015879038188L;
	private UserTab currentTab;
	private String avatarImage;
	private Object data;
	
	public UserProfileViewAdapter(UserTab currentTab, String avatarImage) {
		this.currentTab = currentTab;
		this.avatarImage = avatarImage;
	}
	
	public UserProfileViewAdapter(UserTab currentTab, String avatarImage, Object data) {
		this.currentTab = currentTab;
		this.data = data;
		this.avatarImage = avatarImage;
	}

	public UserTab getCurrentTab() {
		return currentTab;
	}

	public Object getData() {
		return data;
	}
	
	public String getAvatarImage() {
		return avatarImage;
	}
}

enum UserTab {
	PROFILE, MAILBOX, NOTIFICATION;
}