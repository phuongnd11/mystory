package com.inspireon.mystory.web.rest.story.generating;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inspireon.mystory.application.RelationService;
import com.inspireon.mystory.application.StoryTellingService;
import com.inspireon.mystory.application.UserService;
import com.inspireon.mystory.model.domain.story.Tag;
import com.inspireon.mystory.model.domain.user.User;
import com.inspireon.mystory.web.rest.base.AbstractBaseController;
import com.inspireon.mystory.web.rest.story.telling.StoryTellingController;

@Controller
@RequestMapping("/story")
public class GenteratingStoryController extends AbstractBaseController {
	
	private static final Logger logger = Logger.getLogger(StoryTellingController.class);
	
	@Autowired
	private StoryTellingService storyTellingService;
	
	@Autowired
	private RelationService relationService;
	
	@Autowired
	private UserService userService;	
	
	@RequestMapping(method = RequestMethod.POST, value = "/generate")
	@PreAuthorize("isMyStoryUser(#story.username, #story.password)")
	public @ResponseBody Response generateStory(@ModelAttribute GeneratingStoryCommand story) {
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
