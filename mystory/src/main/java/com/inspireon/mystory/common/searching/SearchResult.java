package com.inspireon.mystory.common.searching;

import java.util.Collection;

/**
 * Search result from domain.
 * 
 * @param <R>
 * 
 */
public class SearchResult<R> {

	private int page;
	private int pageSize;

	private Collection<R> rows;
	
	/**
	 * SearchResult constructor
	 * 
	 * @param totalRows
	 * @param rows
	 */
	public SearchResult(Collection<R> rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Rows has data returned from storage.
	 * 
	 * @return rows
	 */
	public Collection<R> getRows() {
		return rows;
	}

	/**
	 * Set rows
	 * 
	 * @param rows
	 */
	public void setRows(Collection<R> rows) {
		this.rows = rows;	
	}
}