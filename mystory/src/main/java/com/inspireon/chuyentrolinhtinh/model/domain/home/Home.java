package com.inspireon.chuyentrolinhtinh.model.domain.home;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.inspireon.chuyentrolinhtinh.model.domain.shared.Entity;
import com.inspireon.chuyentrolinhtinh.model.domain.story.Story;

@Document(collection="homes")
public class Home extends Entity<Home> {
	
    private String day;
    
    private List<Story> stories;
    
    // ---------------------------------- Constructors --------------------------------- //
	public Home(String day) {
		Validate.notEmpty(day);
		
		this.day = day;
		this.stories = new ArrayList<Story>();
	}
    
    // ---------------------------------- Business logic --------------------------------- //
	public String day() {
		return day;
	}

	public List<Story> stories() {
		return stories;
	}
	
	public void addStory(Story newStory) {
		this.stories.add(newStory);
	}
	
	// -------------------------------- Common method ---------------------------------//
	@Override
	public boolean sameIdentityAs(Home other) {
		return other != null && this.day.equals(other.day);
	}


	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final Home other = (Home) object;
		return sameIdentityAs(other);
	}

	@Override
	public int hashCode() {
		return id().hashCode();
	}

	@Override
	public String toString() {
		return "Home [month=" + day + "]";
	}
}
