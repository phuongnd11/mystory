package com.inspireon.mystory.model.manager.impl;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.stereotype.Service;

import com.inspireon.mystory.application.EmailSendingService;
import com.inspireon.mystory.infrastructure.MYSConfig;
import com.inspireon.mystory.model.domain.user.InvitationCode;
import com.inspireon.mystory.model.manager.BetaUserManager;

/**
 * 
 * @author Phuong
 *
 */
@Service
public class BetaUserManagerImpl extends Thread implements BetaUserManager{

	@Override
	public void sendInvitation(InvitationCode invitationCode, String toEmail) throws AddressException, MessagingException{
				
	    String subject = "Mời tham gia mystory.vn";
	    String content = "Chào bạn! \n Bạn đã được mời tham gia mystory.vn. Đây là code dùng để đăng ký :\n" + invitationCode.code();
	    
	    EmailSendingService emailSendingService = new EmailSendingService();
		emailSendingService.setToEmail(toEmail);
	    emailSendingService.setSubject(subject);
	    emailSendingService.setContent(content);
	    
	    emailSendingService.start();		
		
	}
	

	@Override
	public void sendActiveLink(String userId, String toEmail)
			throws AddressException, MessagingException {
		
		String url = 
			    MYSConfig.getInstance().getAsString("siteurl") + "user/activate/" + userId;
	    String subject = "Hoàn thành đăng ký mystory.vn";
	    String content = "Bạn đã đăng ký mystory.vn với email này. Hãy hoàn thành bước cuối cùng "
	    		+ "để có thể tham gia chia sẻ trên mystory bằng cách click vào link dưới đây :\n" +
	    		url;
	    
	    EmailSendingService emailSendingService = new EmailSendingService();
	    emailSendingService.setToEmail(toEmail);
	    emailSendingService.setSubject(subject);
	    emailSendingService.setContent(content);
	    
	    emailSendingService.start();
	    
	}

}
