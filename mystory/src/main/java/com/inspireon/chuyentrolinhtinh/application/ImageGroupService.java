package com.inspireon.chuyentrolinhtinh.application;

import com.inspireon.chuyentrolinhtinh.exception.ImageWrittenFailureException;
import com.inspireon.chuyentrolinhtinh.model.domain.image.ImageGroup;
import com.inspireon.chuyentrolinhtinh.web.rest.image.FileMeta;

public interface ImageGroupService {	
	
	ImageGroup uploadAvatarImage(FileMeta file) throws ImageWrittenFailureException;
	
	ImageGroup uploadFeaturedImage(FileMeta file) throws ImageWrittenFailureException;
	
	ImageGroup uploadContentImage(FileMeta file) throws ImageWrittenFailureException;
	
	void deleteImageGroup(ImageGroup imageGroup);	
	
	ImageGroup findImageGroup(String groupName);
}
