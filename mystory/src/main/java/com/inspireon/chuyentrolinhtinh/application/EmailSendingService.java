package com.inspireon.chuyentrolinhtinh.application;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;

import com.inspireon.chuyentrolinhtinh.common.util.EmailUtils;

public class EmailSendingService extends Thread{
	
	private static final Logger logger = Logger.getLogger(EmailSendingService.class);
	
	private String toEmail;
	
	private String subject;
	
	private String content;
	
	public void run(){
		
		try {
			EmailUtils.sendEmail(toEmail, subject, content);
		} catch (AddressException e) {
			logger.error("Error sending mail to " + toEmail);
			e.printStackTrace();
		} catch (MessagingException e) {
			logger.error("MessagingException sending mail to " + toEmail + " with subject: " + subject);
			e.printStackTrace();
		}
		
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
