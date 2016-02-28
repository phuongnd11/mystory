package com.inspireon.mystory.application;

import com.inspireon.mystory.model.domain.comment.Comment;

public interface CommentingService {
	
	String addNewComment(String content, String commenter, String storyId) throws Exception;
	
	void editComment(Comment comment, String editor, String newContent) throws Exception;
}
