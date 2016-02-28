package com.inspireon.mystory.application.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inspireon.mystory.application.VotingService;
import com.inspireon.mystory.model.domain.comment.Comment;
import com.inspireon.mystory.model.domain.comment.CommentRepo;
import com.inspireon.mystory.model.domain.story.Story;
import com.inspireon.mystory.model.domain.story.StoryRepo;
import com.inspireon.mystory.model.domain.user.User;
import com.inspireon.mystory.model.domain.user.UserRepo;

@Service
public class VotingServiceImpl implements VotingService {
	
	private static final Logger logger = Logger.getLogger(VotingServiceImpl.class);

	@Autowired
	StoryRepo storyRepo;
	
	@Autowired
	CommentRepo commentRepo;
	
	@Autowired
	UserRepo userRepo;

	@Override
	public void upVoteStory(String voter, String storyId) throws Exception {
		
		Story storyToVote = storyRepo.find(storyId);
		
		if (storyToVote.isAlreadyVotedDownBy(voter)) {
			upVoteStoryAfterVotedDown(voter, storyToVote);
		} else {
			upVoteNewStory(voter, storyToVote);
		}
	}

	private void upVoteNewStory(String voter, Story storyToVote) throws Exception {
		try {
			storyToVote.upVote(voter);
			storyRepo.store(storyToVote);
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		User storyTeller = userRepo.findByUsername(storyToVote.author());
		
		storyTeller.gainPoint();
		
		try {
			userRepo.store(storyTeller);
		} catch (Exception e) {
			logger.error(e, e);
			
//			storyToVote.downVote(voter);
//			storyRepo.store(storyToVote);
			
			throw e;
		}
	}
	
	private void upVoteStoryAfterVotedDown(String voter, Story storyToVote) throws Exception {
		try {
			storyToVote.unDownVote(voter);
			storyToVote.upVote(voter);
			storyRepo.store(storyToVote);
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		User storyTeller = userRepo.findByUsername(storyToVote.author());
		
		// story teller gain 2 points
		storyTeller.gainPoint();
		storyTeller.gainPoint();
		
		try {
			userRepo.store(storyTeller);
		} catch (Exception e) {
			logger.error(e, e);
			
//			storyToVote.downVote(voter);
//			storyRepo.store(storyToVote);
			
			throw e;
		}
	}
	
	@Override
	public void unUpVoteStory(String voter, String storyId) throws Exception {
		
		Story storyToVote = storyRepo.find(storyId);
		
		try {
			storyToVote.unUpVote(voter);
			storyRepo.store(storyToVote);
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		User storyTeller = userRepo.findByUsername(storyToVote.author());
		
		storyTeller.losePoint();
		
		try {
			userRepo.store(storyTeller);
		} catch (Exception e) {
			logger.error(e, e);
			
//			storyToVote.downVote(voter);
//			storyRepo.store(storyToVote);
			
			throw e;
		}
	}

	private void downVoteNewStory(String voter, Story storyToVote) throws Exception {
		try {
			storyToVote.downVote(voter);
			storyRepo.store(storyToVote);
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		User storyTeller = userRepo.findByUsername(storyToVote.author());
		
		storyTeller.losePoint();
		
		try {
			userRepo.store(storyTeller);
		} catch (Exception e) {
			logger.error(e, e);
			
//			storyToVote.downVote(voter);
//			storyRepo.store(storyToVote);
			
			throw e;
		}
	}
	
	private void downVoteStoryAfterVotedUp(String voter, Story storyToVote) throws Exception {
		try {
			storyToVote.unUpVote(voter);
			storyToVote.downVote(voter);
			storyRepo.store(storyToVote);
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		User storyTeller = userRepo.findByUsername(storyToVote.author());
		
		storyTeller.losePoint();
		storyTeller.losePoint();
		
		try {
			userRepo.store(storyTeller);
		} catch (Exception e) {
			logger.error(e, e);
			
//			storyToVote.downVote(voter);
//			storyRepo.store(storyToVote);
			
			throw e;
		}
	}

	@Override
	public void downVoteStory(String voter, String storyId) throws Exception {
		
		Story storyToVote = storyRepo.find(storyId);
		
		if (storyToVote.isAlreadyVotedUpBy(voter)) {
			downVoteStoryAfterVotedUp(voter, storyToVote);
		} else {
			downVoteNewStory(voter, storyToVote);
		}
	}
	

	@Override
	public void unDownVoteStory(String voter, String storyId) throws Exception {
		Story storyToVote = storyRepo.find(storyId);
		
		storyToVote.unDownVote(voter);
		
		try {
			storyRepo.store(storyToVote);
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		User storyTeller = userRepo.findByUsername(storyToVote.author());
		
		storyTeller.gainPoint();
		
		try {
			userRepo.store(storyTeller);
		} catch (Exception e) {
			logger.error(e, e);
			
//			storyToVote.upVote(voter);
//			storyRepo.store(storyToVote);
			
			throw e;
		}
	}

	@Override
	public void upVoteComment(String voter, String commentId) throws Exception {
		
		Comment commentToVote = commentRepo.find(commentId);
		
		if (commentToVote.isAlreadyVotedDownBy(voter)) {
			upVoteCommentAfterVotedDown(commentToVote, voter);
		} else {
			upVoteNewComment(commentToVote, voter);	
		}
	}

	private int upVoteNewComment(Comment commentToVote, String voter) throws Exception {

		try {
			commentToVote.upVote(voter);
			commentRepo.store(commentToVote);
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		User commenter = userRepo.findByUsername(commentToVote.author());
		
		commenter.gainPoint();
		
		try {
			userRepo.store(commenter);
		} catch (Exception e) {
			logger.error(e, e);
			
//			commentToVote.downVote(voter);
//			commentRepo.store(commentToVote);
			
			throw e;
		}
		
		return commentToVote.point();
	}
	
	private void upVoteCommentAfterVotedDown(Comment commentToVote, String voter) throws Exception {
		
		try {
			commentToVote.unDownVote(voter);
			commentToVote.upVote(voter);
			commentRepo.store(commentToVote);
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		User commenter = userRepo.findByUsername(commentToVote.author());
		
		commenter.gainPoint();
		commenter.gainPoint();
		
		try {
			userRepo.store(commenter);
		} catch (Exception e) {
			logger.error(e, e);
			
//			commentToVote.downVote(voter);
//			commentRepo.store(commentToVote);
			
			throw e;
		}
	}
	
	@Override
	public void unUpVoteComment(String voter, String commentId) throws Exception {
		
		Comment commentToVote = commentRepo.find(commentId);
		
		commentToVote.unUpVote(voter);
		
		try {
			commentRepo.store(commentToVote);
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		User commenter = userRepo.findByUsername(commentToVote.author());
		
		commenter.losePoint();
		
		try {
			userRepo.store(commenter);
		} catch (Exception e) {
			logger.error(e, e);
			
//			commentToVote.downVote(voter);
//			commentRepo.store(commentToVote);
			
			throw e;
		}
	}

	@Override
	public void downVoteComment(String voter, String commentId) throws Exception {
		
		Comment commentToVote = commentRepo.find(commentId);
		
		if (commentToVote.isAlreadyVotedUpBy(voter)) {
			downVoteCommentAfterVotedUp(voter, commentToVote);
		} else {
			downVoteNewComment(voter, commentToVote);
		}
	}

	private void downVoteNewComment(String voter, Comment commentToVote)
			throws Exception {
		
		try {
			commentToVote.downVote(voter);
			commentRepo.store(commentToVote);
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		User commenter = userRepo.findByUsername(commentToVote.author());
		
		commenter.losePoint();
		
		try {
			userRepo.store(commenter);
		} catch (Exception e) {
			logger.error(e, e);
			
//			commentToVote.upVote(voter);
//			commentRepo.store(commentToVote);
			
			throw e;
		}
	}
	
	private void downVoteCommentAfterVotedUp(String voter, Comment commentToVote) throws Exception {
		
		try {
			commentToVote.unUpVote(voter);
			commentToVote.downVote(voter);
			commentRepo.store(commentToVote);
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		User commenter = userRepo.findByUsername(commentToVote.author());
		
		commenter.losePoint();
		commenter.losePoint();
		
		try {
			userRepo.store(commenter);
		} catch (Exception e) {
			logger.error(e, e);
			
//			commentToVote.upVote(voter);
//			commentRepo.store(commentToVote);
			
			throw e;
		}
	}

	@Override
	public void unDownVoteComment(String voter, String commentId) throws Exception {
		
		Comment commentToVote = commentRepo.find(commentId);
		
		commentToVote.unDownVote(voter);
		
		try {
			commentRepo.store(commentToVote);
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		User commenter = userRepo.findByUsername(commentToVote.author());
		
		commenter.gainPoint();
		
		try {
			userRepo.store(commenter);
		} catch (Exception e) {
			logger.error(e, e);
			
//			commentToVote.downVote(voter);
//			commentRepo.store(commentToVote);
			
			throw e;
		}
	}
}
