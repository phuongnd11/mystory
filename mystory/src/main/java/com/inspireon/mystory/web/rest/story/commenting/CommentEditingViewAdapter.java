package com.inspireon.mystory.web.rest.story.commenting;

import java.io.Serializable;
import java.util.Date;

import com.inspireon.mystory.common.util.ProcessBBCode;
import com.inspireon.mystory.model.domain.comment.Comment;

public class CommentEditingViewAdapter implements Serializable {
	private static final long serialVersionUID = -4547525241685881120L;

	private final Comment comment;

	public CommentEditingViewAdapter(Comment comment) {
		this.comment = comment;
	}
    
	public String getId() {
		return comment.id();
	}

	public String getContent() {
		ProcessBBCode processBBCode = new ProcessBBCode();
		
		if(comment.submittedDate().before(new Date(1388479849883l))){
			processBBCode.setAcceptHTML(true);
		}
		
		processBBCode.setAcceptBBCode(false);
		return processBBCode.preparePostText(comment.content());
	}
}
