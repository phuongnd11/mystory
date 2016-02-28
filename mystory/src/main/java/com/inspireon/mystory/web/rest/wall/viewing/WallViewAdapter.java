package com.inspireon.mystory.web.rest.wall.viewing;

import java.util.List;

import com.inspireon.mystory.web.rest.home.viewing.HomeStoryViewAdapter;
import com.inspireon.mystory.web.rest.home.viewing.HomeViewAdapter;

public class WallViewAdapter extends HomeViewAdapter {

	private static final long serialVersionUID = 1944111210488516569L;
	
	private String author;
	
	public WallViewAdapter(int page, List<HomeStoryViewAdapter> stories, String author) {
		super(page, stories);
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}
}
