package com.inspireon.mystory.web.rest.shared.prepare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.inspireon.mystory.application.CategoryService;
import com.inspireon.mystory.application.NotificationService;
import com.inspireon.mystory.application.UserService;
import com.inspireon.mystory.model.domain.notification.Notification;
import com.inspireon.mystory.model.domain.story.Tag;
import com.inspireon.mystory.model.domain.user.User;
import com.inspireon.mystory.model.domain.user.UserRepo;
import com.inspireon.mystory.web.rest.category.CategoryViewAdapter;
import com.inspireon.mystory.web.rest.home.viewing.NotificationViewAdapter;
import com.inspireon.mystory.web.rest.home.viewing.TopTellerViewAdapter;
import com.inspireon.mystory.web.rest.home.viewing.UserPanelViewAdapter;
import com.inspireon.mystory.web.rest.security.MystoryUserReference;
import com.inspireon.mystory.web.rest.shared.context.MystoryUser;
import com.inspireon.mystory.web.rest.shared.i18n.I18NUtils;

@Controller("myStoryViewPrepare")
@Scope("session")
public class MyStoryViewPrepare implements ViewPreparer{

	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private I18NUtils i18nUtils;
	
	@Override
	public void execute(Request request, AttributeContext attributeContext) {
		
		try {
			
			Map<String, Object> viewMap = request.getContext("session");
			
			MystoryUser myStoryUser = MystoryUserReference.getLoggedInUser();
			String username = null;
			
			if (myStoryUser != null) {
				//user side bar
				username = myStoryUser.getUsername();
				User currentUser = userRepo.findByUsername(username);
				
				List<Notification> notifications = notificationService.
						getNewNotificationForUser(myStoryUser.getUserId());
				if(notifications!=null && notifications.size()>0){
					List<NotificationViewAdapter> notificationViewAdapters = new ArrayList<NotificationViewAdapter>();
					int notificationCount = 0;
					for(Notification notification : notifications){
						if(notification.createdDate().after(currentUser.lastViewedNotification())){
							if(notificationViewAdapters.size() < 30)
								notificationViewAdapters.add(new NotificationViewAdapter(notification, i18nUtils, false));
							notificationCount++;
						}
						else if(notificationViewAdapters.size() < 30)
								notificationViewAdapters.add(new NotificationViewAdapter(notification, i18nUtils, true));
					}
					
					viewMap.put("userPanel", new UserPanelViewAdapter(currentUser, notificationViewAdapters, notificationCount));
				} else {
					viewMap.put("userPanel", new UserPanelViewAdapter(currentUser, null, 0));
				}
			}
			
			else{
				viewMap.put("userPanel", null);
			}
			
			viewMap.put("topTellers", getTopTellers());
			viewMap.put("menuCategories", getMenuCategories());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private List<TopTellerViewAdapter> getTopTellers() {
		List<User> users = userRepo.findTopStoryTellers();
		List<TopTellerViewAdapter> topTellers = new ArrayList<TopTellerViewAdapter>(users.size());
		
		for (User user : users) {
			topTellers.add(new TopTellerViewAdapter(user));
		}
		return topTellers;
	}
	
	private List<CategoryViewAdapter> getMenuCategories() {
		List<CategoryViewAdapter> menuCategories = new ArrayList<CategoryViewAdapter>();
		Tag selectedTag = null;
		
		for (Tag tag : Tag.values()) {
			menuCategories.add(new CategoryViewAdapter(tag, selectedTag));
		}

		return menuCategories;
	}
}
