package com.inspireon.chuyentrolinhtinh.application.impl;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inspireon.chuyentrolinhtinh.application.BetaInvitationService;
import com.inspireon.chuyentrolinhtinh.application.ImageGroupService;
import com.inspireon.chuyentrolinhtinh.application.UserService;
import com.inspireon.chuyentrolinhtinh.exception.DuplicateUsernameException;
import com.inspireon.chuyentrolinhtinh.exception.EmailAlreadyInUseException;
import com.inspireon.chuyentrolinhtinh.exception.ImageWrittenFailureException;
import com.inspireon.chuyentrolinhtinh.exception.InvalidPasswordException;
import com.inspireon.chuyentrolinhtinh.exception.MessageOpenFailureException;
import com.inspireon.chuyentrolinhtinh.exception.UserNotFoundException;
import com.inspireon.chuyentrolinhtinh.model.domain.image.ImageGroup;
import com.inspireon.chuyentrolinhtinh.model.domain.image.ImageGroupRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.post.Tag;
import com.inspireon.chuyentrolinhtinh.model.domain.user.FacebookSetting;
import com.inspireon.chuyentrolinhtinh.model.domain.user.Message;
import com.inspireon.chuyentrolinhtinh.model.domain.user.Setting;
import com.inspireon.chuyentrolinhtinh.model.domain.user.TagSetting;
import com.inspireon.chuyentrolinhtinh.model.domain.user.User;
import com.inspireon.chuyentrolinhtinh.model.domain.user.UserRepo;
import com.inspireon.chuyentrolinhtinh.model.manager.BetaUserManager;
import com.inspireon.chuyentrolinhtinh.model.manager.UserManager;
import com.inspireon.chuyentrolinhtinh.web.rest.image.FileMeta;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private BetaInvitationService betaInvitationService;
	
	@Autowired
	private BetaUserManager betaUserManager;
	
	@Autowired
	private ImageGroupRepo imageGroupRepo;
	
	@Autowired
	private ImageGroupService imageGroupService;	
	
	/**
	 * @author Phuong
	 * 
	 */
	@Override
	public boolean resetPassword(String email) {
		
		User user = userRepo.findByEmail(email);
		
		if(user == null) return false;
		
		else{
			
			//generate new password
			String password = userManager.generateRandomPassword();
			try {
				//save new password
				user.resetPassword(password);
				userRepo.store(user);
				
				//email password to user
				userManager.emailPassword(email, password);
				
				return true;
				
			} catch (AddressException e) {
				if(logger.isDebugEnabled())
					logger.debug("Error sending email to " + email);
				return false;
			} catch (MessagingException e) {
				if(logger.isDebugEnabled())
					logger.debug("Error sending email to " + email);
				return false;
			}
		}
	}

	@Override
	public User getUserInfo(String username) throws UserNotFoundException{
		User user = userRepo.findByUsername(username);
		if (user == null) {
			// if user not found, then throw exception
			logger.debug("User not found: " + username);			
			throw new UserNotFoundException(username);
		} else {
			return user;
		}
	}	

	@Override
	public void updateTagSetting(String username, List<Tag> tags)
			throws UserNotFoundException {
		// find user in repository
		User user = userRepo.findByUsername(username);
		
		if (user == null) {
			// if user not found, then throw exception
			if(logger.isDebugEnabled()) {
				logger.warn("Can't update non-existing user: " + username);
			}
			
			throw new UserNotFoundException(username);
		} else {
			
			// update new setting for user
			user.updateTagSetting(new TagSetting(tags));
			
			// store user into repository
			userRepo.store(user);
			
			if(logger.isDebugEnabled()) {
				logger.info("Setting of user: " + username + " has been updated!");
			}
		}		
		
	}
	
	@Override
	public void updateSetting(String username, String slogan, String lang,
			String facebook, boolean isDisplayToOthers,
			boolean allowPostingActivities) throws UserNotFoundException {
		
		// find user in repository
		User user = userRepo.findByUsername(username);
		
		if (user == null) {
			// if user not found, then throw exception
			if(logger.isDebugEnabled()) {
				logger.warn("Can't update non-existing user: " + username);
			}
			
			throw new UserNotFoundException(username);
		} else {
			FacebookSetting facebookSetting = new FacebookSetting(facebook, isDisplayToOthers, allowPostingActivities);
			Setting newSetting = new Setting(slogan, lang, facebookSetting, user.setting().tagSetting());
			
			// update new setting for user
			user.updateSetting(newSetting);
			
			// store user into repository
			userRepo.store(user);
			
			if(logger.isDebugEnabled()) {
				logger.info("Setting of user: " + username + " has been updated!");
			}
		}
		
	}		
	
	@Override
	public void sendMessage(String from, String to, String title, String content) 
			throws UserNotFoundException{
		logger.info("Sending a message from " + from + " to " + to + " with title: " + title);
		User fromUser = userRepo.findByUsername(from);
		User toUser = userRepo.findByUsername(to);
		
		if (fromUser == null) {
			logger.error("Can't send message from non-existing user: " + from);			
			throw new UserNotFoundException(from);			
		} else if (toUser == null) {
			logger.error("Can't send message to non-existing user: " + to);			
			throw new UserNotFoundException(to);			
		} else if (from.equalsIgnoreCase(to)){		
			fromUser.sendMessage(new Message(from, to, title, content));
			fromUser.receiveMessage(new Message(from, to, title, content));
			
			userRepo.store(fromUser);
		} else {
			fromUser.sendMessage(new Message(from, to, title, content));
			toUser.receiveMessage(new Message(from, to, title, content));
			
			userRepo.store(fromUser);
			userRepo.store(toUser);
		}				
	}

	@Override
	public List<Message> showInboxes(String username) throws UserNotFoundException{
		logger.info("Showing Inboxes of user: " + username);
		
		User user = userRepo.findByUsername(username);
		if (user == null) {
			logger.error("Can't show Inboxes from non-existing user: " + username);			
			throw new UserNotFoundException(username);			
		} else {		
			return user.mailBox().inboxes();
		}		
	}

	@Override
	public List<Message> showSentItems(String username) throws UserNotFoundException {
		logger.info("Showing SentItems of user: " + username);
		
		User user = userRepo.findByUsername(username);
		if (user == null) {
			logger.error("Can't show SentItems from non-existing user: " + username);			
			throw new UserNotFoundException(username);			
		} else {		
			return user.mailBox().sentItems();
		}
	}
	
	@Override
	public Message openInboxMessage(String username, String from, Date submittedTime) 
			throws MessageOpenFailureException {
		logger.info("Opening message sent from " + from + " to " + username + ", time: " + submittedTime.toString());
		
		Message message = null;
		User user = userRepo.findByUsername(username);
		
		if (user != null) {	
			message = user.readInboxMessage(from, submittedTime);
		}
		
		if (message != null) {
			// If this message has not read yet, mark read and update user into repository
			if (!message.isRead()) {				
				user.markRead(message);
				userRepo.store(user);
				logger.debug("This message has not read yet, mark read it!");
			}			
		} else {
			logger.error("Can't open message.");
			throw new MessageOpenFailureException();
		}		
				
		return message;
	}

	@Override
	public Message openSentMessage(String username, String to, Date submittedTime) 
			throws MessageOpenFailureException {
		logger.info("Opening message sent from " + username + " to " + to + ", time: " + submittedTime.toString());
		
		Message message = null;
		User user = userRepo.findByUsername(username);
		
		if (user != null) {	
			message = user.readSendMessage(to, submittedTime);
		}
		
		if (message != null) {
			// If this message has not read yet, mark read and update user into repository
			if (!message.isRead()) {				
				user.markRead(message);
				userRepo.store(user);
				logger.debug("This message has not read yet, mark read it!");
			}			
		} else {
			logger.error("Can't open message.");
			throw new MessageOpenFailureException();
		}			
				
		return message;
	}


