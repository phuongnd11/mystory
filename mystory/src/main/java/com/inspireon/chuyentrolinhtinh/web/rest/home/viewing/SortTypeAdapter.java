package com.inspireon.chuyentrolinhtinh.web.rest.home.viewing;

import com.inspireon.chuyentrolinhtinh.model.domain.shared.ValueObject;
import com.inspireon.chuyentrolinhtinh.model.domain.story.Tag;

public class SortTypeAdapter {
	
	private static final String SLASH = "/";
	
	private Type type;
	
	private Tag currentTag;
	
	private Type selectedSort;
	
	public SortTypeAdapter(Type type, Tag currentTag, Type selectedSort) {
		this.type = type;
		this.currentTag = currentTag;
		this.selectedSort = selectedSort;
	}
	
	public String getName() {
		return type.name();
	}
	
	public String getDisplayName() {
		return type.displayName();
	}

	public String getUrl() {
		String prefixUrl;
		
		if (Tag.ALL.equals(this.currentTag)) {
			prefixUrl = HomeViewingController.HOME_URL;
		} else {
			prefixUrl = HomeViewingController.TAG_URL + SLASH + currentTag.name().toLowerCase();
		}
		
		return prefixUrl + SLASH + type.name().toLowerCase();
	}
	
	public boolean isSelected() {
		return type.equals(selectedSort);
	}

	public enum Type implements ValueObject<Type> {	
		HOT("Hot"), NEW("Mới"), CONTROVERSIAL("Tranh luận"), LEGEND("Huyền thoại");
	
		private final String displayName;
		
		@Override
		public boolean sameValueAs(final Type other) {
			return this.equals(other);
		}
		
		private Type(final String displayName) {
			this.displayName = displayName;
		}
		
		public String displayName() {
			return displayName;
		}
	}
}
