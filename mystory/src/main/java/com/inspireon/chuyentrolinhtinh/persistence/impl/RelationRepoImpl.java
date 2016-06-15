package com.inspireon.chuyentrolinhtinh.persistence.impl;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.inspireon.chuyentrolinhtinh.model.domain.relation.Relation;
import com.inspireon.chuyentrolinhtinh.model.domain.relation.RelationRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.relation.RelationType;
import com.inspireon.chuyentrolinhtinh.persistence.BaseRepoImpl;

@Repository
public class RelationRepoImpl extends BaseRepoImpl<Relation, String> implements RelationRepo{

	@Override
	public Relation findRelationSubscribe(String userId, String storyId) {
		
		List<Relation> relations = null;
		
		Criteria criteria = new Criteria();
		
		criteria = criteria.and("userId").is(userId).and("objectId").is(storyId);
		
		criteria = criteria.and("relationType").in(RelationType.SUBSCRIBE);
		
		relations = getTemplate().find(new Query().addCriteria(criteria), Relation.class);
		
		if(relations == null || relations.size() ==0) return null;
		
		return relations.get(0);
	}


	@Override
	public Relation findRelationFollow(String userId,
			String followeeId) {
		
		List<Relation> relations = null;
		
		Criteria criteria = new Criteria();
		
		criteria = criteria.and("userId").is(userId).and("objectId").is(followeeId);
		
		criteria = criteria.and("relationType").in(RelationType.FOLLOW);
		
		
		relations = getTemplate().find(new Query().addCriteria(criteria), Relation.class);
		
		if(relations == null || relations.size() ==0) return null;
		
		return relations.get(0);
	}


	@Override
	public List<Relation> findUserRelationWithStories(String userId) {
		
		Criteria criteria = new Criteria();
		
		criteria = criteria.and("userId").is(userId);
		
		criteria = criteria.and("relationType").in(RelationType.SUBSCRIBE);
		
		
		return getTemplate().find(new Query().addCriteria(criteria), Relation.class);
		
	}


	@Override
	public List<Relation> findUserRelationWithStoryTellers(String userId) {
		
		Criteria criteria = new Criteria();
		
		criteria = criteria.and("userId").is(userId);
		
		criteria = criteria.and("relationType").in(RelationType.FOLLOW);
		
		
		return getTemplate().find(new Query().addCriteria(criteria), Relation.class);
	}

	
	@Override
	public List<Relation> findNewFollowers(String inspirerId, Date cutOffTime){
		
		Criteria criteria = new Criteria().where("startTime").gte(cutOffTime).and("objectId").is(inspirerId);
		
		criteria = criteria.and("relationType").is(RelationType.FOLLOW);
		
		
		return getTemplate().find(new Query().addCriteria(criteria), Relation.class);
	}
	
}
