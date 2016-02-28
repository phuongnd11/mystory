package com.inspireon.mystory.web.rest.user;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.inspireon.mystory.application.RelationService;
import com.inspireon.mystory.application.UserService;
import com.inspireon.mystory.common.util.ImageUtils;
import com.inspireon.mystory.exception.ImageWrittenFailureException;
import com.inspireon.mystory.exception.InvalidPasswordException;
import com.inspireon.mystory.exception.MessageOpenFailureException;
import com.inspireon.mystory.exception.UserNotFoundException;
import com.inspireon.mystory.model.domain.image.ImageGroup;
import com.inspireon.mystory.model.domain.story.Tag;
import com.inspireon.mystory.model.domain.user.Message;
import com.inspireon.mystory.model.domain.user.User;
import com.inspireon.mystory.web.rest.base.AbstractBaseController;
import com.inspireon.mystory.web.rest.image.FileMeta;
import com.inspireon.mystory.web.rest.image.ImageViewAdapter;
import com.inspireon.mystory.web.rest.security.MystoryUserReference;
import com.inspireon.mystory.web.rest.shared.context.MystoryUser;
import com.inspireon.mystory.web.rest.shared.i18n.I18NCode;

@Controller
@RequestMapping("/account")
public class UserProfileController extends AbstractBaseController{
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired		
	private UserService userService;
	
