package com.inspireon.chuyentrolinhtinh.application;

import java.util.Date;
import java.util.List;

import com.inspireon.chuyentrolinhtinh.exception.DuplicateUsernameException;
import com.inspireon.chuyentrolinhtinh.exception.EmailAlreadyInUseException;
import com.inspireon.chuyentrolinhtinh.exception.ImageWrittenFailureException;
import com.inspireon.chuyentrolinhtinh.exception.InvalidPasswordException;
import com.inspireon.chuyentrolinhtinh.exception.MessageOpenFailureException;
import com.inspireon.chuyentrolinhtinh.exception.UserNotFoundException;
import com.inspireon.chuyentrolinhtinh.model.domain.image.ImageGroup;
import com.inspireon.chuyentrolinhtinh.model.domain.story.Tag;
import com.inspireon.chuyentrolinhtinh.model.domain.user.Message;
import com.inspireon.chuyentrolinhtinh.model.domain.user.User;
import com.inspireon.chuyentrolinhtinh.web.rest.image.FileMeta;

public interface UserService {
	
	boolean checkUnique(String username);
	
	boolean checkUniqueEmail(String email);
	
	boolean activate(String userId);
	
//	void register(String username, String password, String email) throws AddressException, MessagingException;
	
	boolean betaRegister(String username, String password, String email) throws DuplicateUsernameException, EmailAlreadyInUseException;
	
	void updateTagSetting(String username, List<Tag> tags) throws UserNotFoundException;
	
	/**
	 * Update user setting 
	 * @param username
	 * @param newSetting
	 * @author Tung
	 * @throws UserNotFoundException 
	 */
	void updateSetting(String username, String slogan, String lang, String facebook, 
			boolean isDisplayToOthers, boolean allowPostingActivities) throws UserNotFoundException;
	
	void updateLastViewedNotification(String userId);
	
	public boolean resetPassword(String email);
	
	public void changePassword(String userName, String oldPassword, String newPassword)
						throws UserNotFoundException, InvalidPasswordException;
	
	/**
	 * Get User information and get rank based on number of likes. 
	 * @param username
	 * @return
	 */
	User getUserInfo(String username) throws UserNotFoundException;
	
	/**
	 * Send a new message.
	 * @param from
	 * @param to
	 * @param title
	 * @param content
	 */	
	void sendMessage(String from, String to, String title, String content) throws UserNotFoundException;
	
	/**
	 * User open his/her Inboxes. 
	 * @return List of messages from Inboxes
	 */
	List<Message> showInboxes(String username) throws UserNotFoundException;
	
	
	/**
	 * User open his/her SentItems. 
	 * @return List of messages from SentItems
	 */
	List<Message> showSentItems(String username) throws UserNotFoundException;	
	
	/**
	 * User read a message in Inboxes
	 * @param username
	 * @param from
	 * @param submittedTime
	 * @return Message
	 */
	Message openInboxMessage(String username, String from, Date submittedTime) 
			throws MessageOpenFailureException;
	
	/**
	 * User read a message in SentItems
	 * @param username 
	 * @param to
	 * @param submittedTime
	 * @return Message
	 */	
	Message openSentMessage(String username, String to, Date submittedTime) 
			throws MessageOpenFailureException;
	
	ImageGroup changeAvatar(String username, FileMeta newAvatar) 
			throws ImageWrittenFailureException;
	
	void followUser(String follower, String inspirerName) throws UserNotFoundException;
	
	void unfollowUser(String follower, String inspirerName) throws UserNotFoundException;
	
	User registerWithFacebook(String username, String password, String email, String facebook, FileMeta avatar);
}
