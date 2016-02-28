package com.inspireon.mystory.web.rest.story.telling;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.inspireon.mystory.application.ImageGroupService;
import com.inspireon.mystory.application.RelationService;
import com.inspireon.mystory.application.StoryTellingService;
import com.inspireon.mystory.model.domain.image.ImageGroup;
import com.inspireon.mystory.model.domain.story.Story;
import com.inspireon.mystory.model.domain.story.StoryRepo;
import com.inspireon.mystory.model.domain.story.Tag;
import com.inspireon.mystory.web.rest.base.AbstractBaseController;
import com.inspireon.mystory.web.rest.category.CategoryViewAdapter;
import com.inspireon.mystory.web.rest.security.MystoryUserReference;
import com.inspireon.mystory.web.rest.shared.context.MystoryUser;
import com.inspireon.mystory.web.rest.shared.i18n.I18NCode;
import com.inspireon.mystory.web.rest.story.reading.ChapterViewAdapter;

@Controller
@RequestMapping("/story")
public class StoryTellingController extends AbstractBaseController {
	
	private static final Logger logger = Logger.getLogger(StoryTellingController.class);
	
	@Autowired
	private StoryTellingService storyTellingService;
	
	@Autowired
	private ImageGroupService imageGroupService;
	
	@Autowired
	private RelationService relationService;
	
	@Autowired
	private StoryRepo storyRepo;
	
	@RequestMapping(method = RequestMethod.POST, value = "/tell")
	@PreAuthorize("canCreateStory()")
	public ModelAndView tellStory(@ModelAttribute StoryCommand newStory) {

		try {
			MystoryUser user = MystoryUserReference.getLoggedInUser();		
			
			ImageGroup featuredImage = imageGroupService.findImageGroup(newStory.getFeaturedImg());
		
			String newStoryId = storyTellingService.tellNewStory(user.getUsername(), newStory.getTitle(), newStory.getContent(), newStory.getOriginalStoryId(), featuredImage, newStory.getCategory());
			
			relationService.followAStory(user.getUserId(), newStoryId);
			
			return new ModelAndView("redirect:/story/" + newStoryId);
		} catch (Exception e) {
			// TODO define error message
			logger.error(e, e);
			return new ModelAndView("redirect:/story/newStory", "response", failure(I18NCode.MESSAGE_STORY_CREATE_FAIL));
		}
	}
	
	private List<ChapterViewAdapter> suggestEligibleOriginalStories(String teller, Story currentStory) {
			
		List<Story> eligibleOriginalStories = storyRepo.findStoriesEligibleToBeOriginalChapter(teller);
		
		if (currentStory != null) {
			eligibleOriginalStories.remove(currentStory);
		}
		
		List<ChapterViewAdapter> originalChapters = new ArrayList<ChapterViewAdapter>(eligibleOriginalStories.size()); 
		
		for (Story story : eligibleOriginalStories) {
			originalChapters.add(new ChapterViewAdapter(story));
		}
		
		return originalChapters;  
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/newStory")
	@PreAuthorize("canCreateStory()")
	public ModelAndView prepareTellingPage() {
		
		try {
			String teller = MystoryUserReference.getLoggedInUser().getUsername();
				
			ModelMap modelMap = new ModelMap();
			modelMap.put("eligibleOriginalStory", suggestEligibleOriginalStories(teller, null));
			modelMap.put("allCategories", getAllCategories(null));
			
			return new ModelAndView("storyCreate", modelMap);
		} catch (Exception e) {
			logger.error(e, e);
			return new ModelAndView("redirect:/story/newStory", "response", failure(I18NCode.MESSAGE_STORY_CREATE_FAIL));
		}
	} 
	
	@RequestMapping(method = RequestMethod.GET, value = "/editStory/{storyId}")
	@PreAuthorize("canEditStory(#storyId)")
	public ModelAndView prepareEditingPage(@PathVariable("storyId") String storyId) {
		
		try {
			Story story = storyRepo.find(storyId);
			Story originalStory = storyRepo.find(story.originalStoryId());

			List<ChapterViewAdapter> eligibleOriginalStory = suggestEligibleOriginalStories(story.author(), story);
			
			ModelMap modelMap = new ModelMap();
			modelMap.put("story", new StoryEditingViewAdapter(story, originalStory));
			modelMap.put("eligibleOriginalStory", eligibleOriginalStory);
			modelMap.put("allCategories", getAllCategories(story.tag()));
			
			return new ModelAndView("storyEdit", modelMap);
		} catch (Exception e) {
			logger.error(e, e);
			return new ModelAndView("redirect:/story/" + storyId, "response", failure(I18NCode.MESSAGE_STORY_UPDATE_FAIL));
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/edit")
	@PreAuthorize("canEditStory(#story.id)")	
	public ModelAndView editStory(@ModelAttribute StoryCommand story) {
		try {
			String editor = MystoryUserReference.getLoggedInUser().getUsername();
			ImageGroup featuredImage = imageGroupService.findImageGroup(story.getFeaturedImg());
		
			storyTellingService.editStory(story.getId(), editor, 
					story.getTitle(), story.getContent(), 
					story.getOriginalStoryId(), featuredImage, 
					story.getCategory());
			
			return new ModelAndView("redirect:/story/" + story.getId());
		} catch (Exception e) {
			// TODO define error message
			logger.error(e, e);
			return new ModelAndView("redirect:/story/editStory/" + story.getId(), "response", failure(I18NCode.MESSAGE_STORY_UPDATE_FAIL));
		}
	}
	
	private List<CategoryViewAdapter> getAllCategories(Tag selectedTag) {
		List<CategoryViewAdapter> allCategories = new ArrayList<CategoryViewAdapter>();
		
		for (Tag tag : Tag.values()) {
			if (!Tag.ALL.equals(tag)) {
				allCategories.add(new CategoryViewAdapter(tag, selectedTag));	
			}
		}
		
		return allCategories;
	}
}
