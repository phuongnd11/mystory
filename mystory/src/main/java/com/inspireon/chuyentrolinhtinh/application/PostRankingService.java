package com.inspireon.chuyentrolinhtinh.application;

import java.util.List;

import com.inspireon.chuyentrolinhtinh.model.domain.post.Post;
import com.inspireon.chuyentrolinhtinh.web.rest.home.viewing.HomeViewCondition;

public interface PostRankingService {
	
	List<Post> getTopHot(HomeViewCondition condition);
	
	List<Post> getTopControversial(HomeViewCondition condition);
}
