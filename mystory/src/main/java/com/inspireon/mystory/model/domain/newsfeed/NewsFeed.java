package com.inspireon.mystory.model.domain.newsfeed;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.inspireon.mystory.model.domain.shared.Entity;
import com.inspireon.mystory.model.domain.story.Story;

@Document(collection="news_feeds")
public class NewsFeed extends Entity<NewsFeed> {
	
    private String username;
    
    private String month;
    
    private List<Story> stories;
    
    // ---------------------------------- Constructors --------------------------------- //
	public NewsFeed(String username, String month) {
		Validate.notEmpty(username);
		Validate.notEmpty(month);
		
		this.username = username;
		this.month = month;
		stories = new ArrayList<Story>();
	}
    
    // ---------------------------------- Business logic --------------------------------- //
	public String username() {
		return username;
	}

	public String month() {
		return month;
	}

	public List<Story> stories() {
		return stories;
	}
	
	public void addStory(Story newStory) {
		this.stories.add(newStory);
	}
	
	public void editStory(Story updatedStory) {
		int index = -1;
		
		for (Story story : this.stories) {
			if (story.equals(updatedStory)) {
				index = this.stories.indexOf(story);
				break;
			}
		}
		
		if (index > -1) {
			this.stories.set(index, updatedStory);
		} else {
			//throws exception
		}
	}
	
	
	// -------------------------------- Common method ---------------------------------//
	@Override
	public boolean sameIdentityAs(NewsFeed other) {
		return other != null && this.month.equals(other.month) && this.username.equals(other.username);
	}


	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final NewsFeed other = (NewsFeed) object;
		return sameIdentityAs(other);
	}

	@Override
	public int hashCode() {
		return id().hashCode();
	}

	@Override
	public String toString() {
		return "NewsFeed [username=" + username + ", month=" + month + "]";
	}
	
}
