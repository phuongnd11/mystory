package com.inspireon.mystory.application;


public interface SecurityService {
	
	boolean checkStoryAuthor(String storyId, String editor);
	
	boolean checkCommentAuthor(String commentId, String editor);
	
	boolean checkUserValid(String username, String password);
}
