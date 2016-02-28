package com.inspireon.mystory.application;

import java.util.List;

import com.inspireon.mystory.model.domain.story.Tag;

public interface CategoryService {

	List<Tag> showTags(String username);
}
