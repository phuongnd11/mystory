package com.inspireon.mystory.model.domain.wall;

import java.util.List;

import com.inspireon.mystory.persistence.BaseRepo;

public interface WallRepo extends BaseRepo<Wall, String> {
	
	Wall findCurrentWall(String username);
	
	List<Wall> findWallsInSpecifyMonth(String username, String month);
}
