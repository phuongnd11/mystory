package com.inspireon.chuyentrolinhtinh.model.domain.home;

import java.util.List;

import com.inspireon.chuyentrolinhtinh.persistence.BaseRepo;

public interface HomeRepo extends BaseRepo<Home, String> {
	
	List<Home> findRecentHomes();
	
	Home findHomeOfToday();
}