package com.inspireon.chuyentrolinhtinh.web.rest.wall.viewing;

import java.util.List;

import com.inspireon.chuyentrolinhtinh.web.rest.home.viewing.HomePostViewAdapter;
import com.inspireon.chuyentrolinhtinh.web.rest.home.viewing.HomeViewAdapter;

public class WallViewAdapter extends HomeViewAdapter {

	private static final long serialVersionUID = 1944111210488516569L;
	
	private String author;
	
	public WallViewAdapter(int page, List<HomePostViewAdapter> stories, String author) {
		super(page, stories);
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}
}
