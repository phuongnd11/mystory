package com.inspireon.mystory.model.domain.image;

import com.inspireon.mystory.persistence.BaseRepo;

public interface ImageGroupRepo extends BaseRepo<ImageGroup, String> {
	
	ImageGroup findByFileName(String fileName);	
}
