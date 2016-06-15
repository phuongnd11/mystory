package com.inspireon.chuyentrolinhtinh.web.rest.home.viewing;

import com.inspireon.chuyentrolinhtinh.common.util.DateUtils;
import com.inspireon.chuyentrolinhtinh.infrastructure.MYSConfig;
import com.inspireon.chuyentrolinhtinh.model.domain.notification.ActionType;
import com.inspireon.chuyentrolinhtinh.model.domain.notification.Notification;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.i18n.I18NCode;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.i18n.I18NUtils;

public class NotificationViewAdapter {
	
	private Notification notification;
	
	private I18NUtils i18nUtils;
	
	private boolean read;
	
	public NotificationViewAdapter(Notification notification, I18NUtils i18nUtils, boolean read){
		this.notification = notification;
		this.i18nUtils = i18nUtils;
		this.read = read;
	}
	
	
	public boolean isRead() {
		return read;
	}

	public String getMessage(){
		
		StringBuffer message = new StringBuffer();
		
		if(notification.actionType().equals(ActionType.COMMENT)){
			
			if(notification.total() == 1){
				message.append(notification.message() + " ");
			}
			else if (notification.total() > 1){
				message.append(notification.message() + " ");
				String [] other = {String.valueOf((notification.total() - 1))};
				message.append(i18nUtils.getMessage(I18NCode.MESSAGE_NOTIFICATION_MENTION_OTHERS, other));
			}
			message.append(i18nUtils.getMessage(I18NCode.MESSAGE_NOTIFICATION_COMMENT));
		} 
		else if(notification.actionType().equals(ActionType.POST)){
				message.append(notification.message() + " ");
				message.append(i18nUtils.getMessage(I18NCode.MESSAGE_NOTIFICATION_POST));
		}
		else if(notification.actionType().equals(ActionType.MESSAGE)){
			String mentionTotal [] = {String.valueOf(notification.total())}; 
			message.append(i18nUtils.getMessage(I18NCode.MESSAGE_NOTIFICATION_MESSAGE, mentionTotal) + " ");
			message.append(notification.message() + " ");
			int froms = Integer.parseInt(notification.followeeId());
			if(froms > 1){
				String [] other = {String.valueOf(froms - 1)};
				message.append(i18nUtils.getMessage(I18NCode.MESSAGE_NOTIFICATION_MENTION_OTHERS, other));
			}
		}
		else if(notification.actionType().equals(ActionType.FAN)){
			message.append(notification.message() + " ");
			if(notification.total() == 1){
			}
			else if(notification.total() > 1){
				String [] other = {String.valueOf((notification.total() - 1))};
				message.append(i18nUtils.getMessage(I18NCode.MESSAGE_NOTIFICATION_MENTION_OTHERS, other));
			}
			
			message.append(i18nUtils.getMessage(I18NCode.MESSAGE_NOTIFICATION_FAN));
		}
		
		return message.toString();
	}
	
	
	public String getSourceTitle(){
		if(notification.actionType().equals(ActionType.POST) || notification.actionType().equals(ActionType.COMMENT))
		{
			return notification.sourceTitle();
		}
		else if (notification.actionType().equals(ActionType.MESSAGE)){
			return i18nUtils.getMessage(I18NCode.MESSAGE_NOTIFICATION_VIEW_MESSAGE_TITLE);
		}
		else if (notification.actionType().equals(ActionType.FAN)){
			return i18nUtils.getMessage(I18NCode.MESSAGE_NOTIFICATION_FAN_VIEW_TITLE) + " " + notification.message();
		}
		else return "";
		
	}
	
	public String getActionDate(){
		return DateUtils.getTimeAgo(notification.actionDate().getTime(), 
				i18nUtils.getMessage(I18NCode.TIME_JUST_NOW), i18nUtils.getMessage(I18NCode.TIME_A_MINUTE_AGO),
				i18nUtils.getMessage(I18NCode.TIME_MINUTES_AGO), i18nUtils.getMessage(I18NCode.TIME_AN_HOUR_AGO),
				i18nUtils.getMessage(I18NCode.TIME_HOURS_AGO), i18nUtils.getMessage(I18NCode.TIME_YESTERDAY), i18nUtils.getMessage(I18NCode.TIME_DAYS_AGO));
	}
	
	public String getUrl(){
		if(notification.actionType().equals(ActionType.POST) || notification.actionType().equals(ActionType.COMMENT))
		{
			String url = MYSConfig.getInstance().getAsString("story.url");
			url += notification.sourceId();
			return url;
		}
		else if (notification.actionType().equals(ActionType.MESSAGE)){
			String url = MYSConfig.getInstance().getAsString("message.url");
			return url;
		}
		else if (notification.actionType().equals(ActionType.FAN)){
			String url = MYSConfig.getInstance().getAsString("user.url");
			return url + notification.message();
		}
		else return "";
	}
}
