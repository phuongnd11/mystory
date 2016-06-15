package com.inspireon.chuyentrolinhtinh.web.rest.home.viewing;

import java.util.Date;

import org.springframework.data.domain.PageRequest;

import com.inspireon.chuyentrolinhtinh.common.util.DateUtils;
import com.inspireon.chuyentrolinhtinh.model.domain.story.Tag;
import com.inspireon.chuyentrolinhtinh.web.rest.home.viewing.SortTypeAdapter.Type;

public class HomeViewCondition extends PageRequest{
	
	private static final long serialVersionUID = -6109317372660010678L;

	private static final int DEFAULT_PAGE_SIZE = 20;
	
	public static final int FIRST_PAGE = 1;
	
	public static final String DEFAULT_TYPE = "hot";
	
	public static final String NO_TAG = "all";
	
	public static final int NUMBER_OF_DAYS = 100;// Three months
	
	public static final int THREE_YEARS = 1100;// Three years
	
	private Type type;
	
	private Tag tag;
	
	private Date endDate;
	
	private Date startDate;
	
	public HomeViewCondition(String type, String category, int page) {
		super(page - 1, DEFAULT_PAGE_SIZE);
		this.type = Type.valueOf(type.toUpperCase());
		this.tag = Tag.valueOf(category.toUpperCase());
		this.endDate = new Date(); //current date
		this.startDate = DateUtils.substractDate(endDate, THREE_YEARS);
	}	
	
	public HomeViewCondition(int page) {
		super(page - 1, DEFAULT_PAGE_SIZE);
	}
	
	public void moveDateBackward() {
		this.endDate = DateUtils.substractDate(endDate, NUMBER_OF_DAYS);
		this.startDate = DateUtils.substractDate(startDate, NUMBER_OF_DAYS);
	}
	
	public Type getType() {
		return type;
	}

	public Tag getTag() {
		return tag;
	}
	
	public int getPageNumber() {
		return super.getPageNumber() + 1;
	}
	
	public int getStartIndex() {
		return super.getOffset();
	}
	
	public int getEndIndex() {
		return super.getOffset() + super.getPageSize();
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}
}
