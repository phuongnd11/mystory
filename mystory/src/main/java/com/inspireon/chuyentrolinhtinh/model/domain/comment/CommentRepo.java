package com.inspireon.chuyentrolinhtinh.model.domain.comment;

import java.util.Date;
import java.util.List;

import com.inspireon.chuyentrolinhtinh.persistence.BaseRepo;
import com.inspireon.chuyentrolinhtinh.web.rest.post.reading.CommentSortType;

public interface CommentRepo extends BaseRepo<Comment, String> {
	
	List<Comment> findByStoryId(String storyId, CommentSortType sortType);
	
	List<Comment> findNewCommentToNotify(String username, String storyId, Date cutOffTime);
}
