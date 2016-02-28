package com.inspireon.mystory.application.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inspireon.mystory.application.CategoryService;
import com.inspireon.mystory.model.domain.story.Tag;
import com.inspireon.mystory.model.domain.user.User;
import com.inspireon.mystory.model.domain.user.UserRepo;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private UserRepo userRepo; 
	
	@Override
	public List<Tag> showTags(String username) {
		List<Tag> tags = new ArrayList<Tag>();
		
		if (username != null) {
			User user = userRepo.findByUsername(username);
			tags = user.setting().tagSetting().tags();
		} else {
			tags = Arrays.asList(Tag.values());
		}
		
		return tags;
	}
}
