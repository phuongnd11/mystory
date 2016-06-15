package com.inspireon.chuyentrolinhtinh.web.rest.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import com.inspireon.chuyentrolinhtinh.web.rest.security.MysSecurityManager;

public class MysSecurityWebExpressionHandler 
		extends DefaultWebSecurityExpressionHandler{
	
	private final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
	
	@Autowired
	MysSecurityManager mysSecurityManager;
	
	@Override
	protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, FilterInvocation fi) {
		MysSecurityWebExpressionRoot root = new MysSecurityWebExpressionRoot(authentication, fi);
		root.setMysSecurityManager(mysSecurityManager);
		root.setTrustResolver(trustResolver);
		return root;
	}
}