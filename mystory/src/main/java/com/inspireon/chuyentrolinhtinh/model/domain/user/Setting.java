package com.inspireon.chuyentrolinhtinh.model.domain.user;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inspireon.chuyentrolinhtinh.model.domain.shared.ValueObject;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.i18n.I18NUtils;

public class Setting implements ValueObject<Setting> {

	private static final long serialVersionUID = 6357718291453837281L;

	public static final Setting DEFAULT = new Setting(StringUtils.EMPTY, I18NUtils.DEFAULT_LANG, FacebookSetting.defaultFBSetting(), TagSetting.defaultTagSetting());
	
	// ------------------------------ Properties ----------------------------- //
    private String slogan;
    private String lang;
    private FacebookSetting facebookSetting;
    private TagSetting tagSetting;
    
	// ---------------------------------- Constructors --------------------------------- //
    public Setting(final String slogan, final String lang, final FacebookSetting facebookSetting, final TagSetting tagSetting) {
		this.slogan = slogan;
		this.lang = lang;
		this.facebookSetting = facebookSetting;
		this.tagSetting = tagSetting;
	}

	// ---------------------------------- Business logic --------------------------------- //
	public static Setting defaultSetting() {
		return DEFAULT;
	}
    
	public String slogan() {
		return slogan;
	}

	public String lang() {
		return lang;
	}

	public FacebookSetting facebookSetting() {
		return facebookSetting;
	}
    
	public TagSetting tagSetting() {
		return tagSetting;
	}

	public void updateTagSetting(TagSetting newTagSetting) {
		this.tagSetting = newTagSetting;
	}
	
	
	// ---------------------------------- Common logic --------------------------------- //
	@Override
	public boolean sameValueAs(Setting other) {
		return other != null && new EqualsBuilder()
				.append(this.slogan, other.slogan)
				.append(this.lang, other.lang)
				.append(this.facebookSetting, other.facebookSetting)
				.append(this.tagSetting, other.tagSetting)
				.isEquals();
		
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		final Setting other = (Setting) obj;

		return sameValueAs(other);
	}


	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(slogan)
				.append(lang)
				.append(facebookSetting)
				.append(tagSetting)
				.toHashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(Setting.class.getSimpleName());
		
		builder .append("[slogan = ").append(slogan)
				.append(", lang = ").append(lang)
				.append(", facebookSetting = ").append(facebookSetting)
				.append(", tagSetting = ").append(tagSetting)
				.append("]");

		return builder.toString();
	}
}
