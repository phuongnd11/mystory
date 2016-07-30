package com.inspireon.chuyentrolinhtinh.persistence;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inspireon.chuyentrolinhtinh.model.domain.comment.Comment;
import com.inspireon.chuyentrolinhtinh.model.domain.comment.CommentRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.notification.Notification;
import com.inspireon.chuyentrolinhtinh.model.domain.notification.NotificationRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.post.PostRepo;
import com.inspireon.chuyentrolinhtinh.model.domain.relation.Relation;
import com.inspireon.chuyentrolinhtinh.model.domain.relation.RelationRepo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-context.xml"})
public class RelationRepoTest {

	@Autowired
	private RelationRepo relationRepo;
	
	@Autowired
	private PostRepo storyRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private NotificationRepo notificationRepo;
	
	@Ignore
	@Test
	public void checkIfUserSubscribedAStoryTest(){
		
		List<Relation> relations =  relationRepo.findUserRelationWithStories("529eed91870bacff2ac367f2");
		
		for(Relation relation : relations){
			System.out.println(relation.objectId());
			System.out.println(relation.lastViewdTime());
			
			Date now = new Date();
			// find all comments from the story which submitted after lastViewedTime
			//System.out.println(commentRepo.findByStoryId(relation.objectId()));
			//List<Comment> comments = commentRepo.findNewComment(relation.objectId(), relation.lastViewdTime());
			
			//System.out.println(comments);
		}
	}
	
	@Ignore
	@Test
	public void check(){
		for(Notification notification : notificationRepo.findAll()){
			
				notificationRepo.remove(notification);
		}
		
		for(Relation notification : relationRepo.findAll()){
			
			relationRepo.remove(notification);
		}
	}
	
	
	@Ignore
	@Test
	public void check2(){
		Date d = relationRepo.find("52a88bfab5e3b8c7dbd6a59b").lastViewdTime();
		d.setHours(0);;;
		for(Comment comment : commentRepo.findNewCommentToNotify("phuongnd12", "52a1c9f527aa20d707d68a26", d)){
			System.out.println(comment.author());
			System.out.println(comment.content());
		}
	}
	
}
