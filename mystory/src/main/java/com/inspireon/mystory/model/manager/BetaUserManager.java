package com.inspireon.mystory.model.manager;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.inspireon.mystory.model.domain.user.InvitationCode;

public interface BetaUserManager {
	
	void sendInvitation(InvitationCode invitationCode, String toEmail) throws AddressException, MessagingException;
	
	void sendActiveLink(String userId, String toEmail) throws AddressException, MessagingException;
}
