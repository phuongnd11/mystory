package com.inspireon.mystory.model.domain.notification;

import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.inspireon.mystory.model.domain.shared.Entity;

@Document(collection="notifications")
public class Notification extends Entity<Notification> implements Comparable<Notification>{

	@Indexed
	private String userId;
	
	private String followeeId;
	
	private String sourceId;
	
	private String sourceTitle;
	
	private ActionType actionType;

	private String message;
	
	private Integer total;
	
	private Date createdDate;
	
	private Date actionDate;

/*	public Notification(){
		
	}
	
	public Notification(String userId, String followeeId, String sourceId,
			ActionType actionType, Integer total, String message){
		this.userId = userId;
		this.followeeId = followeeId;
		this.sourceId = sourceId;
		//this.sourceTitle = souceTitle;
		this.actionType = actionType;
		this.total = total;
		this.message = message;
		//this.createdDate = createdDate;
		//this.viewedTime = new Date();
	}
	*/
	public Notification(String userId, String followeeId, String sourceId, String sourceTitle,
			ActionType actionType, Integer total, String message, Date createdDate, Date actionDate){
		this.userId = userId;
		this.followeeId = followeeId;
		this.sourceId = sourceId;
		this.sourceTitle = sourceTitle;
		this.actionType = actionType;
		this.total = total;
		this.message = message;
		this.createdDate = createdDate;
		this.actionDate = actionDate;
		//this.viewedTime = new Date();
	}
	
	@Override
	public boolean sameIdentityAs(Notification other) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public Date createdDate(){
		return createdDate;
	}
	
	public ActionType actionType(){
		return actionType;
	}
	
	public String message(){
		return message;
	}

	public Integer total() {
		return total;
	}
	
	public String followeeId(){
		return followeeId;
	}

	public String sourceId() {
		return sourceId;
	}
	
	public String sourceTitle(){
		return sourceTitle;
	}
	
	public Date actionDate(){
		return actionDate;
	}

	@Override
	public int compareTo(Notification other) {
		if(this.actionDate.after(other.actionDate)) return -1;
		else if(this.actionDate.before(other.actionDate)) return 1;
		return 0;
	}
}
