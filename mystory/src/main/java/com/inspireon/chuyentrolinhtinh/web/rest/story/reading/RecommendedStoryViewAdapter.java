package com.inspireon.chuyentrolinhtinh.web.rest.story.reading;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.inspireon.chuyentrolinhtinh.model.domain.story.Story;

public class RecommendedStoryViewAdapter implements Serializable {
	
	private static final long serialVersionUID = 5353124625210522486L;

	public static Logger logger = Logger.getLogger(RecommendedStoryViewAdapter.class);
	
	private final Story story;

	public RecommendedStoryViewAdapter(Story story) {
		this.story = story;
	}
	
	public String getUrl() {
		return "/story/" + story.friendlyUrl();
	}
	
	public String getShortTitle() {
		if (story.title().length() >= 55) {
			return com.inspireon.chuyentrolinhtinh.common.util.StringUtils.substring(story.title(), 52) + "...";
		}
		
		return story.title();
	}
}
