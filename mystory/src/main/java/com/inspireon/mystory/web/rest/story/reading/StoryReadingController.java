package com.inspireon.mystory.web.rest.story.reading;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.inspireon.mystory.application.CategoryService;
import com.inspireon.mystory.application.RecommendationService;
import com.inspireon.mystory.application.UserService;
import com.inspireon.mystory.model.domain.comment.Comment;
import com.inspireon.mystory.model.domain.comment.CommentRepo;
import com.inspireon.mystory.model.domain.story.Story;
import com.inspireon.mystory.model.domain.story.StoryRepo;
import com.inspireon.mystory.model.domain.story.Tag;
import com.inspireon.mystory.model.domain.user.User;
import com.inspireon.mystory.model.domain.user.UserRepo;
import com.inspireon.mystory.web.rest.base.AbstractBaseController;
import com.inspireon.mystory.web.rest.category.CategoryViewAdapter;
import com.inspireon.mystory.web.rest.security.MystoryUserReference;
import com.inspireon.mystory.web.rest.shared.i18n.I18NUtils;

@Controller
@RequestMapping("/story")
public class StoryReadingController extends AbstractBaseController{
	
	private static final Logger logger = Logger.getLogger(StoryReadingController.class);
	
	@Autowired
	private StoryRepo storyRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private RecommendationService recommendationService;
	
	@Autowired
	private I18NUtils i18nUtils;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{storyId}")
	public ModelAndView readStory(@PathVariable("storyId") String storyId) {
		try {
			String reader = null;
			
			if(MystoryUserReference.isUserLoggedIn()) {
				// if user did not log in, we consider he/she is guest
				reader = MystoryUserReference.getLoggedInUser().getUsername();
			} 
				
			Story inHandStory = storyRepo.find(storyId);
			
			User author = userRepo.findByUsername(inHandStory.author());
			
			String originalStoryId = inHandStory.originalStoryId();
			if (StringUtils.isBlank(originalStoryId)) {
				originalStoryId = inHandStory.id();
			}
			
			// find all stories with same originalStoryId (include the original story itself)
			List<Story> siblingChapters = storyRepo.findByOriginalStoryId(originalStoryId);
			
			StoryViewAdapter storyToRead = new StoryViewAdapter(inHandStory, siblingChapters, author, reader, i18nUtils);
			
			List<Comment> commentsOfStory = commentRepo.findByStoryId(storyId, CommentSortType.ALL);
			List<CommentViewAdapter> commentsToRead = new ArrayList<CommentViewAdapter>(commentsOfStory.size());
			
			for (Comment comment : commentsOfStory) {
				User commenter = userRepo.findByUsername(comment.author());
				
				commentsToRead.add(new CommentViewAdapter(commenter, comment, reader, i18nUtils));
			}
			
			storyToRead.setComments(commentsToRead);
			
		    //TODO this piece of code is not considered good practice, we'll move to better solution later on - Tung 
			inHandStory.view();
			storyRepo.store(inHandStory);
			
			ModelMap modelMap = new ModelMap();
			modelMap.put("storyDetail", storyToRead);
			modelMap.put("menuCategories", getMenuCategories(inHandStory.tag()));
			modelMap.put("recommendedStories", getRecommendedStories(inHandStory));
			
			return new ModelAndView("storyDetail", modelMap);
			
		} catch (Exception e) {
			logger.error(e, e);
			return new ModelAndView(REDIRECT_NOT_FOUND);
		}
	}
	
	private List<RecommendedStoryViewAdapter> getRecommendedStories(Story excludedStory) {
		List<Story> excludedStories = new ArrayList<Story>();
		excludedStories.add(excludedStory);
		
		List<RecommendedStoryViewAdapter> recommendedStories = new ArrayList<RecommendedStoryViewAdapter>();
		
		for (Story story : recommendationService.recommendStories(excludedStories)) {
			recommendedStories.add(new RecommendedStoryViewAdapter(story));
		}
		
		return recommendedStories;
	}
	
//	@RequestMapping(method = RequestMethod.POST, value = "/{storyId}/comments/{sortType}")	
//	public @ResponseBody List<CommentViewAdapter> showCommentsOfStory(@PathVariable("storyId") String storyId, @PathVariable("sortType") String sortType) {
//		String reader = null;
//		
//		if(MystoryUserReference.isUserLoggedIn()) {
//			// if user did not log in, we consider he/she is guest
//			reader = MystoryUserReference.getLoggedInUser().getUsername();
//		} 
//		
//		List<Comment> commentsOfStory = commentRepo.findByStoryId(storyId, CommentSortType.valueOf(sortType));
//		List<CommentViewAdapter> comments = new ArrayList<CommentViewAdapter>(commentsOfStory.size());
//		
//		for (Comment comment : commentsOfStory) {
//			User commenter = userRepo.findByUsername(comment.author());
//			
//			comments.add(new CommentViewAdapter(commenter, comment, reader));
//		}
//		
//		return comments;
//	}
	
	private List<CategoryViewAdapter> getMenuCategories(Tag selectedTag) {
		List<CategoryViewAdapter> menuCategories = new ArrayList<CategoryViewAdapter>();
		
		for (Tag tag : Tag.values()) {
			menuCategories.add(new CategoryViewAdapter(tag, selectedTag));
		}
		return menuCategories;
	}
}
