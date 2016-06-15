package com.inspireon.chuyentrolinhtinh.model.manager;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface UserManager {
	
	String generateRandomPassword();
	
	void emailPassword(String email, String password) throws AddressException, MessagingException;

	String encodePassword(String password);
	//void emailConfirm
	
	void emailNewPasswordToFacebookUser(String email, String username, String password) throws AddressException, MessagingException;
}
