package com.inspireon.chuyentrolinhtinh.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inspireon.chuyentrolinhtinh.application.SecurityService;
import com.inspireon.chuyentrolinhtinh.model.domain.comment.Comment;
import com.inspireon.chuyentrolinhtinh.model.domain.comment.CommentRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.post.Post;
import com.inspireon.chuyentrolinhtinh.model.domain.post.PostRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.user.User;
import com.inspireon.chuyentrolinhtinh.model.domain.user.UserRepo;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	PostRepo storyRepo;

	@Autowired
	CommentRepo commentRepo;
	
	@Autowired
	UserRepo userRepo;

	@Override
	public boolean checkStoryAuthor(String storyId, String editor){
		Post story = storyRepo.findByFriendlyUrl(storyId);
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
