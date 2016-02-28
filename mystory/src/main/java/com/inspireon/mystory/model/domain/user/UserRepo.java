package com.inspireon.mystory.model.domain.user;

import java.util.List;

import com.inspireon.mystory.persistence.BaseRepo;

public interface UserRepo extends BaseRepo<User, String> {
	
	User findByEmail(String email);
	
	User findByUsername(String username);
	
	List<User> findTopStoryTellers();
}
