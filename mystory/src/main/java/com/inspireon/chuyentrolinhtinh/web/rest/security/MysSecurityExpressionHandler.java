package com.inspireon.chuyentrolinhtinh.web.rest.security;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import com.inspireon.chuyentrolinhtinh.application.SecurityService;

public class MysSecurityExpressionHandler extends
		DefaultMethodSecurityExpressionHandler implements MethodSecurityExpressionHandler {
	
	@Autowired
	MysSecurityManager mysSecurityManager;
	
	@Autowired
	SecurityService securityService;	
	
	@Override
	protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
			Authentication authentication, MethodInvocation invocation) {
		MysSecurityExpressionRoot root = new MysSecurityExpressionRoot(authentication);
		root.setThis(invocation.getThis());
		//root.setPermissionEvaluator(getPermissionEvaluator());
		root.setMysSecurityManager(mysSecurityManager);
		root.setSecurityService(securityService);
		return root;
	}
}