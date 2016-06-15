package com.inspireon.chuyentrolinhtinh.model.domain.user;

import java.util.List;

import com.inspireon.chuyentrolinhtinh.persistence.BaseRepo;

public interface UserRepo extends BaseRepo<User, String> {
	
	User findByEmail(String email);
	
	User findByUsername(String username);
	
	List<User> findTopStoryTellers();
}
