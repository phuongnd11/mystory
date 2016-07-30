package com.inspireon.chuyentrolinhtinh.web.rest.home.viewing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.inspireon.chuyentrolinhtinh.common.util.DateUtils;
import com.inspireon.chuyentrolinhtinh.common.util.ImageUtils;
import com.inspireon.chuyentrolinhtinh.common.util.ProcessBBCode;
import com.inspireon.chuyentrolinhtinh.model.domain.post.Post;
import com.inspireon.chuyentrolinhtinh.model.domain.shared.Voter;
import com.inspireon.chuyentrolinhtinh.model.domain.user.User;
import com.inspireon.chuyentrolinhtinh.web.rest.post.reading.AuthorViewAdapter;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.i18n.I18NCode;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.i18n.I18NUtils;

public class HomePostViewAdapter implements Serializable {
	
	public static Logger logger = Logger.getLogger(HomePostViewAdapter.class);
	
	private static final long serialVersionUID = -7300527276340256504L;
	
	private final int EXCERPT_LENGTH = 120; 
	
	private final User author;
	private final Post story;
	private final String reader;
	private final I18NUtils i18nUtils;

	public HomePostViewAdapter(Post story, User author, String reader, I18NUtils i18nUtils) {
		this.story = story;
		this.author = author;
		this.reader = reader;
		this.i18nUtils = i18nUtils;
	}
	
	public String getId() {
		return story.id();
	}
	
	public String getUrl() {
		return "/story/" + story.id();
	}
	
	public String getTitle() {
		return story.title();
	}

	public String getContent() {
		
		ProcessBBCode processBBCode = new ProcessBBCode();
		processBBCode.setAcceptHTML(false);
		String content = processBBCode.prepareExcerpt(story.content());
		
		BufferedReader bufReader = new BufferedReader(new StringReader(content));
		String line=null;
		String twoLinesContent = "";
		
		int count = 0;
		try {
			while( (line=bufReader.readLine()) != null && count < 2)
			{
				count++;
				twoLinesContent += line;
			}
		} catch (IOException e) {
			logger.error("error processing exerpt for " + content);
		}
		
		content = twoLinesContent;
		
		if(content!=null && content.length() <= EXCERPT_LENGTH)
			return content;
		else{
			 int endIdx = EXCERPT_LENGTH;
			    while (endIdx < (EXCERPT_LENGTH + 10) && content.charAt(endIdx) != ' ' && endIdx < content.length()-1)
			        endIdx++;
			 
			    content = content.substring(0, endIdx);
			return content + "...";
		}
		
		/*String content = story.content();
		BufferedReader bufReader = new BufferedReader(new StringReader(content));
		String line=null;
		
		String twoLinesContent = "";
		
		int count = 0;
		try {
			while( (line=bufReader.readLine()) != null && count < 2)
			{
				count++;
				twoLinesContent += line;
			}
		} catch (IOException e) {
			logger.error("error processing exerpt for " + content);
		}
		
		content = twoLinesContent;
		
		ProcessBBCode processBBCode = new ProcessBBCode();

		if(content!=null && content.length() <= EXCERPT_LENGTH)
			return processBBCode.prepareExcerpt(content);
		else{
			 int endIdx = EXCERPT_LENGTH;
			    while (endIdx < (EXCERPT_LENGTH + 10) && content.charAt(endIdx) != ' ' && endIdx < content.length()-1)
			        endIdx++;
			 
			    content = content.substring(0, endIdx);
			return processBBCode.prepareExcerpt(content) + "...";
		}*/
		
	}

	public int getViewCount() {
		return story.views();
	}
	
	public int getCommentCount() {
		return story.commentCount();
	}

	public int getVoteCount() {
		return story.point();
	}
	
	public boolean isVotedUpByCurrentReader() {
		if (reader == null) { //if user did not logged in
			return false;
		} else { //if user is already logged in
			return story.upVoters().contains(new Voter(reader));
		}
	}
	
	public boolean isVotedDownByCurrentReader() {
		if (reader == null) { //if user did not logged in
			return false;
		} else { //if user is already logged in
			return story.downVoters().contains(new Voter(reader));
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
	
	public AuthorViewAdapter getAuthor() {
		return new AuthorViewAdapter(author, reader);
	}
	
	public String getFeaturedImage() {
		if (story.featuredImage() != null)
			return ImageUtils.getFullImageURL(story.featuredImage().small());
		else 
			return StringUtils.EMPTY;
	}	
	
	public String getShortTime() {
		return DateUtils.getTimeAgo(story.submittedDate().getTime(), 
				i18nUtils.getMessage(I18NCode.TIME_JUST_NOW), i18nUtils.getMessage(I18NCode.TIME_A_MINUTE_AGO),
				i18nUtils.getMessage(I18NCode.TIME_MINUTES_AGO), i18nUtils.getMessage(I18NCode.TIME_AN_HOUR_AGO),
				i18nUtils.getMessage(I18NCode.TIME_HOURS_AGO), i18nUtils.getMessage(I18NCode.TIME_YESTERDAY), i18nUtils.getMessage(I18NCode.TIME_DAYS_AGO)); 
	}
	
	public String getFriendUrl() {
		return story.friendlyUrl();
	}
}
