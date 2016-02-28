package com.inspireon.mystory.web.rest.user;

import java.io.Serializable;

import com.inspireon.mystory.common.util.DateUtils;
import com.inspireon.mystory.common.util.ProcessBBCode;
import com.inspireon.mystory.model.domain.user.Message;
import com.inspireon.mystory.web.rest.shared.i18n.I18NCode;
import com.inspireon.mystory.web.rest.shared.i18n.I18NUtils;

/**
 * View adapter for displaying message detail
 * @author Cragon
 *
 */
public class UserMessageViewAdapter implements Serializable {

	private static final long serialVersionUID = 8579225858950462657L;
	
	private I18NUtils i18nUtils;
	
	// Should User implement Serializable interface?  
	private final Message message;

	public UserMessageViewAdapter(Message message, I18NUtils i18nUtils) {
		this.i18nUtils = i18nUtils;
		this.message = message;
	}
	
	public String getContent() {
		return new ProcessBBCode().preparePostText(message.content());
	}
	
	public String getFrom() {
		return message.from();
	}
	
	public String getTo() {
		return message.to();
	}
	
	public String getTitle() {
		return message.title();
	}
	
	public String getSubmittedTime() {
		return DateUtils.getTimeAgo(message.submittedTime().getTime(), 
				i18nUtils.getMessage(I18NCode.TIME_JUST_NOW), i18nUtils.getMessage(I18NCode.TIME_A_MINUTE_AGO),
				i18nUtils.getMessage(I18NCode.TIME_MINUTES_AGO), i18nUtils.getMessage(I18NCode.TIME_AN_HOUR_AGO),
				i18nUtils.getMessage(I18NCode.TIME_HOURS_AGO), i18nUtils.getMessage(I18NCode.TIME_YESTERDAY), i18nUtils.getMessage(I18NCode.TIME_DAYS_AGO));
	}
}
