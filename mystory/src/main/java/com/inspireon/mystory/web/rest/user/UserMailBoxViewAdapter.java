package com.inspireon.mystory.web.rest.user;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.inspireon.mystory.common.util.DateUtils;
import com.inspireon.mystory.model.domain.user.Message;
import com.inspireon.mystory.web.rest.shared.i18n.I18NCode;
import com.inspireon.mystory.web.rest.shared.i18n.I18NUtils;

/**
 * View adapter for displaying user mailbox (Inboxes/SentItems)
 * @author Cragon
 *
 */
public class UserMailBoxViewAdapter implements Serializable {

	private static final long serialVersionUID = 8579225858950462657L;
	  
	private final Set<UserMessage> messages;
	
	

	public UserMailBoxViewAdapter(I18NUtils i18nUtils, List<Message> messages, boolean isSender) {
		this.messages = new TreeSet<UserMessage>(new Comparator<UserMessage>() {
			@Override
			public int compare(UserMessage m1, UserMessage m2) {
				return m2.getSubmittedTime().compareTo(m1.getSubmittedTime());
			}			
		});
		for (Message message : messages) {
			String fromTo = isSender ? message.to() : message.from();
			this.messages.add(new UserMessage(i18nUtils, fromTo, message.title(), message.submittedTime(), message.isRead()));
		}
	}
	
	public Set<UserMessage> getMessages() {
		return messages;
	}
	
	public static class UserMessage implements Serializable {
		private static final long serialVersionUID = 971692357735756716L;
		private String fromTo;
		private String title;
		private Date submittedTime;
		private boolean read;

		private final I18NUtils i18nUtils;
		
		public UserMessage(I18NUtils i18nUtils, String fromTo, String title,
							Date submittedTime, boolean read) {
			this.i18nUtils = i18nUtils;
			this.fromTo = fromTo;
			this.title = title;
			this.submittedTime = submittedTime;
			this.read = read;
		}

		public String getFromTo() {
			return fromTo;
		}

		public String getTitle() {
			return title;
		}

		public Date getSubmittedTime() {
			return submittedTime;
		}
		
		public String getFormatedTime() {
			return DateUtils.getTimeAgo(submittedTime.getTime(), 
					i18nUtils.getMessage(I18NCode.TIME_JUST_NOW), i18nUtils.getMessage(I18NCode.TIME_A_MINUTE_AGO),
					i18nUtils.getMessage(I18NCode.TIME_MINUTES_AGO), i18nUtils.getMessage(I18NCode.TIME_AN_HOUR_AGO),
					i18nUtils.getMessage(I18NCode.TIME_HOURS_AGO), i18nUtils.getMessage(I18NCode.TIME_YESTERDAY), i18nUtils.getMessage(I18NCode.TIME_DAYS_AGO));
		}		

		public boolean isRead() {
			return read;
		}	
	}
}
