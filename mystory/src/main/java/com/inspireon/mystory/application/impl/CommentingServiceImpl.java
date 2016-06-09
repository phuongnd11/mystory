package com.inspireon.mystory.application.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inspireon.mystory.application.CommentingService;
import com.inspireon.mystory.model.domain.comment.Comment;
import com.inspireon.mystory.model.domain.comment.CommentRepo;
import com.inspireon.mystory.model.domain.comment.Version;
import com.inspireon.mystory.model.domain.story.Story;
import com.inspireon.mystory.model.domain.story.StoryRepo;
import com.inspireon.mystory.model.domain.user.User;
import com.inspireon.mystory.model.domain.user.UserRepo;

@Service
public class CommentingServiceImpl implements CommentingService {

	private static final Logger logger = Logger.getLogger(CommentingServiceImpl.class);

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private StoryRepo storyRepo;

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public String addNewComment(String content, String username, String storyId) {
		
		Comment comment = new Comment(content, username, storyId);
		
		try {
			commentRepo.store(comment);
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		Story story = storyRepo.findByFriendlyUrl(storyId);
		
		story.addComment(comment);
		
		try {
			storyRepo.store(story);
		} catch (Exception e) {
			logger.error(e, e);
			throw e;
		}
		
		User commenter = userRepo.findByUsername(username);
		
		commenter.contributeOneMoreComment();
		
		try {
			userRepo.store(commenter);
		} catch (Exception e) {
			logger.error(e, e);
			throw e;
		}
		
		return comment.id();
	}

	@Override
	public void editComment(Comment comment, String editor, String newContent) {
		// check permission for edit this comment
		
		Version lastVersion = new Version(comment.content(), comment.lastEditedBy(), comment.lastEditedTime());
		comment.addVersion(lastVersion);
		
		comment.updateContent(newContent);
		comment.updateLastEditedBy(editor);
		comment.updateLastEditedTime(new Date());
		
		commentRepo.store(comment);
	}
}
