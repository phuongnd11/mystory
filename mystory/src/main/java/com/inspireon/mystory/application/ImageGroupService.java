package com.inspireon.mystory.application;

import com.inspireon.mystory.exception.ImageWrittenFailureException;
import com.inspireon.mystory.model.domain.image.ImageGroup;
import com.inspireon.mystory.web.rest.image.FileMeta;

public interface ImageGroupService {	
	
	ImageGroup uploadAvatarImage(FileMeta file) throws ImageWrittenFailureException;
	
	ImageGroup uploadFeaturedImage(FileMeta file) throws ImageWrittenFailureException;
	
	ImageGroup uploadContentImage(FileMeta file) throws ImageWrittenFailureException;
	
	void deleteImageGroup(ImageGroup imageGroup);	
	
	ImageGroup findImageGroup(String groupName);
}
