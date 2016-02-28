package com.inspireon.mystory.web.rest.home.viewing;

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

import com.inspireon.mystory.application.CategoryService;
import com.inspireon.mystory.application.StoryRankingService;
import com.inspireon.mystory.model.domain.story.Story;
import com.inspireon.mystory.model.domain.story.StoryRepo;
import com.inspireon.mystory.model.domain.story.Tag;
import com.inspireon.mystory.model.domain.user.User;
import com.inspireon.mystory.model.domain.user.UserRepo;
import com.inspireon.mystory.web.rest.base.AbstractBaseController;
import com.inspireon.mystory.web.rest.category.CategoryViewAdapter;
import com.inspireon.mystory.web.rest.home.viewing.SortTypeAdapter.Type;
import com.inspireon.mystory.web.rest.security.MystoryUserReference;
import com.inspireon.mystory.web.rest.shared.i18n.I18NCode;
import com.inspireon.mystory.web.rest.shared.i18n.I18NUtils;

@Controller
public class HomeViewingController extends AbstractBaseController {
	
	private static final Logger logger = Logger.getLogger(HomeViewingController.class);
	
	public static final String HOME_URL = "/home";
	public static final String TAG_URL = "/tag";
	public static final String PAGE_URL = "/p";
	
	@Autowired
	private StoryRepo storyRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private StoryRankingService storyRankingService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private I18NUtils i18nUtils;
	
