package com.inspireon.chuyentrolinhtinh.common.searching;

import java.util.List;

import org.springframework.data.domain.PageImpl;

public class MyPostPageImpl<T> extends PageImpl<T>{

	private static final long serialVersionUID = -3125646909995489498L;

	public MyPostPageImpl(List<T> content) {
		super(content);
	}
}
