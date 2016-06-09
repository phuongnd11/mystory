package com.inspireon.mystory.web.rest.story.telling;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.inspireon.mystory.common.util.ImageUtils;
import com.inspireon.mystory.common.util.ProcessBBCode;
import com.inspireon.mystory.model.domain.story.Story;

public class StoryEditingViewAdapter {

	private final Story story;
	private final Story originalStory;

	public StoryEditingViewAdapter(Story story, Story originalStory) {
		this.story = story;
		this.originalStory = originalStory;
	}
    
	public String getId() {
		return story.id();
	}

	public String getTitle() {
		return story.title();
	}

	public String getContent() {
		ProcessBBCode processBBCode = new ProcessBBCode();
		if(story.lastEditedTime().before(new Date(1388479849883l))){
			processBBCode.setAcceptHTML(true);
		}
		processBBCode.setAcceptBBCode(false);
		return processBBCode.preparePostText(story.content());
		//return HTMLUtils.sanitize(story.content());
	}

	public String getOriginalStoryId() {
		if (originalStory != null) {
			return originalStory.id();
		} else {
			return StringUtils.EMPTY;
		}
	}
	
	public String getOriginalStoryTitle() {
		if (originalStory != null) {
			return originalStory.title();
		} else {
			return StringUtils.EMPTY;
		}
	}

	public String getCategory() {
		return story.tag().name().toLowerCase();
	}

	public String getFeaturedImg() {
		if (story.featuredImage() != null)
			return ImageUtils.getFullImageURL(story.featuredImage().small());
		else 
			return StringUtils.EMPTY;
	}
	
	public String getFeaturedImgName() {
		if (story.featuredImage() != null)
			return story.featuredImage().name();
		else 
			return StringUtils.EMPTY;
	}
	
	public String getFriendUrl() {
		return story.friendlyUrl();
	}
}
