package com.inspireon.chuyentrolinhtinh.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inspireon.chuyentrolinhtinh.application.RelationService;
import com.inspireon.chuyentrolinhtinh.model.domain.relation.Relation;
import com.inspireon.chuyentrolinhtinh.model.domain.relation.RelationRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.relation.RelationType;


/**
 * 
 * @author Phuong
 *
 */
@Service
public class RelationServiceImpl implements RelationService{

	@Autowired
	private RelationRepo relationRepo;
	
	@Override
	public void followAStory(String userId, String storyId) {
		
		Relation relation = relationRepo.findRelationSubscribe(userId, storyId);
		if(relation == null){
			Relation newRelation = new Relation(userId,storyId, RelationType.SUBSCRIBE);
			relationRepo.add(newRelation);
		}
		//else relation.updateLastViewedTime(new Date());
		
		//relationRepo.store(relation);
	}

	@Override
	public void followAStoryTeller(String userId, String followeeId) {
		
		Relation relation = relationRepo.findRelationSubscribe(userId, followeeId);
		
		if(relation == null){
			Relation newRelation = new Relation(userId, followeeId, RelationType.FOLLOW);
			relationRepo.add(newRelation);
		}
		//else relation.updateLastViewedTime(new Date());
		
		//relationRepo.store(relation);
	}
	

	@Override
	public void removeRelation(String userId, String ObjectId) {
		
		Relation relation = relationRepo.findRelationFollow(userId, ObjectId);
		
		if(relation!=null)
		
			relationRepo.remove(relation);
	}
	
}
