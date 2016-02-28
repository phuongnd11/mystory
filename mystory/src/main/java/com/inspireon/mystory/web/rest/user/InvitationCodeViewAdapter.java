package com.inspireon.mystory.web.rest.user;

import java.io.Serializable;

import com.inspireon.mystory.common.util.DateUtils;
import com.inspireon.mystory.model.domain.user.InvitationCode;

/**
 * 
 * @author Phuong
 *
 */
public class InvitationCodeViewAdapter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7178399814117599632L;
	private final InvitationCode invitationCode;
	
	public InvitationCodeViewAdapter(InvitationCode invitationCode){
		this.invitationCode = invitationCode;
	}
	
	public String getCode(){
		return invitationCode.code();
	}
	
	public String getGeneratedTime(){
		return DateUtils.dateToString(invitationCode.generatedTime());
	}
	
	public String getStatus(){
		return invitationCode.codeStatus().name();
	}
	
	public String getInvited(){
		return invitationCode.invited();
	}
	
	
}
