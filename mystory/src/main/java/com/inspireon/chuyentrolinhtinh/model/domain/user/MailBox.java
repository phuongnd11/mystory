package com.inspireon.chuyentrolinhtinh.model.domain.user;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.inspireon.chuyentrolinhtinh.model.domain.shared.ValueObject;

public class MailBox implements ValueObject<MailBox>{
	
	private static final long serialVersionUID = 7600924177086089491L;

	private static final MailBox EMPTY_MAILBOX = new MailBox();
	
	// ------------------------------ Properties ----------------------------- //
    private List<Message> inboxes;
    
    private List<Message> sentItems;

	// ---------------------------------- Constructors --------------------------------- //
	private MailBox() {
		this.inboxes = Collections.<Message> emptyList();
		this.sentItems = Collections.<Message> emptyList();
	}

	// ---------------------------------- Business logic --------------------------------- //
	public static MailBox emptyMailBox() {
		return EMPTY_MAILBOX;
	}

	public List<Message> inboxes() {
		return inboxes;
	}

	public List<Message> sentItems() {
		return sentItems;
	}
	
	public void addInboxMessage(Message message){
		inboxes.add(message);
	}

	public void addSentMessage(Message message){
		sentItems.add(message);
	}
	
	// implement later
//	public void removeInboxMessage(Message message){
//		inboxes.remove(message);
//	}
//
//	public void removeSentMessage(Message message){
//		sentItems.remove(message);
//	}
	
	public void markRead(Message message) {
		if (inboxes.contains(message) || sentItems.contains(message)) {
			message.markRead();
		}  
	}
	
	// ---------------------------------- Common logic --------------------------------- //
	@Override
	public boolean sameValueAs(MailBox other) {
		return other != null && new EqualsBuilder()
			.append(this.inboxes, other.inboxes)
			.append(this.sentItems, other.sentItems)
			.isEquals();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		final MailBox other = (MailBox) obj;

		return sameValueAs(other);

	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(inboxes)
			.append(sentItems)
			.toHashCode();
	}

	@Override
	public String toString() {
		return new StringBuilder(MailBox.class.getSimpleName())
			.append("[")
			.append("inboxes = ").append(inboxes)
			.append(", sentItems = ").append(sentItems)
			.append("]")
			.toString();
	}
}