	@RequestMapping(method = RequestMethod.GET, value = HOME_URL)
	public ModelAndView viewHome() {	

		try {
			HomeViewCondition condition = new HomeViewCondition(HomeViewCondition.DEFAULT_TYPE, HomeViewCondition.NO_TAG, HomeViewCondition.FIRST_PAGE);

			return new ModelAndView("homePage", getHomeToView(condition));
		
		} catch (Exception e) {
			logger.error(e, e);
			return new ModelAndView(REDIRECT_NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = HOME_URL + PAGE_URL + "/{page}")
	public String viewHomeWithPage(@PathVariable("page") int page) {	
		return "redirect:/home/" + HomeViewCondition.DEFAULT_TYPE + PAGE_URL + "/" + page;  
	}
	
	@RequestMapping(method = RequestMethod.POST, value = HOME_URL + PAGE_URL + "/{page}")
	public @ResponseBody Response viewHomeAjax(@PathVariable("page") int page) {	

		try {
			HomeViewCondition condition = new HomeViewCondition(HomeViewCondition.DEFAULT_TYPE, HomeViewCondition.NO_TAG, page);
			
			return success(BLANK, getHomeStoriesToView(condition));
			
		} catch (Exception e) {
			logger.error(e, e);
			return failure(I18NCode.MESSAGE_REQUEST_NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = TAG_URL + "/{tag}")
	public ModelAndView viewByTag(@PathVariable("tag") String tag) {
		
		try {
			HomeViewCondition condition = new HomeViewCondition(HomeViewCondition.DEFAULT_TYPE, tag, HomeViewCondition.FIRST_PAGE);
			
			return new ModelAndView("homePage", getHomeToView(condition));
			
		} catch (Exception e) {
			logger.error(e, e);
			return new ModelAndView(REDIRECT_NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = TAG_URL + "/{tag}" + PAGE_URL + "/{page}")
	public ModelAndView viewByTagWithPage(@PathVariable("tag") String tag, @PathVariable("page") int page) {	
		
		try {
			HomeViewCondition condition = new HomeViewCondition(HomeViewCondition.DEFAULT_TYPE, tag, page);
			
			return new ModelAndView("homePage", getHomeToView(condition));
			
		} catch (Exception e) {
			logger.error(e, e);
			return new ModelAndView(REDIRECT_NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = TAG_URL + "/{tag}" + PAGE_URL + "/{page}")
	public @ResponseBody Response viewByTagAjax(@PathVariable("tag") String tag, @PathVariable("page") int page) {
		
		try {
			HomeViewCondition condition = new HomeViewCondition(HomeViewCondition.DEFAULT_TYPE, tag, page);
			
			return success(BLANK, getHomeStoriesToView(condition));
			
		} catch (Exception e) {
			logger.error(e, e);
			return failure(I18NCode.MESSAGE_REQUEST_NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = HOME_URL + "/{type}")
	public ModelAndView viewBySort(@PathVariable("type") String type) {
		
		try {
			HomeViewCondition condition = new HomeViewCondition(type, HomeViewCondition.NO_TAG, HomeViewCondition.FIRST_PAGE);
			
			return new ModelAndView("homePage", getHomeToView(condition));
			
		} catch (Exception e) {
			logger.error(e, e);
			return new ModelAndView(REDIRECT_NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = HOME_URL + "/{type}" + PAGE_URL + "/{page}")
	public ModelAndView viewBySortWithPage(@PathVariable("type") String type, @PathVariable("page") int page) {	

		try {
			HomeViewCondition condition = new HomeViewCondition(type, HomeViewCondition.NO_TAG, page);
			
			return new ModelAndView("homePage", getHomeToView(condition));
			
		} catch (Exception e) {
			logger.error(e, e);
			return new ModelAndView(REDIRECT_NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = HOME_URL + "/{type}" + PAGE_URL + "/{page}")
	public @ResponseBody Response viewBySortAjax(@PathVariable("type") String type, @PathVariable("page") int page) {	

		try {
			HomeViewCondition condition = new HomeViewCondition(type, HomeViewCondition.NO_TAG, page);
			
			return success(BLANK, getHomeStoriesToView(condition));
			
		} catch (Exception e) {
			logger.error(e, e);
			return failure(I18NCode.MESSAGE_REQUEST_NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = TAG_URL + "/{tag}" + "/{type}")
	public ModelAndView viewBySortInTag(@PathVariable("tag") String tag, @PathVariable("type") String type) {	

		try {
			HomeViewCondition condition = new HomeViewCondition(type, tag, HomeViewCondition.FIRST_PAGE);
			
			return new ModelAndView("homePage", getHomeToView(condition));
			
		} catch (Exception e) {
			logger.error(e, e);
			return new ModelAndView(REDIRECT_NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = TAG_URL + "/{tag}/{type}" + PAGE_URL + "/{page}")
	public ModelAndView viewBySortInTagWithPage(@PathVariable("tag") String tag, @PathVariable("type") String type, @PathVariable("page") int page) {	

		try {
			HomeViewCondition condition = new HomeViewCondition(type, tag, page);
			
			return new ModelAndView("homePage", getHomeToView(condition));
			
		} catch (Exception e) {
			logger.error(e, e);
			return new ModelAndView(REDIRECT_NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = TAG_URL + "/{tag}/{type}" + PAGE_URL + "/{page}")
	public @ResponseBody Response viewBySortInTagAjax(@PathVariable("tag") String tag, @PathVariable("type") String type, @PathVariable("page") int page) {	

		try {
			HomeViewCondition condition = new HomeViewCondition(type, tag, page);
			
			return success(BLANK, getHomeStoriesToView(condition));
			
		} catch (Exception e) {
			logger.error(e, e);
			return failure(I18NCode.MESSAGE_REQUEST_NOT_FOUND);
		}
	}

	private ModelMap getHomeToView(HomeViewCondition condition) {
		ModelMap modelMap = new ModelMap();
		
		modelMap.put("home", getHomeStoriesToView(condition));
		modelMap.put("sortTypes", getSortTypes(condition));
		modelMap.put("menuCategories", getMenuCategories(condition));
		
		return modelMap;
	}
	
	private String getCurrentUsername() {
		String viewer = null;
		
		if(MystoryUserReference.isUserLoggedIn()) {
			viewer = MystoryUserReference.getLoggedInUser().getUsername();
		}
		
		return viewer;
	}
	
	private HomeViewAdapter getHomeStoriesToView(HomeViewCondition condition) {
		String viewer = getCurrentUsername();
		
		List<Story> stories = new ArrayList<Story>();
		List<HomeStoryViewAdapter> homeStories = new ArrayList<HomeStoryViewAdapter>();
		
        switch (condition.getType()) {
	        case HOT:  
	        	stories = storyRankingService.getTopHot(condition);
	        	break;
	        case NEW:  
	        	stories = storyRepo.findTopLastCommented(condition);
	            break;
	        case CONTROVERSIAL:  
	        	stories = storyRankingService.getTopControversial(condition);
	            break;
	        case LEGEND:  
	        	stories = storyRepo.findTopLegend(condition);
	            break;
        }
        
		for (Story story : stories) {
			User author = userRepo.findByUsername(story.author());
			
			homeStories.add(new HomeStoryViewAdapter(story, author, viewer, i18nUtils));
		}
		
		return new HomeViewAdapter(condition.getPageNumber(), homeStories);
	}
	
	private List<SortTypeAdapter> getSortTypes(final HomeViewCondition condition) {
		List<SortTypeAdapter> sortTypeAdapters = new ArrayList<SortTypeAdapter>(Type.values().length);
		
		for (Type type : Type.values()) {
			sortTypeAdapters.add(new SortTypeAdapter(type, condition.getTag(), condition.getType()));
		}
		
		return sortTypeAdapters;
	}
	
	private List<CategoryViewAdapter> getMenuCategories(final HomeViewCondition condition) {
		List<CategoryViewAdapter> menuCategories = new ArrayList<CategoryViewAdapter>();
		
		for (Tag tag : Tag.values()) {
			menuCategories.add(new CategoryViewAdapter(tag, condition.getTag()));
		}
		
		return menuCategories;
	}
}
