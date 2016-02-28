package com.inspireon.mystory.application.impl;

import java.util.Collection;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inspireon.mystory.application.BetaInvitationService;
import com.inspireon.mystory.model.domain.user.InvitationCode;
import com.inspireon.mystory.model.domain.user.InvitationCodeRepo;
import com.inspireon.mystory.model.manager.BetaUserManager;

/**
 * 
 * @author Phuong
 *
 */
@Service
public class BetaInvitationServiceImpl implements BetaInvitationService{

	@Autowired
	BetaUserManager betaUserManager;
	
	@Autowired
	InvitationCodeRepo invitationCodeRepo;
	
	@Override
	public InvitationCode generateInvitationCode(String generatedBy) {
		InvitationCode inviteCode = new InvitationCode(generatedBy);
		invitationCodeRepo.store(inviteCode);
		if(inviteCode.id() != null)
			return inviteCode;
		else return null;
	}

	@Override
	public void sendInvitation(String fromUser, String toEmail) throws AddressException, MessagingException{
		
		InvitationCode invitationCode = new InvitationCode(fromUser);
		invitationCodeRepo.store(invitationCode);
		if(invitationCode.id() != null)
			betaUserManager.sendInvitation(invitationCode, toEmail);
		return;
	}

	@Override
	public Collection<InvitationCode> findByUser(String generatedBy) {
		return invitationCodeRepo.findByUser(generatedBy);
	}

	@Override
	public InvitationCode findByCode(String code) {
		return invitationCodeRepo.findByCode(code);
	}

	@Override
	public void store(InvitationCode invitationCode) {
		invitationCodeRepo.store(invitationCode);
	}

	@Override
	public boolean checkValidCode(String code) {
		InvitationCode inviteCode =  invitationCodeRepo.findByCode(code);
		if(inviteCode==null) return false;
		else if(inviteCode.isAvailable()) return true;
		return false;
	}
}