/*	*//**
	 * @author Phuong
	 * @throws MessagingException 
	 * @throws AddressException 
	 *//*
	@Override
	public void register(String username, String password, String email) throws AddressException, MessagingException {
		
		
		
		//not finished yet
		
		User user = new User(username, userManager.encodePassword(password), email);
		userRepo.add(user);
		
		betaUserManager.sendActiveLink(user.id(), user.email());
	}
*/
	/**
	 * @author Phuong
	 *  
	 * 
	 */
	@Override
	public boolean betaRegister(String username, String password, String email) throws DuplicateUsernameException, EmailAlreadyInUseException{
		
		if(userRepo.findByUsername(username) != null) throw new DuplicateUsernameException(username);
		if(userRepo.findByEmail(email) != null) throw new EmailAlreadyInUseException(email);
		
		User user = new User(username, userManager.encodePassword(password), email);
		userRepo.add(user);
		
		if(user.id() != null){
						
			try {
				betaUserManager.sendActiveLink(user.id(), user.email());
				return true;
			} catch (AddressException e) {
				logger.error("Error sending active link to user: " + user.username());
				logger.error(e.getMessage());
				return false;
			} catch (MessagingException e) {
				logger.error("Error sending active link to user: " + user.username());
				logger.error(e.getMessage());
				return false;
			}
		}
		
		return false;
	}

	/*
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		 System.out.println(slugify("[Đến hẹn lại lên] Show màn hình desktop tháng 11/2013 "));
		
	}
	
	  public static String slugify(String input) throws UnsupportedEncodingException {
	        if (input == null || input.length() == 0) return "";
	        String toReturn = normalize(input);
	        
	        toReturn = toReturn.replace(" ", "-");
	        toReturn = toReturn.toLowerCase();
	        toReturn = URLEncoder.encode(toReturn, "UTF-8");
	        return toReturn;
	    }
	 
	    private static String normalize(String input) {
	        if (input == null || input.length() == 0) return "";
	        return Normalizer.normalize(input, Form.NFD).replaceAll("[^\\p{ASCII}]","");
	    }
*/
		@Override
		public boolean activate(String userId) {
			
			User user = userRepo.find(userId);
			if(user!=null){
				user.activate();
				
				userRepo.store(user);
				return true;
			}
			return false;
		}		

		@Override
		public ImageGroup changeAvatar(String username, FileMeta avatar) 
				throws ImageWrittenFailureException {
			User user = userRepo.findByUsername(username);		
			try {
				ImageGroup oriImage = imageGroupService.uploadAvatarImage(avatar);
				user.changeAvatar(oriImage);
				
				userRepo.store(user);
				return oriImage;
			} catch (ImageWrittenFailureException e) {
				logger.error("create and resize new avatar failure " + e.getMessage());
				throw new ImageWrittenFailureException(StringUtils.EMPTY);
			}
		}
		
		@Override
		public boolean checkUnique(String username) {
			User user = userRepo.findByUsername(username);
			if(user!=null) return false;
			return true;
		}

		@Override
		public void followUser(String followerName, String inspirerName) throws UserNotFoundException{
			User follower = userRepo.findByUsername(followerName);
			if (follower == null) throw new UserNotFoundException(followerName);
			
			User inspirer = userRepo.findByUsername(inspirerName);
			if (inspirer == null) throw new UserNotFoundException(inspirerName);
			
			follower.follow(inspirer);
			userRepo.store(inspirer);
		}
		
		@Override
		public void unfollowUser(String followerName, String inspirerName) throws UserNotFoundException{
			User follower = userRepo.findByUsername(followerName);
			if (follower == null) throw new UserNotFoundException(followerName);
			
			User inspirer = userRepo.findByUsername(inspirerName);
			if (inspirer == null) throw new UserNotFoundException(inspirerName);
			
			follower.unfollow(inspirer);
			userRepo.store(inspirer);
		}

		@Override
		public void updateLastViewedNotification(String userId) {
			
			User user = userRepo.find(userId);
			user.updateLastViewedNotification(new Date());
			userRepo.store(user);
			
		}

		@Override
		public void changePassword(String username, String oldPassword, String newPassword) 
				throws UserNotFoundException, InvalidPasswordException {
			User user = userRepo.findByUsername(username);
			if (user != null) {
				user.changePassword(oldPassword, newPassword);
				userRepo.store(user);
			} else {
				throw new UserNotFoundException(username);
			}
		}

		@Override
		public boolean checkUniqueEmail(String email) {
			User user = userRepo.findByEmail(email);
			if(user!=null) return false;
			return true;
		}

		@Override
		public User registerWithFacebook(String username, String password, String email,
				String facebook, FileMeta avatar) {
			
			User user = null;
			
			try {
				ImageGroup ig = imageGroupService.uploadAvatarImage(avatar);
				user = new User(username, email, userManager.encodePassword(password), facebook, ig);
				
				userRepo.add(user);
				
				userManager.emailNewPasswordToFacebookUser(email, username, password);
				
				return user;
				
			} catch (ImageWrittenFailureException e) {
				logger.error("create and resize new avatar failure " + e.getMessage());
			} catch (AddressException e) {
				if(logger.isDebugEnabled())
					logger.debug("Error sending email to " + email);
			} catch (MessagingException e) {
				if(logger.isDebugEnabled())
					logger.debug("Error sending email to " + email);
			}
			return user;
		}
}
