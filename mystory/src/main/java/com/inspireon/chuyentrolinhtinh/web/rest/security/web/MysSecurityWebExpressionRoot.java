package com.inspireon.chuyentrolinhtinh.web.rest.security.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import com.inspireon.chuyentrolinhtinh.model.domain.user.Role;
import com.inspireon.chuyentrolinhtinh.model.domain.user.Status;
import com.inspireon.chuyentrolinhtinh.web.rest.security.MysSecurityManager;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.context.MysAction;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.context.MysObject;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.context.MystoryUser;

public class MysSecurityWebExpressionRoot extends WebSecurityExpressionRoot{

    private MysSecurityManager mysSecurityManager;
    
	public MysSecurityWebExpressionRoot(Authentication auth, FilterInvocation fi) {
		super(auth, fi);
	}
		
	/*
	 * Mystory security methods
	 */
	
	//isAuthenticated, have UPDATE permission, or author
	public boolean canEditStory(String author) {
		Object currentUser = this.getPrincipal();
		if (currentUser instanceof MystoryUser) {
			MystoryUser user =  (MystoryUser) currentUser;
			if (Status.BANNED.equals(user.getStatus())) return false;
			
			return mysSecurityManager.hasPermis(Role.getByKey(user.getRole()), MysObject.STORY, MysAction.UPDATE)
					|| StringUtils.equalsIgnoreCase(author, user.getUsername());
		} else { 
			//GUEST		
			return mysSecurityManager.hasPermis(Role.GUEST, MysObject.STORY, MysAction.UPDATE);
		}
	}
	
	//isAuthenticated, have CREATE permission
	public boolean canCreateStory() {
		Object currentUser = this.getPrincipal();
		if (currentUser instanceof MystoryUser) {
			MystoryUser user =  (MystoryUser) currentUser;
			if (Status.BANNED.equals(user.getStatus())) return false;
			
			return mysSecurityManager.hasPermis(Role.getByKey(user.getRole()), MysObject.STORY, MysAction.CREATE);
		} else { 
			//GUEST		
			return mysSecurityManager.hasPermis(Role.GUEST, MysObject.STORY, MysAction.CREATE);
		}		
	}
	
	/*
	 * Mystory security methods
	 */
	
	//isAuthenticated, have UPDATE permission, or author
	public boolean canEditComment(String author) {
		Object currentUser = this.getPrincipal();
		if (currentUser instanceof MystoryUser) {
			MystoryUser user =  (MystoryUser) currentUser;
			if (Status.BANNED.equals(user.getStatus())) return false;
			
			return mysSecurityManager.hasPermis(Role.getByKey(user.getRole()), MysObject.COMMENT, MysAction.UPDATE)
					|| StringUtils.equalsIgnoreCase(author, user.getUsername());
		} else { 
			//GUEST		
			return mysSecurityManager.hasPermis(Role.GUEST, MysObject.COMMENT, MysAction.UPDATE);
		}
	}
	
	//isAuthenticated, have CREATE permission
	public boolean canCreateComment() {
		Object currentUser = this.getPrincipal();
		if (currentUser instanceof MystoryUser) {
			MystoryUser user =  (MystoryUser) currentUser;
			if (Status.BANNED.equals(user.getStatus())) return false;
			
			return mysSecurityManager.hasPermis(Role.getByKey(user.getRole()), MysObject.COMMENT, MysAction.CREATE);
		} else { 
			//GUEST		
			return mysSecurityManager.hasPermis(Role.GUEST, MysObject.COMMENT, MysAction.CREATE);
		}		
	}	
	
	
	public boolean hasPermis(String mysObj, String mysAct) {
		Object user = this.getPrincipal();
		if (user instanceof MystoryUser) {
			return mysSecurityManager.hasPermis(Role.getByKey(((MystoryUser)user).getRole()), 
												MysObject.getByValue(mysObj), MysAction.getByValue(mysAct));	
		} else { //GUEST
			return mysSecurityManager.hasPermis(Role.GUEST, MysObject.getByValue(mysObj), MysAction.getByValue(mysAct));
		}		
	}

	public void setMysSecurityManager(MysSecurityManager mysSecurityManager) {
		this.mysSecurityManager = mysSecurityManager;
	}
}