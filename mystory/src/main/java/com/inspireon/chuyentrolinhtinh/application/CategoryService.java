package com.inspireon.chuyentrolinhtinh.application;

import java.util.List;

import com.inspireon.chuyentrolinhtinh.model.domain.post.Tag;

public interface CategoryService {

	List<Tag> showTags(String username);
}
