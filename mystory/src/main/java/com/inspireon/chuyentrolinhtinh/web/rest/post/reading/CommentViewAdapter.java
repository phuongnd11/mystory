package com.inspireon.chuyentrolinhtinh.web.rest.post.reading;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import com.inspireon.chuyentrolinhtinh.common.util.DateUtils;
import com.inspireon.chuyentrolinhtinh.common.util.ProcessBBCode;
import com.inspireon.chuyentrolinhtinh.model.domain.comment.Comment;
import com.inspireon.chuyentrolinhtinh.model.domain.user.User;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.i18n.I18NCode;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.i18n.I18NUtils;

public class CommentViewAdapter implements Serializable {

	private static final long serialVersionUID = -6679913942652990548L;

	private final User author;
	private final Comment comment;
	private final String reader;
	private final I18NUtils i18nUtils;

	public CommentViewAdapter(User author, Comment comment, String reader, I18NUtils i18nUtils) {
		this.author = author;
		this.comment = comment;
		this.reader = reader;
		this.i18nUtils = i18nUtils;
	}
	
	public AuthorViewAdapter getAuthor() {
		return new AuthorViewAdapter(author, reader);
	}

	public String getContent() throws IOException {
		ProcessBBCode processBBCode = new ProcessBBCode();
		
		if(comment.lastEditedTime().before(new Date(1388479849883l))){
			processBBCode.setAcceptHTML(true);
			processBBCode.setAcceptBBCode(false);
		}
		return new ProcessBBCode().preparePostText(comment.content());
	}

	public int getVoteCount() {
		return comment.point();
	}
	
	public String getId() {
		return comment.id();
	}
	
	public boolean isVotedUpByCurrentReader() {
		if (reader == null) { //if user did not logged in
			return false;
		} else { //if user is already logged in
			return comment.isAlreadyVotedUpBy(reader);
		}
	}
	
	public boolean isVotedDownByCurrentReader() {
		if (reader == null) { //if user did not logged in
			return false;
		} else { //if user is already logged in
			return comment.isAlreadyVotedDownBy(reader);
		}
	}
	
	public String getSubmittedDate() {
		return DateUtils.dateToViString(comment.submittedDate());
	}
	
	public String getShortTime() {
		return DateUtils.getTimeAgo(comment.submittedDate().getTime(), 
				i18nUtils.getMessage(I18NCode.TIME_JUST_NOW), i18nUtils.getMessage(I18NCode.TIME_A_MINUTE_AGO),
				i18nUtils.getMessage(I18NCode.TIME_MINUTES_AGO), i18nUtils.getMessage(I18NCode.TIME_AN_HOUR_AGO),
				i18nUtils.getMessage(I18NCode.TIME_HOURS_AGO), i18nUtils.getMessage(I18NCode.TIME_YESTERDAY), i18nUtils.getMessage(I18NCode.TIME_DAYS_AGO)); 
	}
}
