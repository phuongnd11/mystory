package com.inspireon.mystory.web.rest.home.viewing;

import java.io.Serializable;
import java.util.List;

public class HomeViewAdapter implements Serializable {

	private static final long serialVersionUID = 6590509121570148176L;

	private int page;
	
	private List<HomeStoryViewAdapter> stories;

	public HomeViewAdapter(int page, List<HomeStoryViewAdapter> stories) {
		this.page = page;
		this.stories = stories;
	}

	public int getPage() {
		return page;
	}

	public List<HomeStoryViewAdapter> getStories() {
		return stories;
	}
}
