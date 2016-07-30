package com.inspireon.chuyentrolinhtinh.web.rest.wall.viewing;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.inspireon.chuyentrolinhtinh.model.domain.post.Post;
import com.inspireon.chuyentrolinhtinh.model.domain.post.PostRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.user.User;
import com.inspireon.chuyentrolinhtinh.model.domain.user.UserRepo;
import com.inspireon.chuyentrolinhtinh.web.rest.base.AbstractBaseController;
import com.inspireon.chuyentrolinhtinh.web.rest.home.viewing.HomePostViewAdapter;
import com.inspireon.chuyentrolinhtinh.web.rest.home.viewing.HomeViewAdapter;
import com.inspireon.chuyentrolinhtinh.web.rest.home.viewing.HomeViewCondition;
import com.inspireon.chuyentrolinhtinh.web.rest.home.viewing.HomeViewingController;
import com.inspireon.chuyentrolinhtinh.web.rest.security.MyPostUserReference;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.i18n.I18NCode;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.i18n.I18NUtils;

@Controller
public class WallViewingController extends AbstractBaseController {
	
	private static final Logger logger = Logger.getLogger(HomeViewingController.class);
	
	public static final String PAGE_URL = "/p";
	
	private static final int FIRST_PAGE = 1;
	
	@Autowired
	private PostRepo storyRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private I18NUtils i18nUtils;
	
	@RequestMapping(method = RequestMethod.GET, value = "user/{username}/story")
	public ModelAndView viewWall(@PathVariable("username") String username) {	

		try {
			HomeViewCondition condition = new HomeViewCondition(FIRST_PAGE);
			
			HomeViewAdapter wallViewAdapter = new WallViewAdapter(condition.getPageNumber(), getWallToView(condition, username), username);
			
			ModelMap modelMap = new ModelMap();
			modelMap.put("wall", wallViewAdapter);
			
			return new ModelAndView("viewWall", modelMap);
			
		} catch (Exception e) {
			logger.error(e, e);
			return new ModelAndView(REDIRECT_NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "user/{username}/story" + PAGE_URL + "/{page}")
	public @ResponseBody Response viewWallAjax(@PathVariable("username") String username, @PathVariable("page") int page) {	

		try {
			HomeViewCondition condition = new HomeViewCondition(page);
			
			HomeViewAdapter wallViewAdapter = new WallViewAdapter(condition.getPageNumber(), getWallToView(condition, username), username);
			
			return success(wallViewAdapter);
			
		} catch (Exception e) {
			logger.error(e, e);
			return failure(I18NCode.MESSAGE_REQUEST_NOT_FOUND);
		}
	}

	private List<HomePostViewAdapter> getWallToView(HomeViewCondition condition, String username) {

		String viewer = getCurrentUsername();
		
		List<Post> stories = storyRepo.findByAuthor(condition, username);
		List<HomePostViewAdapter> userStories = new ArrayList<HomePostViewAdapter>(stories.size());
        
		for (Post story : stories) {
			User author = userRepo.findByUsername(story.author());
			
			userStories.add(new HomePostViewAdapter(story, author, viewer, i18nUtils));
		}
		
		return userStories;
	}
	
	private String getCurrentUsername() {
		String viewer = null;
		
		if(MyPostUserReference.isUserLoggedIn()) {
			viewer = MyPostUserReference.getLoggedInUser().getUsername();
		}
		
		return viewer;
	}
}
