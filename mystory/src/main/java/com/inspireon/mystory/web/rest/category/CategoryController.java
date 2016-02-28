package com.inspireon.mystory.web.rest.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inspireon.mystory.model.domain.story.Tag;
import com.inspireon.mystory.web.rest.base.AbstractBaseController;

@Controller
public class CategoryController extends AbstractBaseController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/categories")
	public @ResponseBody List<CategoryViewAdapter> showCategories() {
		
		List<CategoryViewAdapter> allCategories = new ArrayList<CategoryViewAdapter>();
		
		for (Tag tag : Tag.values()) {
			if (!tag.equals(Tag.ALL)) {
				allCategories.add(new CategoryViewAdapter(tag, null));
			}
		}

		return allCategories;
	}
}
