package com.inspireon.mystory.model.domain.comment;

import java.util.Date;
import java.util.List;

import com.inspireon.mystory.persistence.BaseRepo;
import com.inspireon.mystory.web.rest.story.reading.CommentSortType;

public interface CommentRepo extends BaseRepo<Comment, String> {
	
	List<Comment> findByStoryId(String storyId, CommentSortType sortType);
	
	List<Comment> findNewCommentToNotify(String username, String storyId, Date cutOffTime);
}
