package com.inspireon.chuyentrolinhtinh.model.domain.user;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.inspireon.chuyentrolinhtinh.model.domain.shared.Entity;

@Document(collection="invitation_codes")
public class InvitationCode extends Entity<InvitationCode>{
	
	private String code;
	
	private String generatedBy;
	
	private Date generatedTime;
	
	private String invited;
	
	private CodeStatus status; 
	
	
	public InvitationCode(String generatedBy){
		this.generatedBy = generatedBy;
		this.code = generateInvitationCode();
		this.generatedTime = new Date();
		this.status = CodeStatus.NEW;
	}

	
	private String generateInvitationCode(){
		long longCode = (new Date(). getTime() - 1384504433000l);
		if(longCode < 0) longCode = new Date(). getTime(); 
		return String.valueOf(longCode);
	}
	
	public String code(){
		return code;
	}
	
	public Date generatedTime(){
		return generatedTime;
	}
	
	public String invited(){
		return invited;
	}

	public CodeStatus codeStatus(){
		return status;
	}
	
	public boolean isAvailable(){
		if(status != CodeStatus.TAKEN){
			return true;
		}
		else return false;
	}

	public void updateInvited(String invited){
		this.invited = invited;
		this.status = CodeStatus.TAKEN;
	}
	
	@Override
	public boolean sameIdentityAs(InvitationCode other) {
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

}
