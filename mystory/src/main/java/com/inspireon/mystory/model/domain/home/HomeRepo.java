package com.inspireon.mystory.model.domain.home;

import java.util.List;

import com.inspireon.mystory.persistence.BaseRepo;

public interface HomeRepo extends BaseRepo<Home, String> {
	
	List<Home> findRecentHomes();
	
	Home findHomeOfToday();
}