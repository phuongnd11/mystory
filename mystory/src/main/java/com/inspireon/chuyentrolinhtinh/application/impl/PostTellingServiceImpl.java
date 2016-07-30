package com.inspireon.chuyentrolinhtinh.application.impl;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inspireon.chuyentrolinhtinh.application.ImageGroupService;
import com.inspireon.chuyentrolinhtinh.application.PostTellingService;
import com.inspireon.chuyentrolinhtinh.common.util.DateUtils;
import com.inspireon.chuyentrolinhtinh.model.domain.home.Home;
import com.inspireon.chuyentrolinhtinh.model.domain.home.HomeRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.image.ImageGroup;
import com.inspireon.chuyentrolinhtinh.model.domain.newsfeed.NewsFeed;
import com.inspireon.chuyentrolinhtinh.model.domain.newsfeed.NewsFeedRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.post.Post;
import com.inspireon.chuyentrolinhtinh.model.domain.post.PostRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.post.Tag;
import com.inspireon.chuyentrolinhtinh.model.domain.post.Version;
import com.inspireon.chuyentrolinhtinh.model.domain.user.Follower;
import com.inspireon.chuyentrolinhtinh.model.domain.user.User;
import com.inspireon.chuyentrolinhtinh.model.domain.user.UserRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.wall.Wall;
import com.inspireon.chuyentrolinhtinh.model.domain.wall.WallRepo;

@Service
public class PostTellingServiceImpl implements PostTellingService {

	private static final Logger logger = Logger.getLogger(PostTellingServiceImpl.class);

	@Autowired
	private PostRepo storyRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ImageGroupService imageGroupService;	
	
	@Autowired
	private NewsFeedRepo newsFeedRepo;
	
	@Autowired
	private WallRepo wallRepo;
	
	@Autowired
	private HomeRepo homeRepo;

	@Override
	public String tellNewStory(String tellerName, String title, String content,
			String originalStoryId, ImageGroup featuredImage, Tag tag) {
		Post storyUrl = null;
		String friendUrl = getFriendUrl(title);
		storyUrl = storyRepo.findByFriendlyUrl(friendUrl);

		if (storyUrl != null) {
			friendUrl = getFriendUrl(title) + "-" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		}
		
		Post newStory = new Post(tellerName, title, content, originalStoryId, featuredImage, tag);
		newStory.setFriendlyUrl(friendUrl);
		
		try {
			storyRepo.store(newStory);
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		User teller = userRepo.findByUsername(tellerName);
		
		teller.contributeOneMoreStory();
		
		try {
			userRepo.store(teller);
		} catch (Exception e) {
			logger.error(e, e);
			storyRepo.remove(newStory);
			throw e;
		}
		
		postToWallOfTeller(tellerName, newStory);
		postToNewsFeedOfFollowers(teller, newStory);
		postToHomeOfCommunity(newStory);
		
		return newStory.friendlyUrl();
	}
	
	private void postToWallOfTeller(String tellerName, Post newStory) {
		Wall wallOfTeller = wallRepo.findCurrentWall(tellerName);
		
		if (wallOfTeller == null) {
			wallOfTeller = new Wall(tellerName, DateUtils.dateToMonthFormat(new Date()));
		}
		
		wallOfTeller.addStory(newStory);
		wallRepo.store(wallOfTeller);
	}
	
	private void postToNewsFeedOfFollowers(User teller, Post newStory) {
		Set<Follower> followers = teller.followers();
		NewsFeed newsFeedofFollower; 
		
		for (Follower follower : followers) {
			newsFeedofFollower = newsFeedRepo.findCurrentNewsFeed(follower.name());
			
			if (newsFeedofFollower == null) {
				newsFeedofFollower = new NewsFeed(teller.username(), DateUtils.dateToMonthFormat(new Date()));
			}
			
			newsFeedofFollower.addStory(newStory);
			newsFeedRepo.store(newsFeedofFollower);
		}
	}
	
	private void postToHomeOfCommunity(Post newStory) {
		Home homeOfToday = homeRepo.findHomeOfToday();
		
		if (homeOfToday == null) {
			homeOfToday = new Home(DateUtils.dateToDayFormat(new Date()));
		}
		
		homeOfToday.addStory(newStory);
		homeRepo.store(homeOfToday);
	}
	
	@Override
	public void editStory(String storyId, String editor, String newTitle,
			String newContent, String newOriginalStoryId,
			ImageGroup newFeaturedImage, Tag newTag) {

		try {
			Post story = storyRepo.findByFriendlyUrl(storyId);
				
			// check permission for edit this story
			Version lastVersion = new Version(story.content(), story.lastEditedBy(), story.lastEditedTime());
			story.addVersion(lastVersion);
			
			story.updateTitle(newTitle);
			story.updateContent(newContent);
			story.updateOriginalStoryId(newOriginalStoryId);
			story.updateTags(newTag);
			story.updateLastEditedBy(editor);
			story.updateLastEditedTime(new Date());
			story.updateFeaturedImage(newFeaturedImage);	
					
			storyRepo.store(story);
		} catch (Exception e) {
			logger.error(e, e);
			throw e;
		} 
	}

	@Override
	public void closeStory(Post story) {
		// check permission for close this story
		story.close();
		
		storyRepo.store(story);
	}
	
	public static void main(String[] args) {
		System.out.println(getFriendUrl("một title rất dài và nhiều ký tự đặc biệt !%$$& (*&()4 *&^%*& ... <html> </html>"));
	}
	public static String getFriendUrl(String title) {
		String normal = Normalizer.normalize(title, Normalizer.Form.NFD);
		String url = normal.replaceAll("\\p{M}", "").replaceAll("Đ", "D").replaceAll("đ", "d").replaceAll("\\<[^>]*>","").replaceAll("[^\\w\\s]","").replaceAll("\\s+", " ").trim().replace(" ", "-");
		return url;
	}
}
