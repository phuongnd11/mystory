package com.inspireon.chuyentrolinhtinh.model.manager.impl;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.inspireon.chuyentrolinhtinh.application.EmailSendingService;
import com.inspireon.chuyentrolinhtinh.common.util.EmailUtils;
import com.inspireon.chuyentrolinhtinh.model.manager.UserManager;

@Service
public class UserManagerImpl implements UserManager{
	
	/**
	 * @author Phuong
	 */
	@Override
	public String generateRandomPassword() {
		
		return new BigInteger(30, new SecureRandom()).toString(32);
	}
	
	public String encodePassword(String password){
		return new BCryptPasswordEncoder().encode(password);
	}

	/**
	 * @author Phuong
	 */
	@Override
	public void emailPassword(String email, String password) throws AddressException, MessagingException {
		String subject = "MyStory! Reset password";
		String text = "Chào bạn! \n Bạn đã yêu cầu reset password từ MyStory. Dưới đây là password mới của bạn :\n" + password;
		EmailUtils.sendEmail(email, subject, text);
		
		EmailSendingService emailSendingService = new EmailSendingService();
		
		emailSendingService.setToEmail(email);
	    emailSendingService.setSubject(subject);
	    emailSendingService.setContent(text);
	    
	    emailSendingService.start();
	}
	
	/**
	 * @author Phuong
	 */
	@Override
	public void emailNewPasswordToFacebookUser(String email, String username, String password) throws AddressException, MessagingException {
		String subject = "MyStory! Xác nhận đăng ký";
		String text = "Chào bạn! \n Bạn đã đăng ký tham gia MyStory với tên: " + username + ". Dưới đây là password của bạn :\n" + password;
		text = text + "\n" + "Bạn có thể chọn đăng nhập qua facebook hoặc sử dụng username và password này.";
		EmailUtils.sendEmail(email, subject, text);
		
		EmailSendingService emailSendingService = new EmailSendingService();
		
		emailSendingService.setToEmail(email);
	    emailSendingService.setSubject(subject);
	    emailSendingService.setContent(text);
	    
	    emailSendingService.start();
	}
}
