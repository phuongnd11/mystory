package com.inspireon.mystory.model.domain.wall;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.inspireon.mystory.model.domain.shared.Entity;
import com.inspireon.mystory.model.domain.story.Story;

@Document(collection="walls")
public class Wall extends Entity<Wall>{
    
    private String username;
    
    private String month;
    
    private List<Story> stories;
    
    // ---------------------------------- Constructors --------------------------------- //
	public Wall(String username, String month) {
		Validate.notEmpty(username);
		Validate.notEmpty(month);
		
		this.username = username;
		this.month = month;
		this.stories = new ArrayList<Story>();
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
	
	// -------------------------------- Common method ---------------------------------//
	@Override
	public boolean sameIdentityAs(Wall other) {
		return other != null && this.month.equals(other.month) && this.username.equals(other.username);
	}


	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final Wall other = (Wall) object;
		return sameIdentityAs(other);
	}

	/**
	 * @return Hash code of username
	 */
	@Override
	public int hashCode() {
		return id().hashCode();
	}

	@Override
	public String toString() {
		return "Wall [username=" + username + ", month=" + month + "]";
	}
	
}

