package com.inspireon.mystory.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inspireon.mystory.application.SecurityService;
import com.inspireon.mystory.model.domain.comment.Comment;
import com.inspireon.mystory.model.domain.comment.CommentRepo;
import com.inspireon.mystory.model.domain.story.Story;
import com.inspireon.mystory.model.domain.story.StoryRepo;
import com.inspireon.mystory.model.domain.user.User;
import com.inspireon.mystory.model.domain.user.UserRepo;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	StoryRepo storyRepo;

	@Autowired
	CommentRepo commentRepo;
	
	@Autowired
	UserRepo userRepo;

	@Override
	public boolean checkStoryAuthor(String storyId, String editor){
		Story story = storyRepo.find(storyId);
		if (story != null) { 
			return story.author().equalsIgnoreCase(editor);
		} else {
			return false;
		}
	}

	@Override
	public boolean checkCommentAuthor(String commentId, String editor) {
		Comment comment = commentRepo.find(commentId);
		if (comment != null) { 
			return comment.author().equalsIgnoreCase(editor);
		} else {
			return false;
		}
	}

	@Override
	public boolean checkUserValid(String username, String password) {
		User user = userRepo.findByUsername(username);
		if (user != null) {
			PasswordEncoder pe = new BCryptPasswordEncoder();
			
			return pe.matches(password, user.password());				
		}
		
		return false;
	}
}
