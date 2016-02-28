package com.inspireon.mystory.persistence;

import com.inspireon.mystory.model.domain.user.User;

public class MockObjectUtils {
	public static User createUser(String username, String password, String email){
		User user = new User(username, password, email);
		
		return user;
	}
}
