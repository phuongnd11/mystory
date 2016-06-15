package com.inspireon.chuyentrolinhtinh.persistence.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.inspireon.chuyentrolinhtinh.model.domain.user.InvitationCode;
import com.inspireon.chuyentrolinhtinh.model.domain.user.InvitationCodeRepo;
import com.inspireon.chuyentrolinhtinh.persistence.BaseRepoImpl;

@Repository
public class InvitationCodeRepoImpl extends BaseRepoImpl<InvitationCode, String> implements InvitationCodeRepo{

	@Override
	public Collection<InvitationCode> findByUser(String username) {
		Query query = new Query();
		
		Criteria criteria = new Criteria();
		
		criteria = criteria.and("generatedBy").is(username);
		
		query.addCriteria(criteria);
		
		List<InvitationCode> invitationCodes = getTemplate().find(query, InvitationCode.class);
		return invitationCodes;
	}

	@Override
	public InvitationCode findByCode(String code) {
		Query query = new Query();
		
		Criteria criteria = new Criteria();
		
		criteria = criteria.and("code").is(code);
		
		query.addCriteria(criteria);
		
		List<InvitationCode> invitationCodes = getTemplate().find(query, InvitationCode.class);
		if(invitationCodes.size() == 1) return invitationCodes.get(0);
		else return null;
	}
		
	
}
