package com.inspireon.chuyentrolinhtinh.persistence;


import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inspireon.chuyentrolinhtinh.common.util.ImageUtils;
import com.inspireon.chuyentrolinhtinh.model.domain.image.ImageGroup;
import com.inspireon.chuyentrolinhtinh.model.domain.image.ImageGroupRepo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-context.xml"})
public class ImageGroupRepoTest {

	@Autowired
	ImageGroupRepo imageGroupRepo;
	
	@Test 
	@Ignore
	public void generareDefaultImage() {
		ImageGroup defaultAvatar = ImageGroup.defaultAvatarImageGroup();
		ImageGroup defaultFeatured = ImageGroup.defaultFeturedImageGroup();
		if (imageGroupRepo.findByFileName(defaultAvatar.name()) == null) {
			imageGroupRepo.store(defaultAvatar);
		}
		if (imageGroupRepo.findByFileName(defaultFeatured.name()) == null) {
			imageGroupRepo.store(defaultFeatured);
		}		
	}
	
	@Test
	@Ignore
	public void findByName(){
		
		String fileName = ImageUtils.generateName();
		String filePath = ImageUtils.generateDirectory() + "/" + fileName + ".jpeg";
		
		// Prepare data
		ImageGroup imageGroup = new ImageGroup(fileName, filePath, "jpeg", null, null, null, null);  
		imageGroupRepo.store(imageGroup);		
			
		// Verify insert image successfully
		ImageGroup newImage = imageGroupRepo.findByFileName(fileName);
		Assert.assertNotNull(newImage);
		
		Assert.assertEquals(newImage.name(), fileName);
		Assert.assertEquals(newImage.path(), filePath);
		Assert.assertEquals(newImage.type(), "jpeg");		
		Assert.assertNotNull(newImage.createdDate());
		
	    // Roll back data
		imageGroupRepo.remove(imageGroup);   
	}		
}
