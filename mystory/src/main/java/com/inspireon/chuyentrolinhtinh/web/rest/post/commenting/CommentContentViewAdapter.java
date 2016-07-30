package com.inspireon.chuyentrolinhtinh.web.rest.post.commenting;

import java.io.Serializable;

import com.inspireon.chuyentrolinhtinh.common.util.ProcessBBCode;

public class CommentContentViewAdapter implements Serializable {

	private static final long serialVersionUID = 3021040247529410562L;
	
	private String content;

	public CommentContentViewAdapter(String content) {
		this.content = content;
	}

	public String getContent() {
		ProcessBBCode processBBCode = new ProcessBBCode();
		return processBBCode.preparePostText(content);
	}
}
