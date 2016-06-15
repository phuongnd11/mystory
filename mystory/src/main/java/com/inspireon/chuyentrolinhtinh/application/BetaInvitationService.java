package com.inspireon.chuyentrolinhtinh.application;

import java.util.Collection;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.inspireon.chuyentrolinhtinh.model.domain.user.InvitationCode;


/**
 * 
 * @author Phuong
 * 
 *
 */
public interface BetaInvitationService {
	
	boolean checkValidCode(String code);
	
	InvitationCode generateInvitationCode(String generatedBy);

	void sendInvitation(String fromUser, String toEmail) throws AddressException, MessagingException;
	
	Collection<InvitationCode> findByUser(String generatedBy);
	
	InvitationCode findByCode(String code);
	
	void store(InvitationCode invitationCode);
}
