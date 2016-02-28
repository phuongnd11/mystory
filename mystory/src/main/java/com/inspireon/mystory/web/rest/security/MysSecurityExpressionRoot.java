package com.inspireon.mystory.web.rest.security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import com.inspireon.mystory.application.SecurityService;
import com.inspireon.mystory.model.domain.user.Role;
import com.inspireon.mystory.model.domain.user.Status;
import com.inspireon.mystory.web.rest.shared.context.MysAction;
import com.inspireon.mystory.web.rest.shared.context.MysObject;
import com.inspireon.mystory.web.rest.shared.context.MystoryUser;

public class MysSecurityExpressionRoot extends SecurityExpressionRoot 
				implements MethodSecurityExpressionOperations {
    private Object filterObject;
    private Object returnObject;
    private Object target;
    private MysSecurityManager mysSecurityManager;
    private SecurityService securityService;
    
	public MysSecurityExpressionRoot(Authentication auth) {
		super(auth);
	}		
	
	/*
	 **********************************************************************************************************
	 ***************************************** MYSTORY SECURITY METHODS ***************************************
	 **********************************************************************************************************
	 */
	
	//isAuthenticated, have UPDATE permission, or author
	public boolean canEditStory(String storyId) {
		Object currentUser = this.getPrincipal();
		if (currentUser instanceof MystoryUser) {
			MystoryUser user =  (MystoryUser) currentUser;
			if (Status.BANNED.equals(user.getStatus())) return false;
			
			return mysSecurityManager.hasPermis(Role.getByKey(user.getRole()), MysObject.STORY, MysAction.UPDATE)
					|| securityService.checkStoryAuthor(storyId, user.getUsername());
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
	public boolean canEditComment(String commentId) {
		Object currentUser = this.getPrincipal();
		if (currentUser instanceof MystoryUser) {
			MystoryUser user =  (MystoryUser) currentUser;
			if (Status.BANNED.equals(user.getStatus())) return false;
			
			return mysSecurityManager.hasPermis(Role.getByKey(user.getRole()), MysObject.COMMENT, MysAction.UPDATE)
					|| securityService.checkCommentAuthor(commentId, user.getUsername());
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

	//isAuthenticated, have CREATE permission
	public boolean isMyStoryUser(String username, String password) {
		return securityService.checkUserValid(username, password);		
	}	
	
	/*
	 **********************************************************************************************************
	 ***************************************** END ************************************************************
	 **********************************************************************************************************
	 */
	
	@Override
	public boolean hasPermission(Object mysObj, Object mysAct) {
		return false;
	}
	
	@Override
	public boolean hasPermission(Object targetId, String targetType,
			Object permission) {
		return false;
	}	
	
	@Override
	public void setFilterObject(Object filterObject) {
		this.filterObject = filterObject;
	}
	
	@Override
	public Object getFilterObject() {
		return filterObject;
	}
	
	@Override
	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}
	
	@Override
	public Object getReturnObject() {
		return returnObject;
	}

	public void setThis(Object target) {
		this.target = target;
	}
	
	@Override
	public Object getThis() {
		return target;
	}
	
	public void setMysSecurityManager(MysSecurityManager mysSecurityManager) {
		this.mysSecurityManager = mysSecurityManager;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}	
}