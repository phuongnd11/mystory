package com.inspireon.mystory.application.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inspireon.mystory.application.NotificationService;
import com.inspireon.mystory.model.domain.comment.Comment;
import com.inspireon.mystory.model.domain.comment.CommentRepo;
import com.inspireon.mystory.model.domain.notification.ActionType;
import com.inspireon.mystory.model.domain.notification.Notification;
import com.inspireon.mystory.model.domain.notification.NotificationRepo;
import com.inspireon.mystory.model.domain.relation.Relation;
import com.inspireon.mystory.model.domain.relation.RelationRepo;
import com.inspireon.mystory.model.domain.story.Story;
import com.inspireon.mystory.model.domain.story.StoryRepo;
import com.inspireon.mystory.model.domain.user.Message;
import com.inspireon.mystory.model.domain.user.User;
import com.inspireon.mystory.model.domain.user.UserRepo;

@Service
public class NotificationServiceImpl implements NotificationService{

	@Autowired
	private RelationRepo relationRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private NotificationRepo notificationRepo;
	
	@Autowired
	private StoryRepo storyRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public List<Notification> getNewNotificationForUser(String userId) {
		
		List<Notification> notifications = notificationRepo.findAllNotification(userId);
		
		Date lastViewMessageNotification = new Date(0l);
		Date lastViewFanNotification = new Date(0l);
		
		
		if(notifications == null) notifications = new ArrayList<Notification>();
		
		else{
			for(Notification notification : notifications){
				if(notification.actionType().equals(ActionType.MESSAGE) && notification.createdDate().after(lastViewMessageNotification))
					lastViewMessageNotification = notification.createdDate();
				
				if(notification.actionType().equals(ActionType.FAN) && notification.createdDate().after(lastViewFanNotification))
					lastViewFanNotification = notification.createdDate();
			}
		}
		
		List<Relation> relations = relationRepo.findUserRelationWithStories(userId);
		if(relations != null && relations.size() >0){
			List<Comment> comments = null;
			for(Relation relation : relations){
				/*Date cutOffTime = null;
				
				if(relation.lastViewdTime().before(user.lastViewedNotification())){
					cutOffTime = user.lastViewedNotification();
				}
				else cutOffTime = relation.lastViewdTime();*/
				
				// find all comments from the story which submitted after lastViewedTime
				comments = commentRepo.findNewCommentToNotify(userRepo.find(userId).username(), relation.objectId(), relation.lastViewdTime());
				relation.updateLastViewedTime(new Date());
				
				if(comments != null && comments.size() > 0){
					Set<String> authors = new HashSet<String>();
					// group by author
					for(Comment comment : comments) authors.add(comment.author());
					if(authors.size() > 0){
						Notification newNotifi = new Notification(userId, relation.objectId(), relation.objectId(), storyRepo.find(relation.objectId()).title(),
								ActionType.COMMENT, authors.size(), comments.get(comments.size()-1).author(), new Date(), comments.get(comments.size()-1).submittedDate());
						
						notifications.add(newNotifi);
						notificationRepo.add(newNotifi);
					}
				}
				
				relationRepo.store(relation);
			}
		}
		
		
		// find all users which this user followed
		List<Relation> userRelations = relationRepo.findUserRelationWithStoryTellers(userId);
		if(userRelations != null && userRelations.size() >0){
			List<Story> stories = null;
			for(Relation relation : userRelations){
				String author = userRepo.find(relation.objectId()).username();
				/*Date cutOffTime = null;
				
				if(relation.lastViewdTime().before(user.lastViewedNotification())){
					cutOffTime = user.lastViewedNotification();
				}
				else cutOffTime = relation.lastViewdTime();*/
				// find all stories from the author which submitted after lastViewedTime
				stories = storyRepo.findRecentStoriesByAuthor(author, 
						relation.lastViewdTime());
				relation.updateLastViewedTime(new Date());
				
				if(stories != null && stories.size() >0){
					
					for(Story story : stories){
						Notification newNotifi = new Notification(userId, relation.objectId(), story.id(), story.title(),
								ActionType.POST, 1, author, new Date(), story.submittedDate());
						
						notifications.add(newNotifi);
						notificationRepo.add(newNotifi);
					}
				}
				
				relationRepo.store(relation);
			}
		}
		
		// find new messages
		User user = userRepo.find(userId);
		if(user.mailBox() != null && user.mailBox().inboxes() !=null ){
			List<Message> messages = user.mailBox().inboxes();
			Date latestMessageDate = new Date(0);
			
			String latestFrom = "";
			
			Set<String> froms = new HashSet<String>();
			int total = 0;
			
			for(Message message : messages){
				if(message.submittedTime().after(lastViewMessageNotification)){
					total++;
					froms.add(message.from());
					if(message.submittedTime().after(latestMessageDate))
						latestMessageDate = message.submittedTime();
						latestFrom = message.from();
				}
			}
			if(froms.size() > 0){
				Notification newNotifi = new Notification(userId, String.valueOf(froms.size()), "", "",
						ActionType.MESSAGE, total, latestFrom, new Date(), latestMessageDate);
				
				notifications.add(newNotifi);
				notificationRepo.add(newNotifi);
			}
		}
		
		
		//find new fan 
		List<Relation> fanRelations = relationRepo.findNewFollowers(userId, lastViewFanNotification);
		
		int totalNewFans = 0;
		
		if(fanRelations != null && fanRelations.size()>0){
			for(Relation relation : fanRelations){
				if(relation.startTime().after(lastViewFanNotification)){
					totalNewFans++;
				}
			}
		}
		
		if(totalNewFans > 0){
			String follower = userRepo.find(
					fanRelations.get(0).userId()).username();
			
			Notification newNotifi = new Notification(userId, fanRelations.get(0).objectId(), "", "",
					ActionType.FAN, totalNewFans, follower, new Date(), fanRelations.get(0).startTime());
			
			notifications.add(newNotifi);
			notificationRepo.add(newNotifi);
		}
		
		
		java.util.Collections.sort(notifications);
		
		return notifications;
	}
	
	
}
