package com.inspireon.chuyentrolinhtinh.model.domain.image;

import com.inspireon.chuyentrolinhtinh.persistence.BaseRepo;

public interface ImageGroupRepo extends BaseRepo<ImageGroup, String> {
	
	ImageGroup findByFileName(String fileName);	
}
