package com.inspireon.chuyentrolinhtinh.application.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inspireon.chuyentrolinhtinh.application.ImageGroupService;
import com.inspireon.chuyentrolinhtinh.common.util.ImageProperty;
import com.inspireon.chuyentrolinhtinh.common.util.ImageUtils;
import com.inspireon.chuyentrolinhtinh.exception.ImageWrittenFailureException;
import com.inspireon.chuyentrolinhtinh.model.domain.image.Image;
import com.inspireon.chuyentrolinhtinh.model.domain.image.ImageGroup;
import com.inspireon.chuyentrolinhtinh.model.domain.image.ImageGroupRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.image.Size;
import com.inspireon.chuyentrolinhtinh.web.rest.image.FileMeta;

@Service
public class ImageGroupServiceImpl implements ImageGroupService{

	private static final Logger logger = Logger.getLogger(ImageGroupServiceImpl.class);
	
	@Autowired
	private ImageGroupRepo imageGroupRepo;

	@Override
	public ImageGroup uploadAvatarImage(FileMeta file) throws ImageWrittenFailureException{
		
		String folderName = ImageUtils.generateName();
		String folderPath = ImageUtils.generateDirectory() + "/" + folderName;
		String originalPath = folderPath + "/" + ImageProperty.IMAGE_NAME_ORIGINAL + "." + file.getFileType();
		String smallPath = folderPath + "/" + ImageProperty.IMAGE_NAME_SMALL + "." + file.getFileType();
		String largePath = folderPath + "/" + ImageProperty.IMAGE_NAME_LARGE + "." + file.getFileType();
		
		try {
			//Create Physical File
			BufferedImage phyImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));			
			ImageUtils.createImageFile(originalPath, ImageProperty.IMAGE_NAME_ORIGINAL, file.getFileType(), phyImage); //Original
			Size smallSize = ImageUtils.createAndResize(originalPath, ImageProperty.IMAGE_NAME_SMALL, ImageProperty.AVATAR_SMALL, true); //small
			Size largeSize = ImageUtils.createAndResize(originalPath, ImageProperty.IMAGE_NAME_LARGE, ImageProperty.AVATAR_LARGE, true); // large			
			//Create Logical File			
			Image original = new Image(ImageProperty.IMAGE_NAME_ORIGINAL, originalPath, new Size(phyImage.getWidth(), phyImage.getHeight()));
			Image small = new Image(ImageProperty.IMAGE_NAME_SMALL, smallPath, smallSize);
			Image large = new Image(ImageProperty.IMAGE_NAME_LARGE, largePath, largeSize);
			
			ImageGroup imageGroup = new ImageGroup(folderName, folderPath, file.getFileType(), original, null, small, large);
			imageGroupRepo.store(imageGroup);
			
			return imageGroup;
		} catch (Exception e) {
			logger.error(e);
			throw new ImageWrittenFailureException(folderName);
		}		
	}
	
	@Override
	public ImageGroup uploadFeaturedImage(FileMeta file) throws ImageWrittenFailureException{
		
		String folderName = ImageUtils.generateName();
		String folderPath = ImageUtils.generateDirectory() + "/" + folderName;
		String originalPath = folderPath + "/" + ImageProperty.IMAGE_NAME_ORIGINAL + "." + file.getFileType();
		String smallPath = folderPath + "/" + ImageProperty.IMAGE_NAME_SMALL + "." + file.getFileType();
		String largePath = folderPath + "/" + ImageProperty.IMAGE_NAME_LARGE + "." + file.getFileType();
		
		try {
			//Create Physical File
			BufferedImage phyImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));			
			ImageUtils.createImageFile(originalPath, ImageProperty.IMAGE_NAME_ORIGINAL, file.getFileType(), phyImage); //Original
			Size smallSize = ImageUtils.createAndResize(originalPath, ImageProperty.IMAGE_NAME_SMALL, ImageProperty.FEATURED_SMALL); //small
			Size largeSize = ImageUtils.createAndResize(originalPath, ImageProperty.IMAGE_NAME_LARGE, ImageProperty.FEATURED_LARGE); // large			
			//Create Logical File			
			Image original = new Image(ImageProperty.IMAGE_NAME_ORIGINAL, originalPath, new Size(phyImage.getWidth(), phyImage.getHeight()));
			Image small = new Image(ImageProperty.IMAGE_NAME_SMALL, smallPath, smallSize);
			Image large = new Image(ImageProperty.IMAGE_NAME_LARGE, largePath, largeSize);
			
			ImageGroup imageGroup = new ImageGroup(folderName, folderPath, file.getFileType(), original, null, small, large);
			imageGroupRepo.store(imageGroup);
			
			return imageGroup;
		} catch (Exception e) {
			logger.error(e);
			throw new ImageWrittenFailureException(folderName);
		}		
	}

	@Override
	public ImageGroup uploadContentImage(FileMeta file) throws ImageWrittenFailureException{
		
		String folderName = ImageUtils.generateName();
		String folderPath = ImageUtils.generateDirectory() + "/" + folderName;
		String originalPath = folderPath + "/" + ImageProperty.IMAGE_NAME_ORIGINAL + "." + file.getFileType();
		String fullPath = folderPath + "/" + ImageProperty.IMAGE_NAME_FULL + "." + file.getFileType();
		
		try {
			//Create Physical File
			BufferedImage phyImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));			
			ImageUtils.createImageFile(originalPath, ImageProperty.IMAGE_NAME_ORIGINAL, file.getFileType(), phyImage); //Original
			Size fullSize = ImageUtils.createAndResize(originalPath, ImageProperty.IMAGE_NAME_FULL, ImageProperty.FULL); //small
			
			//Create Logical File			
			Image original = new Image(ImageProperty.IMAGE_NAME_ORIGINAL, originalPath, new Size(phyImage.getWidth(), phyImage.getHeight()));
			Image full = new Image(ImageProperty.IMAGE_NAME_FULL, fullPath, fullSize);
			
			ImageGroup imageGroup = new ImageGroup(folderName, folderPath, file.getFileType(), original, full, null, null);
			imageGroupRepo.store(imageGroup);
			
			return imageGroup;
		} catch (Exception e) {
			logger.error(e);
			throw new ImageWrittenFailureException(folderName);
		}		
	}
	
	@Override
	public void deleteImageGroup(ImageGroup imageGroup){
		if (imageGroup != null) {
			boolean sucess = ImageUtils.delete(imageGroup.path());
			
			if (sucess) {
				imageGroupRepo.remove(imageGroup);
			} else {
				logger.error("Can't delete image group: " + imageGroup.name());
			}
		}
	}

	@Override
	public ImageGroup findImageGroup(String groupName) {
		return imageGroupRepo.findByFileName(groupName);
	}

}
