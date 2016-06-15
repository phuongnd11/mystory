package com.inspireon.chuyentrolinhtinh.model.domain.user;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inspireon.chuyentrolinhtinh.model.domain.shared.ValueObject;

public class Message implements ValueObject<Message>{
	
	private static final long serialVersionUID = 4494641333411193967L;
	private static final String DEFAULT_TITLE = "(No title)";
	private static final String DEFAULT_CONTENT = StringUtils.EMPTY;
	// ------------------------------ Properties ------------------------------------- //
	private String from;
	private String to;
	private String title;
	private String content;
	private Date submittedTime;	
	private boolean isRead;
	
	// ------------------------------ Constructors ----------------------------------- //
	public Message(String from, String to, String title, String content) {
		this.from = from;
		this.to = to;
		this.title = (title == null) ? DEFAULT_TITLE : title;
		this.content = (content == null) ? DEFAULT_CONTENT : content;
		this.submittedTime = new Date();
		this.isRead = false;
	}
	
	// ------------------------------ Business logic --------------------------------- //	
	public String from() {
		return from;
	}

	public String to() {
		return to;
	}

	public String title() {
		return title;
	}

	public String content() {
		return content;
	}

	public Date submittedTime() {
		return submittedTime;
	}

	public boolean isRead() {
		return isRead;
	}
	
	public void markRead() {
		this.isRead = true;
	}	
	
	// ------------------------------ Common logic ----------------------------------- //
	@Override
	public boolean sameValueAs(Message other) {
		return other != null && new EqualsBuilder()
				.append(this.from, other.from)
				.append(this.to, other.to)
			    .append(this.submittedTime, other.submittedTime)
			    .isEquals();
	}		
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		final Message other = (Message) obj;

		return sameValueAs(other);

	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(from)
				.append(to)
				.append(submittedTime)
				.toHashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(Message.class.getSimpleName());
		
		builder .append("[from = ").append(from)
				.append(", to = ").append(to)
				.append(", title = ").append(title)
				.append(", content = ").append(content)
				.append(", submittedTime = ").append(submittedTime)
				.append(", isRead = ").append(isRead)
				.append("]");

		return builder.toString();
	}	
}
