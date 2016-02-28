package com.inspireon.mystory.web.rest.story.reading;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.inspireon.mystory.common.util.DateUtils;
import com.inspireon.mystory.common.util.ImageUtils;
import com.inspireon.mystory.common.util.ProcessBBCode;
import com.inspireon.mystory.model.domain.story.Story;
import com.inspireon.mystory.model.domain.user.User;
import com.inspireon.mystory.web.rest.shared.i18n.I18NCode;
import com.inspireon.mystory.web.rest.shared.i18n.I18NUtils;

public class StoryViewAdapter implements Serializable {

	private static final long serialVersionUID = -6679913942652990548L;

	private List<CommentViewAdapter> comments;
	private final User author;
	private final Story story;
	private final List<Story> siblingChapters;
	private final String reader;
	private final I18NUtils i18nUtils;

	public StoryViewAdapter(Story story, List<Story> siblingChapters, User author, String reader, I18NUtils i18nUtils) {
		this.story = story;
		this.siblingChapters = siblingChapters;
		this.author = author;
		this.reader = reader;
		this.i18nUtils = i18nUtils;
	}
	
	public String getId() {
		return story.id();
	}
	
	public String getTitle() {
		return story.title();
	}

	
	public String getContent() throws IOException {
		ProcessBBCode processBBCode = new ProcessBBCode();
		
		if(story.lastEditedTime().before(new Date(1388479849883l))){
			processBBCode.setAcceptHTML(true);
			processBBCode.setAcceptBBCode(false);
		}
		return processBBCode.preparePostText(story.content());
	}

	public int getViewCount() {
		return story.views();
	}
	
	public int getCommentCount() {
		return story.commentCount();
	}

	public String getFeaturedImage() {
		if (story.featuredImage() != null)
			return ImageUtils.getFullImageURL(story.featuredImage().small());
		else 
			return StringUtils.EMPTY;
	}
	
	public String getLargeFeaturedImage(){
		if (story.featuredImage() != null)
			return ImageUtils.getFullImageURL(story.featuredImage().large());
		else 
			return StringUtils.EMPTY;
	}

	public int getVoteCount() {
		return story.point();
	}
	
	public boolean isVotedUpByCurrentReader() {
		if (reader == null) { //if user did not logged in
			return false;
		} else { //if user is already logged in
			return story.isAlreadyVotedUpBy(reader);
		}
	}
	
	public boolean isVotedDownByCurrentReader() {
		if (reader == null) { //if user did not logged in
			return false;
		} else { //if user is already logged in
			return story.isAlreadyVotedDownBy(reader);
		}
	}
	
	public String getCategoryDisplayName() {
		return story.tag().displayName();
	}
	
	public String getCategoryName() {
		return story.tag().name().toLowerCase();
	}
	
	public String getSubmittedDate() {
		return DateUtils.dateToViString(story.submittedDate());
	}
	
	public List<ChapterViewAdapter> getPreviousChapters() {
		List<ChapterViewAdapter> previousChapters = new ArrayList<ChapterViewAdapter>(siblingChapters.size());
		
		for (Story siblingChapter : siblingChapters) {
			if (siblingChapter.submittedDate().compareTo(story.submittedDate()) < 0) {
				previousChapters.add(new ChapterViewAdapter(siblingChapter));
			}
		}
		
		return previousChapters;
	}
	
	public List<ChapterViewAdapter> getNextChapters() {
		List<ChapterViewAdapter> nextChapters = new ArrayList<ChapterViewAdapter>(siblingChapters.size());
		
		for (Story siblingChapter : siblingChapters) {
			if (siblingChapter.submittedDate().compareTo(story.submittedDate()) > 0) {
				nextChapters.add(new ChapterViewAdapter(siblingChapter));
			}
		}
		
		return nextChapters;
	}
	
	public AuthorViewAdapter getAuthor() {
		return new AuthorViewAdapter(author, reader);
	}
	
	public void setComments(List<CommentViewAdapter> comments) {
		this.comments = comments;
	}
	
	public List<CommentViewAdapter> getComments() {
		return comments;
	}
	
	public String getShortTime() {
		return DateUtils.getTimeAgo(story.submittedDate().getTime(), 
				i18nUtils.getMessage(I18NCode.TIME_JUST_NOW), i18nUtils.getMessage(I18NCode.TIME_A_MINUTE_AGO),
				i18nUtils.getMessage(I18NCode.TIME_MINUTES_AGO), i18nUtils.getMessage(I18NCode.TIME_AN_HOUR_AGO),
				i18nUtils.getMessage(I18NCode.TIME_HOURS_AGO), i18nUtils.getMessage(I18NCode.TIME_YESTERDAY), i18nUtils.getMessage(I18NCode.TIME_DAYS_AGO)); 
	}
}
