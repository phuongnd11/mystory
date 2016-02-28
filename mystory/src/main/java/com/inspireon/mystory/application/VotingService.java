package com.inspireon.mystory.application;

public interface VotingService {
	
	void upVoteStory(String voter, String storyId) throws Exception;
	
	void unUpVoteStory(String voter, String storyId) throws Exception;
	
	void downVoteStory(String voter, String storyId) throws Exception;
	
	void unDownVoteStory(String voter, String storyId) throws Exception;
	
	void upVoteComment(String voter, String commentId) throws Exception;
	
	void unUpVoteComment(String voter, String commentId) throws Exception;
	
	void downVoteComment(String voter, String commentId) throws Exception;
	
	void unDownVoteComment(String voter, String commentId) throws Exception;
}
