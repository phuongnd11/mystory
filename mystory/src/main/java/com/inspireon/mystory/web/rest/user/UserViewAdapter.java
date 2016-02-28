package com.inspireon.mystory.web.rest.user;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.inspireon.mystory.common.util.DateUtils;
import com.inspireon.mystory.common.util.ImageUtils;
import com.inspireon.mystory.model.domain.user.User;

/**
 * View adapter for displaying information about one user, which other users can see on his/her profile page
 * @author Tung
 *
 */
public class UserViewAdapter implements Serializable {

	private static final long serialVersionUID = 8579225858950462657L;
	
	// Should User implement Serializable interface?  
	private final User user;
	private String viewer;

	public UserViewAdapter(User user) {
		this.user = user;
	}
	
	public UserViewAdapter(User user, String viewer) {
		this.user = user;
		this.viewer = viewer;
	}
	
	public String getUsername() {
		return user.username();
	}
	
	public String getJoinedDate() {
		// return user's joined date with "HH:mm:ss ngày dd/MM/yyyy" format
		return DateUtils.dateToViString(user.joinedDate());
	}
	
	public String getFacebook() {
		boolean isDisplayToOthers = user.setting().facebookSetting().displayToOthers();
		
		if (isDisplayToOthers) {
			// return facebook address of user
			return user.setting().facebookSetting().facebook();
		} else {
			// do not return facebook address
			return StringUtils.EMPTY; 
		}
	}
	
	public String getSlogan() {
		return user.setting().slogan();
	}
	
	public String getLang() {
		return user.setting().lang();
	}
	
	public boolean isAllowPostingActivities() {
		return user.setting().facebookSetting().allowPostingActivities();
	}	
	
	public boolean isDisplayToOthers() {
		return user.setting().facebookSetting().displayToOthers();
	}	
	
	public String getAvatarImage() {
		return ImageUtils.getFullImageURL(user.avatar().large());
	}

	public int getNumberOfStories() {
		return user.contribution().numberOfStories();
	}

	public int getNumberOfComments() {
		return user.contribution().numberOfComments();
	}

	public int getPoints() {
		return user.contribution().points();
	}

	public boolean isFollowed() {
		if (viewer != null) {
			return user.isFollowedBy(viewer);
		}
		
		return false;
	}
	
	public int getNumberOfFollowers() {
		return user.followers().size();
	}
}
