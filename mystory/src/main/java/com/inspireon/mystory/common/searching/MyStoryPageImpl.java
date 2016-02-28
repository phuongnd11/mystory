package com.inspireon.mystory.common.searching;

import java.util.List;

import org.springframework.data.domain.PageImpl;

public class MyStoryPageImpl<T> extends PageImpl<T>{

	private static final long serialVersionUID = -3125646909995489498L;

	public MyStoryPageImpl(List<T> content) {
		super(content);
	}
}
