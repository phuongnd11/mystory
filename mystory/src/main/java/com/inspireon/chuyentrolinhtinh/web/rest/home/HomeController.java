package com.inspireon.chuyentrolinhtinh.web.rest.home;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.inspireon.chuyentrolinhtinh.web.rest.base.AbstractBaseController;
import com.inspireon.chuyentrolinhtinh.web.rest.security.MystoryUserReference;

@Controller
public class HomeController extends AbstractBaseController {
	
	private static final Logger logger = Logger.getLogger(HomeController.class);

	@RequestMapping("/")
	public String defaultPage() {
		return REDIRECT_HOME;
	}
	  
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String loginPage() {
		
		if(MystoryUserReference.getLoggedInUser() != null){
			return "redirect:/home";
		}
		
		return "login";
	}
	
	
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public String registerPage() {
		
		return "register";
	}
	
	@RequestMapping(value="/resetpassword", method = RequestMethod.GET)
	public String resetPassword() {
		
		return "resetPassword";
	}

	@RequestMapping(value="/accessdenied", method = RequestMethod.GET)
	public String accessDenied() {
		
		return "accessDenied";
	}
	
	@RequestMapping(value="/notfound", method = RequestMethod.GET)
	public String notFound() {
		
		return "notFound";
	}
}
