package com.inspireon.chuyentrolinhtinh.web.rest.user;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.inspireon.chuyentrolinhtinh.application.RelationService;
import com.inspireon.chuyentrolinhtinh.application.UserService;
import com.inspireon.chuyentrolinhtinh.exception.UserNotFoundException;
import com.inspireon.chuyentrolinhtinh.model.domain.user.User;
import com.inspireon.chuyentrolinhtinh.web.rest.base.AbstractBaseController;
import com.inspireon.chuyentrolinhtinh.web.rest.security.MyPostUserReference;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.context.MystoryUser;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractBaseController{
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RelationService relationService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{username}")
	public ModelAndView viewUserProfile(@PathVariable("username") String username) {
		MystoryUser currentUser = MyPostUserReference.getLoggedInUser();
		try {
			User user = userService.getUserInfo(username);
			if (currentUser != null)
				return new ModelAndView("profileView", "profile", new UserViewAdapter(user, currentUser.getUsername()));
			else 
				return new ModelAndView("profileView", "profile", new UserViewAdapter(user));
		} catch (UserNotFoundException e) {
			logger.error(e, e);
			//TODO define error page
			return new ModelAndView(REDIRECT_HOME);			
		}	
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/password-reset")
	public ModelAndView resetPassword(@ModelAttribute ResetPasswordCommand resetPasswordCommand){
		logger.debug("request to reset password for email: " + resetPasswordCommand.getEmail());
		if(userService.resetPassword(resetPasswordCommand.getEmail())){
			return new ModelAndView(REDIRECT_LOGIN, "reset", true); 
		}
		else{
			return new ModelAndView("redirect:/resetpassword", "error", 1);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/activate/{userId}")
	public ModelAndView activate(@PathVariable("userId") String userId){
		try{
			if(userService.activate(userId)){
				return new ModelAndView(REDIRECT_HOME, "activated", true);
			}
			return new ModelAndView(REDIRECT_HOME, "activated", false);
		} catch(Exception e){
			return new ModelAndView(REDIRECT_HOME, "activated", false);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/lasviewed-update")
	public @ResponseBody 
	Response updateLastViewedNotification(){
		
		MystoryUser user = MyPostUserReference.getLoggedInUser();
		
		if(user==null) return failure(BLANK);
		else{
			try{
				userService.updateLastViewedNotification(user.getUserId());
				return success();
			} catch (Exception e){
				logger.error(e.getMessage());
				return failure();
			}
		}
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/check-unique/{username}")
	@ResponseBody
	public String checkUnique(@PathVariable("username") String username){
		try{
			if(userService.checkUnique(username))
				return String.valueOf(true);
		} catch(Exception e){
			return String.valueOf(false);
		}
		
		return String.valueOf(false);
	}	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/check-unique-email/{email}/")
	@ResponseBody
	public String checkUniqueEmail(@PathVariable("email") String email){
		try{
			if(userService.checkUniqueEmail(email))
				return String.valueOf(true);
		} catch(Exception e){
			return String.valueOf(false);
		}
		
		return String.valueOf(false);
	}	
}
