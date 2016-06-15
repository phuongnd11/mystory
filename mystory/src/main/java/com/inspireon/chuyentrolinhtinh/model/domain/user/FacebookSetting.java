package com.inspireon.chuyentrolinhtinh.model.domain.user;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inspireon.chuyentrolinhtinh.model.domain.shared.ValueObject;

public class FacebookSetting implements ValueObject<FacebookSetting> {

	private static final long serialVersionUID = 2799090198952407361L;
	
	private static final String DEFAULT_FACEBOOK_ADDRESS = StringUtils.EMPTY;
	private static final boolean DEFAULT_DISPLAY_TO_OTHERS = Boolean.FALSE;
	private static final boolean DEFAULT_ALLOW_POSTING_ACTIVITIES = Boolean.TRUE;
	
	private static final FacebookSetting DEFAULT = 
			new FacebookSetting(DEFAULT_FACEBOOK_ADDRESS, DEFAULT_DISPLAY_TO_OTHERS, DEFAULT_ALLOW_POSTING_ACTIVITIES);
	
	// ------------------------------ Properties ----------------------------- //
	//facebook id
	private String facebook;
	private boolean displayToOthers;
    private boolean allowPostingActivities;
    
	// ---------------------------------- Constructors --------------------------------- //
	public FacebookSetting(final String facebook, final boolean displayToOthers,
			final boolean allowPostingActivities) {
		this.facebook = facebook;
		this.displayToOthers = displayToOthers;
		this.allowPostingActivities = allowPostingActivities;
	}
	
	// ---------------------------------- Business logic --------------------------------- //

	public static FacebookSetting defaultFBSetting() {
		return DEFAULT;
	}
	
	public String facebook() {
		return facebook;
	}

	public boolean displayToOthers() {
		return displayToOthers;
	}

	public boolean allowPostingActivities() {
		return allowPostingActivities;
	}
	
	// ---------------------------------- Common logic --------------------------------- //
	@Override
	public boolean sameValueAs(FacebookSetting other) {
		return other != null && new EqualsBuilder()
				.append(this.facebook, other.facebook)
				.append(this.displayToOthers, other.displayToOthers)
				.append(this.allowPostingActivities, other.allowPostingActivities)
				.isEquals();
			
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		final FacebookSetting other = (FacebookSetting) obj;

		return sameValueAs(other);
	}


	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(facebook)
				.append(displayToOthers)
				.append(allowPostingActivities)
				.toHashCode();
	}
	
	@Override
	public String toString() {
		return new StringBuilder(FacebookSetting.class.getSimpleName())
				.append("[")
				.append("facebook = ").append(facebook)
				.append(", displayToOthers = ").append(displayToOthers)
				.append(", alowPostingActivities = ").append(allowPostingActivities)
				.append("]")
				.toString();
	}
}
