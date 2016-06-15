package com.inspireon.chuyentrolinhtinh.persistence.impl;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.inspireon.chuyentrolinhtinh.model.domain.comment.Comment;
import com.inspireon.chuyentrolinhtinh.model.domain.comment.CommentRepo;
import com.inspireon.chuyentrolinhtinh.persistence.BaseRepoImpl;
import com.inspireon.chuyentrolinhtinh.web.rest.story.reading.CommentSortType;

@Repository
public class CommentRepoImpl extends BaseRepoImpl<Comment, String> implements CommentRepo{

	@Override
	public List<Comment> findByStoryId(String storyId, CommentSortType sortType) {
		if (CommentSortType.TOP.equals(sortType)) {
			return getTemplate().find(new Query()
								.addCriteria(Criteria.where("storyId").is(storyId))
								.with(new Sort(Sort.Direction.DESC, "point"))
								.limit(5) , Comment.class);
		} else {
			return getTemplate().find(new Query()
								.addCriteria(Criteria.where("storyId").is(storyId))
								.with(new Sort(Sort.Direction.ASC, "submittedDate")), Comment.class);
		}
	}
	

	@Override
	public List<Comment> findNewCommentToNotify(String username, String storyId, Date cutOffTime) {
		
		Criteria criteria = Criteria.where("storyId").is(storyId).and("author").ne(username)
				.and("submittedDate").gte(cutOffTime);
		
		return getTemplate().find(new Query().addCriteria(criteria), Comment.class);
		
	}
	
}
