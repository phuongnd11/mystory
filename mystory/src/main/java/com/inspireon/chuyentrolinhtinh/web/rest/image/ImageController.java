package com.inspireon.chuyentrolinhtinh.web.rest.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.inspireon.chuyentrolinhtinh.application.ImageGroupService;
import com.inspireon.chuyentrolinhtinh.common.util.ImageProperty;
import com.inspireon.chuyentrolinhtinh.common.util.ImageUtils;
import com.inspireon.chuyentrolinhtinh.exception.ImageWrittenFailureException;
import com.inspireon.chuyentrolinhtinh.model.domain.image.Image;
import com.inspireon.chuyentrolinhtinh.model.domain.image.ImageGroup;
import com.inspireon.chuyentrolinhtinh.web.rest.base.AbstractBaseController;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.i18n.I18NCode;

/**
 * 
 * @author Cuong
 *
 */
@Controller
@RequestMapping("/image")
public class ImageController extends AbstractBaseController{
	
	private static final Logger logger = Logger.getLogger(ImageController.class);
		
	@Autowired
	private ImageGroupService imageGroupService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{imageId}")
	public void showImage(HttpServletResponse response, @PathVariable String imageId){
		try {
			String[] strToks = imageId.split(ImageProperty.IMAGE_GROUP_NAME_SEPARATOR);
			if (strToks.length == 2) {
				final ServletOutputStream out = response.getOutputStream();
				ImageGroup imageGroup = imageGroupService.findImageGroup(strToks[0]);
				if (!ImageUtils.availableImage(imageGroup, strToks[1])) {
					imageGroup = ImageGroup.defaultFeturedImageGroup();
				}
				
				Image image = imageGroup.findImage(strToks[1]);
				if (image != null) {
					response.setContentType(ImageUtils.toHtmlType(imageGroup.type()));
					//response.setHeader("Content-disposition", "attachment; filename=\""+photo.getName()+"\"");
					
					FileInputStream in = new FileInputStream(new File(ImageUtils.addRootToPath(image.path())));
					
					IOUtils.copy(in, out);
			 			 	
				 	in.close();
				 	out.close();
				}
			} 
		} catch (IOException ie) {
			logger.error(ie.getMessage());
		}
	}	

	@RequestMapping(method = RequestMethod.POST, value="/upload/content")
	public @ResponseBody Response uploadContentImage(MultipartHttpServletRequest request) {
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;

		if (itr.hasNext()) {
			try {
				mpf = request.getFile(itr.next());			
				mpf.getOriginalFilename();
				if (mpf != null && ImageUtils.isSizeValid(mpf.getSize())) {	
					if (mpf.getContentType().contains("image/")) {
						FileMeta fileMeta = new FileMeta(ImageUtils.toImageType(mpf.getContentType()),
													 mpf.getBytes());
						ImageGroup imageGroup = imageGroupService.uploadContentImage(fileMeta);
						return success(new ImageViewAdapter(imageGroup));
					} else {
						return failure(I18NCode.MESSAGE_IMAGE_FILETYPE_WRONG);
					}
				} else {
					return failure(I18NCode.MESSAGE_IMAGE_FILESIZE_OVER);
				}
			} catch (ImageWrittenFailureException e) {
				logger.error(e.getMessage());
			} catch (IOException ie) {
				logger.error(ie.getMessage());
			}			
		}
		return failure(I18NCode.MESSAGE_IMAGE_UPLOAD_FAIL);
	}	
	
	@RequestMapping(method = RequestMethod.POST, value="/upload/avatar")
	public @ResponseBody Response uploadAvatarImage(MultipartHttpServletRequest request) {
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;

		if (itr.hasNext()) {
			try {
				mpf = request.getFile(itr.next());			
				mpf.getOriginalFilename();
				if (mpf != null && ImageUtils.isSizeValid(mpf.getSize())) {	
					if (mpf.getContentType().contains("image/")) {
						FileMeta fileMeta = new FileMeta(ImageUtils.toImageType(mpf.getContentType()),
													 mpf.getBytes());
						ImageGroup imageGroup = imageGroupService.uploadAvatarImage(fileMeta);
						return success(new ImageViewAdapter(imageGroup));
					} else {
						return failure(I18NCode.MESSAGE_IMAGE_FILETYPE_WRONG);
					}
				} else {
					return failure(I18NCode.MESSAGE_IMAGE_FILESIZE_OVER);
				}
			} catch (ImageWrittenFailureException e) {
				logger.error(e.getMessage(), e);
			} catch (IOException ie) {
				logger.error(ie.getMessage(), ie);
			}			
		}
		return failure(I18NCode.MESSAGE_IMAGE_UPLOAD_FAIL);
	}	
	
	@RequestMapping(method = RequestMethod.POST, value="/upload/featured")
	public @ResponseBody Response uploadFeaturedImage(MultipartHttpServletRequest request) {
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;

		if (itr.hasNext()) {
			try {
				mpf = request.getFile(itr.next());			
				mpf.getOriginalFilename();
				if (mpf != null && ImageUtils.isSizeValid(mpf.getSize())) {	
					if (mpf.getContentType().contains("image/")) {
						FileMeta fileMeta = new FileMeta(ImageUtils.toImageType(mpf.getContentType()),
													 mpf.getBytes());
						ImageGroup imageGroup = imageGroupService.uploadFeaturedImage(fileMeta);
						return success(new ImageViewAdapter(imageGroup));
					} else {
						return failure(I18NCode.MESSAGE_IMAGE_FILETYPE_WRONG);
					}
				} else {
					return failure(I18NCode.MESSAGE_IMAGE_FILESIZE_OVER);
				}
			} catch (ImageWrittenFailureException e) {
				logger.error(e.getMessage());
			} catch (IOException ie) {
				logger.error(ie.getMessage());
			}			
		}
		return failure(I18NCode.MESSAGE_IMAGE_UPLOAD_FAIL);
	}	
}
