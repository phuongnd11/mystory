package com.inspireon.chuyentrolinhtinh.web.rest.post.reading;

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

import com.inspireon.chuyentrolinhtinh.application.CategoryService;
import com.inspireon.chuyentrolinhtinh.application.RecommendationService;
import com.inspireon.chuyentrolinhtinh.application.UserService;
import com.inspireon.chuyentrolinhtinh.model.domain.comment.Comment;
import com.inspireon.chuyentrolinhtinh.model.domain.comment.CommentRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.post.Post;
import com.inspireon.chuyentrolinhtinh.model.domain.post.PostRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.post.Tag;
import com.inspireon.chuyentrolinhtinh.model.domain.user.User;
import com.inspireon.chuyentrolinhtinh.model.domain.user.UserRepo;
import com.inspireon.chuyentrolinhtinh.web.rest.base.AbstractBaseController;
import com.inspireon.chuyentrolinhtinh.web.rest.category.CategoryViewAdapter;
import com.inspireon.chuyentrolinhtinh.web.rest.security.MyPostUserReference;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.i18n.I18NUtils;

@Controller
@RequestMapping("/story")
public class PostReadingController extends AbstractBaseController{
	
	private static final Logger logger = Logger.getLogger(PostReadingController.class);
	
	@Autowired
	private PostRepo storyRepo;
	
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
			
			if(MyPostUserReference.isUserLoggedIn()) {
				// if user did not log in, we consider he/she is guest
				reader = MyPostUserReference.getLoggedInUser().getUsername();
			} 
//			storyId = storyId.substring(storyId.lastIndexOf("-")+1);	
			Post inHandStory = storyRepo.findByFriendlyUrl(storyId);
			
			User author = userRepo.findByUsername(inHandStory.author());
			
			String originalStoryId = inHandStory.originalStoryId();
			if (StringUtils.isBlank(originalStoryId)) {
				originalStoryId = inHandStory.id();
			}
			
			// find all stories with same originalStoryId (include the original story itself)
			List<Post> siblingChapters = storyRepo.findByOriginalStoryId(originalStoryId);
			
			PostViewAdapter storyToRead = new PostViewAdapter(inHandStory, siblingChapters, author, reader, i18nUtils);
			
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
	
	private List<RecommendedPostViewAdapter> getRecommendedStories(Post excludedStory) {
		List<Post> excludedStories = new ArrayList<Post>();
		excludedStories.add(excludedStory);
		
		List<RecommendedPostViewAdapter> recommendedStories = new ArrayList<RecommendedPostViewAdapter>();
		
		for (Post story : recommendationService.recommendStories(excludedStories)) {
			recommendedStories.add(new RecommendedPostViewAdapter(story));
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
