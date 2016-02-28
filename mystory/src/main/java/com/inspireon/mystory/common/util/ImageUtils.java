package com.inspireon.mystory.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.imgscalr.Scalr;

import com.inspireon.mystory.model.domain.image.Image;
import com.inspireon.mystory.model.domain.image.ImageGroup;
import com.inspireon.mystory.model.domain.image.Size;

public class ImageUtils {	
	private static final Logger logger = Logger.getLogger(ImageUtils.class);
	
    /**
     * Delete a file with path
     * @param path
     * @return
     */
    public static boolean delete(String path)
    {
    	logger.debug("Image delete: " + path);
        return delete(new File(addRootToPath(path)));
    }
    
    /**
     * Delete a file
     * @param file
     * @return
     */
    public static boolean delete(File file)
    {   	
        if ( file == null ) return false;
        if ( !file.exists() ) return true;
        if ( file.isDirectory() )
        {
            File[] subFiles = file.listFiles();
            for (File subFile : subFiles)
            {
                boolean success = delete(subFile);
                if (!success)
                {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return file.delete();
    }
    
    
    //****************** Utility Method for Image *********************************//   
    
  	public static File createImageFile(String filepath, String fileName, String type, BufferedImage destImg) throws IOException {
  		File newFile = new File(addRootToPath(filepath));
  		newFile.mkdirs();
  		logger.debug("Image resize: " + newFile.getName());
  		ImageIO.write(destImg, type.toUpperCase(), newFile);
  		return newFile;
  	}
  	
    public static String generateName() {
    	return MD5Hash.digest(String.valueOf(System.currentTimeMillis())).toString();
    }
	
    public static Size createAndResize(String sourcePath, String fileName, Size size) throws IOException {
    	return createAndResize(sourcePath, fileName, size, false);
    }
    
	public static Size createAndResize(String sourcePath, String fileName, Size size, boolean cropped) throws IOException {
		File source = new File(addRootToPath(sourcePath));
		BufferedImage sourceImg = ImageIO.read(source);
		BufferedImage destImg = null;
		
		if (cropped) sourceImg = cropImage(sourceImg);				
		
		Size newSize = resize(new Size(sourceImg.getWidth(), sourceImg.getHeight()), size);
		destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, newSize.width(), newSize.height()); 
		String formatName = getExtensionFile(source);
		
		File newFile = new File(source.getParent() + "/" + fileName + "." + formatName);
		
		newFile.mkdirs();
		logger.debug("Image resize: " + newFile.getName());
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		sourceImg.flush();
		destImg.flush();		
		
		return newSize;
	}
	
	private static BufferedImage cropImage(BufferedImage sourceImg) throws IOException {
		int newSize, x=0, y=0;
		if (sourceImg.getHeight() > sourceImg.getWidth()) {
			newSize = sourceImg.getWidth();
			y = (sourceImg.getHeight() - sourceImg.getWidth())/2;
		} else {
			newSize = sourceImg.getHeight();
			x = (sourceImg.getWidth() - sourceImg.getHeight())/2;
		}
				
		return Scalr.crop(sourceImg, x, y, newSize, newSize, Scalr.OP_ANTIALIAS); 
	}
	
	public static String getExtensionFile(File file) {
		if (file != null && file.isFile()) {
			return file.getName().substring(file.getName().lastIndexOf(".") + 1);
		}	
		
		return StringUtils.EMPTY;
	}
		
    /**
    * Resize 
    * @param from  		The original size.
    * @param standart	The maximum height/width that will to be.
    * @return           The size after resize.
    */
    public static Size resize(Size from, Size standart) {
        int newHeight = from.height();
        int newWidth = from.width();

        //Work out the resized width/height
        if (from.height() > standart.height() || from.width() > standart.width()) {
        	newHeight = standart.height();
            int wid = standart.width();
            float ratio = (float)from.width() / (float)from.height();
            newWidth = Math.round(newHeight * ratio);

            if (newWidth > wid) {
                //rezise again for the width this time
            	newHeight = Math.round(wid / ratio);
            	newWidth = wid;
            }
        }

        return new Size(newWidth, newHeight);
    }	
    
    public static String generateDirectory() {
		return DateUtils.getCurrentYear() + "/" 
				+ DateUtils.getCurrentMonth() + "/" 
				+ DateUtils.getCurrentDate();
    }

    public static String addRootToPath(String filePath) {
    	if (!StringUtils.isEmpty(filePath)) {
    		return ImageProperty.IMAGE_ROOT + "/" + filePath;
    	} else {
    		return StringUtils.EMPTY;
    	}
    }
    
    /**
     * 
     * @return
     */
    public static String toHtmlType(String imageType) {
    	if (StringUtils.isEmpty(imageType)) return null;
    	return "image/" + imageType;
    }

    public static String toImageType(String htmlType) {
    	if (StringUtils.isEmpty(htmlType)) return null;
    	return htmlType.replace("image/", StringUtils.EMPTY);
    }
    
    public static String getFullImageURL(String imageName) {
    	if (StringUtils.isEmpty(imageName)) return StringUtils.EMPTY;
    	return ImageProperty.IMAGE_HOST_SERVICE + "/" + imageName;
    }
    
	public static boolean isSizeValid(long size) {
		return size/1024 < ImageProperty.SIZE_MAXIMUM; 
	}    
	
	public static boolean availableImage(ImageGroup imageGroup, String imageName) {
		if (imageGroup != null) {
			Image image = imageGroup.findImage(imageName);
			if (image != null) {
				File file = new File(ImageUtils.addRootToPath(image.path()));
				return file.exists();
			}
		}
		
		return false;
	}
	
    public static void main(String a[]) throws IOException {
//    	delete(new File(ImageProperty.IMAGE_ROOT + "/" + "2013/11/3/1765ba1d38f61e00a07c766861cf8657.jpeg"));
//    	delete(new File(ImageProperty.IMAGE_ROOT + "/" + "2013/11/3/8adb447f7e9a417bb7b1656f2b88fa19.jpeg"));
//    	createAndResize("testa.jpg", "test1", ImageProperty.LARGE, true);
//    	createAndResize("testa.jpg", "test2", ImageProperty.MEDIUM, true);
//    	createAndResize("testa.jpg", "test3", ImageProperty.SMALL, true);   	
    }
}
