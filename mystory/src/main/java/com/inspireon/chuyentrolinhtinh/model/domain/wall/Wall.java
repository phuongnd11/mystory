package com.inspireon.chuyentrolinhtinh.model.domain.wall;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.inspireon.chuyentrolinhtinh.model.domain.post.Post;
import com.inspireon.chuyentrolinhtinh.model.domain.shared.Entity;

@Document(collection="walls")
public class Wall extends Entity<Wall>{
    
    private String username;
    
    private String month;
    
    private List<Post> stories;
    
    // ---------------------------------- Constructors --------------------------------- //
	public Wall(String username, String month) {
		Validate.notEmpty(username);
		Validate.notEmpty(month);
		
		this.username = username;
		this.month = month;
		this.stories = new ArrayList<Post>();
	}
    
    // ---------------------------------- Business logic --------------------------------- //
	public String username() {
		return username;
	}

	public String month() {
		return month;
	}

	public List<Post> stories() {
		return stories;
	}
	
	public void addStory(Post newStory) {
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

