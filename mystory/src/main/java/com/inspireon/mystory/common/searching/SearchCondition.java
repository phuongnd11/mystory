package com.inspireon.mystory.common.searching;

import java.util.Map;

public class SearchCondition {

	private static final int DEFAULT_PAGE_SIZE = 20;
	
	/*------------------------ Properties -------------------------*/
	// Paging condition
	private int page;
	private int pageSize = DEFAULT_PAGE_SIZE;
	
	// Sort condition
	private String sortColumn;
	private String sortOrder;
	
	// User's input data
	private Map<String, String> input;
	
	/*------------------------ Getters/Setters ---------------------*/
	public int getStartIndex() {
		return (page * pageSize - pageSize);
	}

	public int getEndIndex() {
		return (page * pageSize);
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Map<String, String> getInput() {
		return input; 
	}

	public void setInput(Map<String, String> input) {
		this.input = input;
	}
}