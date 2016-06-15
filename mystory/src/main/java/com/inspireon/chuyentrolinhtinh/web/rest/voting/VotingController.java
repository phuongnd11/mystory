package com.inspireon.chuyentrolinhtinh.web.rest.voting;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inspireon.chuyentrolinhtinh.application.VotingService;
import com.inspireon.chuyentrolinhtinh.model.domain.comment.CommentRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.story.StoryRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.user.UserRepo;
import com.inspireon.chuyentrolinhtinh.web.rest.base.AbstractBaseController;
import com.inspireon.chuyentrolinhtinh.web.rest.security.MystoryUserReference;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.i18n.I18NCode;

@Controller
public class VotingController extends AbstractBaseController{

	private static final Logger logger = Logger.getLogger(VotingController.class);
	
	@Autowired
	private VotingService votingService;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private StoryRepo storyRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@RequestMapping(method = RequestMethod.POST, value = "/vote")
	@ResponseBody
	public Response vote(@ModelAttribute VoteCommand newVote){
		
		if(!MystoryUserReference.isUserLoggedIn()) {
			// define error message (user not logged in) later
			return failure(I18NCode.MESSAGE_USER_NOTLOGIN);
		} else {
			String voter = MystoryUserReference.getLoggedInUser().getUsername();
			
			try {
		        switch (newVote.getType()) {
		            case UPVOTE_STORY:  
		            	votingService.upVoteStory(voter, newVote.getCandidateId());
		            	break;
		            case UPVOTE_COMMENT:  
		            	votingService.upVoteComment(voter, newVote.getCandidateId());
		                break;
		            case DOWNVOTE_STORY:  
		            	votingService.downVoteStory(voter, newVote.getCandidateId());
		                break;
		            case DOWNVOTE_COMMENT:  
		            	votingService.downVoteComment(voter, newVote.getCandidateId());
		                break;
		            case UN_UPVOTE_STORY:  
		            	votingService.unUpVoteStory(voter, newVote.getCandidateId());
		                break;
		            case UN_DOWNVOTE_STORY:  
		            	votingService.unDownVoteStory(voter, newVote.getCandidateId());
		                break;
		            case UN_UPVOTE_COMMENT:  
		            	votingService.unUpVoteComment(voter, newVote.getCandidateId());
		                break;
		            case UN_DOWNVOTE_COMMENT:  
		            	votingService.unDownVoteComment(voter, newVote.getCandidateId());
		                break;
		        }
			} catch (Exception e) {
				logger.error(e, e);
				return failure(I18NCode.MESSAGE_VOTE_FAIL);
			}
			
			return success(I18NCode.MESSAGE_VOTE_SUCCESS); 
		}
	}
	
}
