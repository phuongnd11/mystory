package com.inspireon.mystory.model.domain.image;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import com.inspireon.mystory.common.util.ImageProperty;
import com.inspireon.mystory.model.domain.shared.Entity;
import com.inspireon.mystory.model.domain.user.MailBox;

@Document(collection="imageGroups")
public class ImageGroup extends Entity<ImageGroup>{
	
	// ------------------------------ Properties ------------------------------------- //
	private String name;
	private String path;
	private String type;
	private Image original;
	private Image full;
	private Image small;
	private Image large;
	private Date createdDate;	

	// ------------------------------ Constructors ----------------------------------- //
	public ImageGroup(String name, String path, String type, Image original, 
						Image full, Image small, Image large) {
		this.name = name;
		this.path = path;
		this.type = type;
		this.original = original;
		this.small = small;
		this.full = full;
		this.large = large;
		this.createdDate = new Date();
	}
	
	public static ImageGroup defaultAvatarImageGroup() {
		Image original = new Image(ImageProperty.IMAGE_NAME_ORIGINAL, 
				ImageProperty.DEFAULT_IMAGE_AVATAR_PATH + "/" + ImageProperty.IMAGE_NAME_ORIGINAL + ".jpeg", ImageProperty.FULL);
		Image full = new Image(ImageProperty.IMAGE_NAME_FULL, 
				ImageProperty.DEFAULT_IMAGE_AVATAR_PATH + "/" + ImageProperty.IMAGE_NAME_FULL + ".jpeg", ImageProperty.FULL);
		Image small = new Image(ImageProperty.IMAGE_NAME_SMALL, 
				ImageProperty.DEFAULT_IMAGE_AVATAR_PATH + "/" + ImageProperty.IMAGE_NAME_SMALL + ".jpeg", ImageProperty.AVATAR_SMALL);
		Image large = new Image(ImageProperty.IMAGE_NAME_LARGE, 
				ImageProperty.DEFAULT_IMAGE_AVATAR_PATH + "/" + ImageProperty.IMAGE_NAME_LARGE + ".jpeg", ImageProperty.AVATAR_LARGE);		
		return new ImageGroup("default", ImageProperty.DEFAULT_IMAGE_AVATAR_PATH, "jpeg", original, full, small, large);
	}
	
	public static ImageGroup defaultFeturedImageGroup() {
		Image original = new Image(ImageProperty.IMAGE_NAME_ORIGINAL, 
				ImageProperty.DEFAULT_IMAGE_FEATURED_PATH + "/" + ImageProperty.IMAGE_NAME_ORIGINAL + ".jpeg", ImageProperty.FULL);
		Image full = new Image(ImageProperty.IMAGE_NAME_FULL, 
				ImageProperty.DEFAULT_IMAGE_FEATURED_PATH + "/" + ImageProperty.IMAGE_NAME_FULL + ".jpeg", ImageProperty.FULL);
		Image small = new Image(ImageProperty.IMAGE_NAME_SMALL, 
				ImageProperty.DEFAULT_IMAGE_FEATURED_PATH + "/" + ImageProperty.IMAGE_NAME_SMALL + ".jpeg", ImageProperty.FEATURED_SMALL);
		Image large = new Image(ImageProperty.IMAGE_NAME_LARGE, 
				ImageProperty.DEFAULT_IMAGE_FEATURED_PATH + "/" + ImageProperty.IMAGE_NAME_LARGE + ".jpeg", ImageProperty.FEATURED_LARGE);		
		return new ImageGroup("defaultFeatured", ImageProperty.DEFAULT_IMAGE_FEATURED_PATH, "jpeg", original, full, small, large);
	}
	
	// ------------------------------ Business logic --------------------------------- //	
	public String name() {
		return name;
	}
	
	public String path() {
		return path;
	}
	
	public String type() {
		return type;
	}	
	
	public String original() {
		if (original == null) return StringUtils.EMPTY;
		return name + ImageProperty.IMAGE_GROUP_NAME_SEPARATOR + original.name();
	}
	
	public String full() {
		if (full == null) return StringUtils.EMPTY;
		return name + ImageProperty.IMAGE_GROUP_NAME_SEPARATOR + full.name();
	}
	
	public String small() {
		if (small == null) return StringUtils.EMPTY;
		return name + ImageProperty.IMAGE_GROUP_NAME_SEPARATOR + small.name();
	}
	
	public String large() {
		if (large == null) return StringUtils.EMPTY;
		return name + ImageProperty.IMAGE_GROUP_NAME_SEPARATOR + large.name();
	}
	
	public Image findImage(String imageName) {
		if (original != null && original.name().equalsIgnoreCase(imageName)) return original;
		if (full != null && full.name().equalsIgnoreCase(imageName)) return full;
		if (small != null && small.name().equalsIgnoreCase(imageName)) return small;
		if (large != null && large.name().equalsIgnoreCase(imageName)) return large;
		return null;
	}
	
	public Date createdDate() {
		return createdDate;
	}
	
	// -------------------------------- Common method ---------------------------------//
	@Override
	public boolean sameIdentityAs(ImageGroup other) {
		return other != null && this.name().equals(other.name());
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final ImageGroup other = (ImageGroup) object;
		return sameIdentityAs(other);
	}

	
	
	/**
	 * @return Hash code of id
	 */
	@Override
	public int hashCode() {
		return name().hashCode();
	}

	@Override
	public String toString() {
		return new StringBuilder(MailBox.class.getSimpleName())
			.append("Image[")
			.append("name = ").append(name)
			.append(", path = ").append(path)
			.append(", original = ").append(original)
			.append(", full = ").append(full)
			.append(", small = ").append(small)
			.append(", large = ").append(large)
			.append("]")
			.toString();
	}
}
