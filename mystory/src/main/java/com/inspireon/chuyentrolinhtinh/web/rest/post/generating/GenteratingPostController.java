package com.inspireon.chuyentrolinhtinh.web.rest.post.generating;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inspireon.chuyentrolinhtinh.application.RelationService;
import com.inspireon.chuyentrolinhtinh.application.PostTellingService;
import com.inspireon.chuyentrolinhtinh.application.UserService;
import com.inspireon.chuyentrolinhtinh.model.domain.post.Tag;
import com.inspireon.chuyentrolinhtinh.model.domain.user.User;
import com.inspireon.chuyentrolinhtinh.web.rest.base.AbstractBaseController;
import com.inspireon.chuyentrolinhtinh.web.rest.post.telling.PostTellingController;

@Controller
@RequestMapping("/story")
public class GenteratingPostController extends AbstractBaseController {
	
	private static final Logger logger = Logger.getLogger(PostTellingController.class);
	
	@Autowired
	private PostTellingService storyTellingService;
	
	@Autowired
	private RelationService relationService;
	
	@Autowired
	private UserService userService;	
	
	@RequestMapping(method = RequestMethod.POST, value = "/generate")
	@PreAuthorize("isMyStoryUser(#story.username, #story.password)")
	public @ResponseBody Response generateStory(@ModelAttribute GeneratingPostCommand story) {
		try {
			User user = userService.getUserInfo(story.getUsername());
			String newStoryId = storyTellingService.tellNewStory(story.getUsername(), story.getTitle(), story.getContent(), null, null, Tag.NEWS);
			
			relationService.followAStory(user.id(), newStoryId);
			return success();
		} catch (Exception e) {
			logger.error(e, e);
			return failure();
		}
	}

}
