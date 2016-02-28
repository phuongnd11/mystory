package com.inspireon.mystory.model.domain.user;

import java.util.Collection;

import com.inspireon.mystory.persistence.BaseRepo;

public interface InvitationCodeRepo extends BaseRepo<InvitationCode, String>{
	
	Collection<InvitationCode> findByUser(String username);

	InvitationCode findByCode(String code);
}
