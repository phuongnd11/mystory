package com.inspireon.mystory.web.rest.shared.i18n;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

public class I18NInterceptor extends HandlerInterceptorAdapter  {
	/**
	 * Default name of the locale specification parameter: "lang".
	 */
	public static final String DEFAULT_PARAM_NAME = "lang";

	private String paramName = DEFAULT_PARAM_NAME;


	/**
	 * Set the name of the parameter that contains a locale specification
	 * in a locale change request. Default is "locale".
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * Return the name of the parameter that contains a locale specification
	 * in a locale change request.
	 */
	public String getParamName() {
		return this.paramName;
	}


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		// always using Vietnamese
		localeResolver.setLocale(request, response, StringUtils.parseLocaleString(I18NUtils.DEFAULT_LANG));		
		
//		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();		
//		String locale = request.getParameter(paramName);
//		
//		if (currentUser != null) {		
//			if (locale != null) {
//				currentUser.setCurrentLang(locale);
//			} 
//				
//			if (currentUser.getCurrentLang() != null) {
//				localeResolver.setLocale(request, response, StringUtils.parseLocaleString(currentUser.getCurrentLang()));
//			} else {
//				//localeResolver.setLocale(request, response, localeResolver.resolveLocale(request));
//				localeResolver.setLocale(request, response, StringUtils.parseLocaleString(I18NUtils.DEFAULT_LANG));
//			}
//		} else {
//			if (locale != null) { 
//				localeResolver.setLocale(request, response, StringUtils.parseLocaleString(locale));
//			} else {
//				//localeResolver.setLocale(request, response, localeResolver.resolveLocale(request));
//				localeResolver.setLocale(request, response, StringUtils.parseLocaleString(I18NUtils.DEFAULT_LANG));
//			}			
//		}
		
		return true;
	}	
}
