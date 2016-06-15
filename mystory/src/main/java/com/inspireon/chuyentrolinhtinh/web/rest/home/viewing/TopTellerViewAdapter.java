package com.inspireon.chuyentrolinhtinh.web.rest.home.viewing;

import com.inspireon.chuyentrolinhtinh.common.util.ImageUtils;
import com.inspireon.chuyentrolinhtinh.model.domain.user.User;

public class TopTellerViewAdapter {
	
	private User teller;
	
	public TopTellerViewAdapter(User teller) {
		this.teller = teller;
	}

	public String getName() {
		return teller.username();
	}

	public int getPoints() {
		return teller.contribution().points();
	}
	public String getAvatar() {
		return ImageUtils.getFullImageURL(teller.avatar().small());
	}
}
