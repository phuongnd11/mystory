package com.inspireon.chuyentrolinhtinh.web.rest.post.telling;

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

import com.inspireon.chuyentrolinhtinh.application.ImageGroupService;
import com.inspireon.chuyentrolinhtinh.application.RelationService;
import com.inspireon.chuyentrolinhtinh.application.PostTellingService;
import com.inspireon.chuyentrolinhtinh.model.domain.image.ImageGroup;
import com.inspireon.chuyentrolinhtinh.model.domain.post.Post;
import com.inspireon.chuyentrolinhtinh.model.domain.post.PostRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.post.Tag;
import com.inspireon.chuyentrolinhtinh.web.rest.base.AbstractBaseController;
import com.inspireon.chuyentrolinhtinh.web.rest.category.CategoryViewAdapter;
import com.inspireon.chuyentrolinhtinh.web.rest.post.reading.ChapterViewAdapter;
import com.inspireon.chuyentrolinhtinh.web.rest.security.MyPostUserReference;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.context.MystoryUser;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.i18n.I18NCode;

@Controller
@RequestMapping("/story")
public class PostTellingController extends AbstractBaseController {
	
	private static final Logger logger = Logger.getLogger(PostTellingController.class);
	
	@Autowired
	private PostTellingService storyTellingService;
	
	@Autowired
	private ImageGroupService imageGroupService;
	
	@Autowired
	private RelationService relationService;
	
	@Autowired
	private PostRepo storyRepo;
	
	@RequestMapping(method = RequestMethod.POST, value = "/tell")
	@PreAuthorize("canCreateStory()")
	public ModelAndView tellStory(@ModelAttribute PostCommand newStory) {

		try {
			MystoryUser user = MyPostUserReference.getLoggedInUser();		
			
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
	
	private List<ChapterViewAdapter> suggestEligibleOriginalStories(String teller, Post currentStory) {
			
		List<Post> eligibleOriginalStories = storyRepo.findStoriesEligibleToBeOriginalChapter(teller);
		
		if (currentStory != null) {
			eligibleOriginalStories.remove(currentStory);
		}
		
		List<ChapterViewAdapter> originalChapters = new ArrayList<ChapterViewAdapter>(eligibleOriginalStories.size()); 
		
		for (Post story : eligibleOriginalStories) {
			originalChapters.add(new ChapterViewAdapter(story));
		}
		
		return originalChapters;  
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/newStory")
	@PreAuthorize("canCreateStory()")
	public ModelAndView prepareTellingPage() {
		
		try {
			String teller = MyPostUserReference.getLoggedInUser().getUsername();
				
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
			Post story = storyRepo.findByFriendlyUrl(storyId);
			Post originalStory = storyRepo.find(story.originalStoryId());

			List<ChapterViewAdapter> eligibleOriginalStory = suggestEligibleOriginalStories(story.author(), story);
			
			ModelMap modelMap = new ModelMap();
			modelMap.put("story", new PostEditingViewAdapter(story, originalStory));
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
	public ModelAndView editStory(@ModelAttribute PostCommand story) {
		try {
			String editor = MyPostUserReference.getLoggedInUser().getUsername();
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
