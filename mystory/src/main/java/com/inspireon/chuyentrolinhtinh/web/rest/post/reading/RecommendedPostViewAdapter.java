package com.inspireon.chuyentrolinhtinh.web.rest.post.reading;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.inspireon.chuyentrolinhtinh.model.domain.post.Post;

public class RecommendedPostViewAdapter implements Serializable {
	
	private static final long serialVersionUID = 5353124625210522486L;

	public static Logger logger = Logger.getLogger(RecommendedPostViewAdapter.class);
	
	private final Post story;

	public RecommendedPostViewAdapter(Post story) {
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
