package com.inspireon.chuyentrolinhtinh.persistence;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inspireon.chuyentrolinhtinh.model.domain.newsfeed.NewsFeed;
import com.inspireon.chuyentrolinhtinh.model.domain.newsfeed.NewsFeedRepo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-context.xml"})
public class NewsFeedRepoTest {

	@Autowired
	NewsFeedRepo newsFeedRepo;
	
	@Test
	@Ignore
	public void testFindNewFeed_Succesfully(){
		NewsFeed newsFeed = newsFeedRepo.findCurrentNewsFeed("blueiris");
	}
	
}
