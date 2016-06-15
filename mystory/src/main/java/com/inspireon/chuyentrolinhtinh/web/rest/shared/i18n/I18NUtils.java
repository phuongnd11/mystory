package com.inspireon.chuyentrolinhtinh.web.rest.shared.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import com.inspireon.chuyentrolinhtinh.web.rest.security.MystoryUserReference;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.context.MystoryUser;

public class I18NUtils {
	private static final String VIETNAMESE = "vi";
	
	public static final String DEFAULT_LANG = VIETNAMESE;
	
	@Autowired
	private MessageSource messageSource;

	public String getMessage(String code) {
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		return getMessage(code, currentUser == null ? DEFAULT_LANG : currentUser.getCurrentLang());
	}
	
	public String getMessage(String code, String[] args) {
		MystoryUser currentUser = MystoryUserReference.getLoggedInUser();
		return getMessage(code, args, currentUser == null ? DEFAULT_LANG : currentUser.getCurrentLang());
	}
	
	public String getMessage(String code, String lang) {
		return getMessage(code, null, lang);
	}
	
	public String getMessage(String code, String[] args, String lang) {
		if (lang == null) lang = DEFAULT_LANG;
		return messageSource.getMessage(code, args, StringUtils.parseLocaleString(lang));
	}
	
	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
