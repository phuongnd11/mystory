package com.inspireon.chuyentrolinhtinh.model.domain.relation;

import java.util.Date;
import java.util.List;

import com.inspireon.chuyentrolinhtinh.persistence.BaseRepo;

public interface RelationRepo extends BaseRepo<Relation, String>{
	
	List<Relation> findUserRelationWithStories(String userId);

	// find all people which userId follows
	List<Relation> findUserRelationWithStoryTellers(String userId);
	
	//find all fan of inspirerId
	List<Relation> findNewFollowers(String inspirerId, Date cutOffTime);
	
	Relation findRelationSubscribe(String userId, String storyId);

	Relation findRelationFollow(String userId, String followeeId);
}