	@Autowired
	private RelationService relationService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/profile")
	public ModelAndView viewUpdateUserProfile() {
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		if (currentUser != null) {
			try {
				User user = userService.getUserInfo(currentUser.getUsername());
				String avatarImage = ImageUtils.getFullImageURL(user.avatar().large());
				return new ModelAndView("profileEdit", "profile", new UserProfileViewAdapter(UserTab.PROFILE, avatarImage, new UserViewAdapter(user))); 			 
			} catch (UserNotFoundException e) {
				logger.error(e, e);		
			}	
		} 		
		return new ModelAndView(REDIRECT_HOME);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/updateAvatar")
	public @ResponseBody Response changeAvatar(MultipartHttpServletRequest request){
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		if (currentUser != null) {
			Iterator<String> itr = request.getFileNames();
			try {
				if (itr.hasNext()) {
					MultipartFile mpf = request.getFile(itr.next());
					if (mpf != null && ImageUtils.isSizeValid(mpf.getSize())) {	
						if (mpf.getContentType().contains("image/")) {
							FileMeta fileMeta = new FileMeta(ImageUtils.toImageType(mpf.getContentType()),
									 mpf.getBytes());
							ImageGroup newAvatar = userService.changeAvatar(currentUser.getUsername(), fileMeta);
							return success(new ImageViewAdapter(newAvatar));
						} else {
							return failure(I18NCode.MESSAGE_IMAGE_FILETYPE_WRONG);
						}
					}
				} else {
					return failure(I18NCode.MESSAGE_IMAGE_FILESIZE_OVER);
				}
			} catch (ImageWrittenFailureException e) {
				logger.error(e.getMessage(), e);
			} catch (IOException ie) {
				logger.error(ie.getMessage(), ie);
			}											
		} 
			
		return failure(I18NCode.MESSAGE_IMAGE_UPLOAD_FAIL);		
	}	
	
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public ModelAndView updateUserSetting(@ModelAttribute UserSettingCommand setting) {
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		if (currentUser != null) {		
			try {
							
				userService.updateSetting(currentUser.getUsername(), setting.getSlogan(), setting.getLang(), setting.getFacebook(), 
											setting.isDisplayToOthers(), setting.isAllowPostingActivities());
				return new ModelAndView("redirect:/account/profile");
			} catch (Exception e) {
				logger.error(e, e);
				//ERORR 404
				return new ModelAndView("redirect:/account/profile", "response", failure(I18NCode.MESSAGE_USER_UPDATEPROFILE_FAIL));				
			}
		} else {
			return new ModelAndView(REDIRECT_HOME);
		}			
	}	
	
	@RequestMapping(method = RequestMethod.POST, value = "/updateTagSetting")
	public @ResponseBody Response updateTagSetting(@RequestParam("categories[]") List<Tag> categories) {
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		if (currentUser != null) {		
			try {
				userService.updateTagSetting(currentUser.getUsername(), categories);
				return success();
			} catch (Exception e) {
				logger.error(e, e);
				//ERORR 404
				return failure();				
			}
		} else {
			return failure();
		}			
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/mailbox")
	public ModelAndView displayMailbox() {
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		if (currentUser != null) {			
			try {
				User user = userService.getUserInfo(currentUser.getUsername());
				String avatarImage = ImageUtils.getFullImageURL(user.avatar().large());
				return new ModelAndView("profileMailbox", "profile", new UserProfileViewAdapter(UserTab.MAILBOX, avatarImage));
			} catch (UserNotFoundException e) {
				logger.error(e, e);				
			}
		}
		
		return new ModelAndView(REDIRECT_HOME);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/inbox")
	public @ResponseBody Response displayInBoxes() {		
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		if (currentUser != null) {
			try {
				User user = userService.getUserInfo(currentUser.getUsername());
				List<Message> messages = userService.showInboxes(user.username());				
				String avatarImage = ImageUtils.getFullImageURL(user.avatar().large());
				return success(new UserProfileViewAdapter(UserTab.MAILBOX, avatarImage, new UserMailBoxViewAdapter(i18nUtils, messages, false)));
			} catch (UserNotFoundException e) {
				logger.error(e, e);
			}
		}
		
		return failure();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/sent")
	public @ResponseBody Response displaySentItems() {
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		if (currentUser != null) {			
			try {
				User user = userService.getUserInfo(currentUser.getUsername());
				List<Message> messages = userService.showSentItems(user.username());							
				String avatarImage = ImageUtils.getFullImageURL(user.avatar().large());				
				return success(new UserProfileViewAdapter(UserTab.MAILBOX, avatarImage, new UserMailBoxViewAdapter(i18nUtils, messages, true)));
			} catch (UserNotFoundException e) {
				logger.error(e, e);			 
			}
		}
		
		return failure();
	}	
	
	@RequestMapping(method = RequestMethod.GET, value = "/notification")
	public ModelAndView displayNotification() {
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		if (currentUser != null) {	
			try {
				User user = userService.getUserInfo(currentUser.getUsername());
				String avatarImage = ImageUtils.getFullImageURL(user.avatar().large());					
				return new ModelAndView("profileNotification", "profile", new UserProfileViewAdapter(UserTab.NOTIFICATION, avatarImage));				
			} catch (UserNotFoundException e) {
				logger.error(e, e);	
			}					
		} 
		
		return new ModelAndView(REDIRECT_HOME);
	}	
	
	@RequestMapping(method = RequestMethod.GET, value = "/inbox/detail")
	public @ResponseBody Response viewMessageInBoxDetail(@RequestParam("from") String from, @RequestParam("submittedTime") Long submittedTime) {
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		if (currentUser != null) {		
			try {
				Message message = userService.openInboxMessage(currentUser.getUsername(), from, new Date(submittedTime));
				User user = userService.getUserInfo(currentUser.getUsername());
				String avatarImage = ImageUtils.getFullImageURL(user.avatar().large());
				return success(new UserProfileViewAdapter(UserTab.MAILBOX, avatarImage, new UserMessageViewAdapter(message, i18nUtils)));
			} catch (MessageOpenFailureException e) {
				logger.error(e, e);
			} catch (UserNotFoundException e) {
				logger.error(e, e);
			}
		}
		
		return failure();
	}	
	
	@RequestMapping(method = RequestMethod.GET, value = "/sent/detail")
	public @ResponseBody Response viewMessageSentDetail(@RequestParam("to") String to, @RequestParam("submittedTime") Long submittedTime) {
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		if (currentUser != null) {
			try {
				Message message = userService.openSentMessage(currentUser.getUsername(), to, new Date(submittedTime));
				User user = userService.getUserInfo(currentUser.getUsername());
				String avatarImage = ImageUtils.getFullImageURL(user.avatar().large());
				return success(new UserProfileViewAdapter(UserTab.MAILBOX, avatarImage, new UserMessageViewAdapter(message, i18nUtils)));
			} catch (MessageOpenFailureException e) {
				logger.error(e, e);
			} catch (UserNotFoundException e) {
				logger.error(e, e);
			}
		}
		
		return failure();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/sendMessage")
	public @ResponseBody Response sendMessage(@RequestBody UserMessageCommand message){
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		if (currentUser != null) {
			try {
				userService.sendMessage(currentUser.getUsername(), message.getTo(), message.getTitle(), message.getContent());
				return success();
			} catch (UserNotFoundException e) {
				logger.error(e, e);
			}
		} 
			
		return failure(I18NCode.MESSAGE_USER_MAILBOX_COMPOSEMESSAGE_FAIL);		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/replyMessage")
	public @ResponseBody Response replyMessage(@RequestBody UserMessageCommand message){
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		if (currentUser != null) {
			try {
				String to = null;
				if (currentUser.getUsername().equalsIgnoreCase(message.getFrom())) {
					to = message.getTo();
				} else if (currentUser.getUsername().equalsIgnoreCase(message.getTo())){
					to = message.getFrom();
				}
				if (to != null) {
					userService.sendMessage(currentUser.getUsername(), to, getReplyTitle(message.getTitle()), message.getContent());
					return success();
				}
			} catch (UserNotFoundException e) {
				logger.error(e, e);
			}
		} 
			
		return failure(I18NCode.MESSAGE_USER_MAILBOX_COMPOSEMESSAGE_FAIL);		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/compose")
	public ModelAndView composeNewMessage(){
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		if (currentUser != null) {
			try {
				User user = userService.getUserInfo(currentUser.getUsername());
				String avatarImage = ImageUtils.getFullImageURL(user.avatar().large());
				return new ModelAndView("profileSendMessage", "profile", new UserProfileViewAdapter(UserTab.MAILBOX, avatarImage)); 			 
			} catch (UserNotFoundException e) {
				logger.error(e, e);		
			}	
		} 		
		
		return new ModelAndView(REDIRECT_HOME);		
	}
		
	@RequestMapping(method = RequestMethod.POST, value = "/follow/{username}")
	public @ResponseBody 
	Response follow(@PathVariable("username") String inspirer){
		
		MystoryUser user = MystoryUserReference.getLoggedInUser();
		
		if(user==null) return failure();
		else{
			try {
				userService.followUser(user.getUsername(), inspirer);
				
				relationService.followAStoryTeller(user.getUserId(), 
						userService.getUserInfo(inspirer).id());				
				
				return success();
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return failure();
		}
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/unfollow/{username}")
	public @ResponseBody Response unfollowUser(@PathVariable("username") String inspirer){
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		if (currentUser != null) {
			try {
				userService.unfollowUser(currentUser.getUsername(), inspirer);
				
				relationService.removeRelation(currentUser.getUserId(), 
						userService.getUserInfo(inspirer).id());
				
				return success();
			} catch (UserNotFoundException e) {
				logger.error(e, e);
				return failure();
			}
		} else {
			return failure();
		}		
	} 	
	
	@RequestMapping(method = RequestMethod.GET, value = "/password")
	public ModelAndView prepareChangePassword(){
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		if (currentUser != null) {
			try {
				User user = userService.getUserInfo(currentUser.getUsername());
				String avatarImage = ImageUtils.getFullImageURL(user.avatar().large());
				return new ModelAndView("profileChangePassword", "profile", new UserProfileViewAdapter(UserTab.PROFILE, avatarImage)); 			 
			} catch (UserNotFoundException e) {
				logger.error(e, e);		
			}	
		} 		
		
		return new ModelAndView(REDIRECT_HOME);				
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/updatePassword")
	public @ResponseBody Response changePassword(@RequestBody PasswordCommand newPassword){
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		if (currentUser != null) {
			try {
				userService.changePassword(currentUser.getUsername(), 
						newPassword.getOldPassword(), newPassword.getNewPassword());
				return success(I18NCode.MESSAGE_USER_UPDATEPASSWORD_SUCCESS);
			} catch (UserNotFoundException e) {
				logger.error(e, e);
				return failure();
			} catch (InvalidPasswordException e) {
				logger.error(e, e);
				return failure(I18NCode.MESSAGE_USER_UPDATEPASSWORD_OLD_WRONG);
			}
		}
		
		return failure();
	}
	
	private String getReplyTitle(String title) {
		if (title != null && !title.startsWith("RE: ")) {
			title = "RE: " + title;
		}
		
		return title;
	}
}
