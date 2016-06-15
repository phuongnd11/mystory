package com.inspireon.chuyentrolinhtinh.model.domain.relation;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.inspireon.chuyentrolinhtinh.model.domain.shared.Entity;

@Document(collection="relations")
public class Relation extends Entity<Relation>{

	private String userId;
	
	private String objectId; 
	
	private Date startTime;
	
	private Date lastViewedTime;
	
	private RelationType relationType;
	
	
	public Relation(String userId, String objectId, RelationType relationType){
		this.userId = userId;
		this.objectId = objectId;
		this.relationType = relationType;
		this.startTime = new Date();
		this.lastViewedTime = startTime;
	}

	public String userId(){
		return userId;
	}
	
	public String objectId(){
		return objectId;
	}
	
	public void updateLastViewedTime(Date lastViewedTime){
		this.lastViewedTime = lastViewedTime;
	}
	
	public Date lastViewdTime(){
		return lastViewedTime;
	}
	
	public Date startTime(){
		return startTime;
	}

	@Override
	public boolean sameIdentityAs(Relation other) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
