package com.inspireon.chuyentrolinhtinh.web.rest.security;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.inspireon.chuyentrolinhtinh.web.rest.shared.context.MystoryUser;

public class MyPostUserReference {
	
	private static final Logger logger = Logger
			.getLogger(MyPostUserReference.class);	
	
	public static MystoryUser getLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();		
		if (auth != null && auth.isAuthenticated()) {
			Object principal = auth.getPrincipal();
			if (principal instanceof MystoryUser) {
				return (MystoryUser) principal;
			} 
		}
		
		//guest
		return null;
	}
	
	public static boolean isUserLoggedIn() {
		boolean isUserLoggedIn = false;
		
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		
		if (auth != null && auth.isAuthenticated()) {
			Object principal = auth.getPrincipal();
			if (principal instanceof MystoryUser) {
				isUserLoggedIn = true;
			} 
		}
		
		return isUserLoggedIn;
	}
}
