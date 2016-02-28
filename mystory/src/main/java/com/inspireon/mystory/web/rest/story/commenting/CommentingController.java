package com.inspireon.mystory.web.rest.story.commenting;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.inspireon.mystory.application.CommentingService;
import com.inspireon.mystory.application.RelationService;
import com.inspireon.mystory.application.UserService;
import com.inspireon.mystory.model.domain.comment.Comment;
import com.inspireon.mystory.model.domain.comment.CommentRepo;
import com.inspireon.mystory.model.domain.story.StoryRepo;
import com.inspireon.mystory.model.domain.user.UserRepo;
import com.inspireon.mystory.web.rest.base.AbstractBaseController;
import com.inspireon.mystory.web.rest.security.MystoryUserReference;
import com.inspireon.mystory.web.rest.shared.i18n.I18NCode;

@Controller
@RequestMapping("/comment")
public class CommentingController extends AbstractBaseController{
	
	private static final Logger logger = Logger.getLogger(CommentingController.class);
	
	@Autowired
	private CommentingService commentingService;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private StoryRepo storyRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RelationService relationService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	@PreAuthorize("canCreateComment()")
	public ModelAndView addComment(@ModelAttribute CommentCommand newComment){
		try {
			String storyId = newComment.getStoryId();
			
			String author = MystoryUserReference.getLoggedInUser().getUsername();

			String newCommentId = commentingService.addNewComment(newComment.getContent(), author, storyId);
			relationService.followAStory(MystoryUserReference.getLoggedInUser().getUserId(), storyId);
			
			return new ModelAndView("redirect:/story/" + storyId + "#comment-" + newCommentId);
		} catch (Exception e) {
			logger.error(e, e);
			return new ModelAndView("redirect:/story/" + newComment.getStoryId(), "response", failure(I18NCode.MESSAGE_COMMENT_CREATE_FAIL));
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/editComment/{commentId}")	
	@PreAuthorize("canEditComment(#commentId)")
	public @ResponseBody Response prepareEditingPage(@PathVariable("commentId") String commentId) {
		
		Comment com = commentRepo.find(commentId);
		
		if (com == null) {
			logger.error("Could not find comment with id :" + commentId);
			return failure(I18NCode.MESSAGE_COMMENT_REQUEST_FAIL);
		}
		
		CommentEditingViewAdapter comment = new CommentEditingViewAdapter(com);
		
		return success(comment);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/edit")
	@PreAuthorize("canEditComment(#comment.id)")
	public @ResponseBody Response editComment(@ModelAttribute CommentCommand comment){
		
		if(!MystoryUserReference.isUserLoggedIn()) {
			// if user did not log in, we consider he/she is guest
			return failure(I18NCode.MESSAGE_USER_NOTLOGIN);
		}
		
		try {
			String editor = MystoryUserReference.getLoggedInUser().getUsername();
			
			Comment oldComment = commentRepo.find(comment.getId());
			
			commentingService.editComment(oldComment, editor, comment.getContent());
			
			
			//String newContent = HTMLUtils.sanitize(new PegDownProcessor().markdownToHtml(commentRepo.find(comment.getId()).content()));
			
			return success(new CommentContentViewAdapter(comment.getContent()));
			
		} catch (Exception e) {
			logger.error(e, e);
			return failure(I18NCode.MESSAGE_COMMENT_UPDATE_FAIL);
		}
	}
}
