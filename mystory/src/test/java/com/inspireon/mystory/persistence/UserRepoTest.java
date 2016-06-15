package com.inspireon.mystory.persistence;


import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inspireon.mystory.model.domain.user.FacebookSetting;
import com.inspireon.mystory.model.domain.user.InvitationCode;
import com.inspireon.mystory.model.domain.user.InvitationCodeRepo;
import com.inspireon.mystory.model.domain.user.Setting;
import com.inspireon.mystory.model.domain.user.TagSetting;
import com.inspireon.mystory.model.domain.user.User;
import com.inspireon.mystory.model.domain.user.UserRepo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-context.xml"})
public class UserRepoTest {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	InvitationCodeRepo invitationCodeRepo;
	
//	@Test
//	public void findByEmailTest(){
//		
//		User user = userRepo.findByEmail("phuongnd11@gmail.com");
//	    Assert.assertNotNull(user);
//		
//	}
	
	@Ignore
	@Test
	public void findByUsernameTest(){
		
		// Prepare data
		User oriUser = MockObjectUtils.createUser("C_rankTest1", "pass",  "@mail");
	
		userRepo.add(oriUser);
		
		// Verify insert user successfully
		User afterStoreUser = userRepo.findByUsername("C_rankTest1");
		Assert.assertNotNull(afterStoreUser);
		
		Assert.assertEquals(oriUser.username(), afterStoreUser.username());
		Assert.assertEquals(oriUser.password(), afterStoreUser.password());
		Assert.assertEquals(oriUser.email(), afterStoreUser.email());		
		
	    // Roll back data
	    userRepo.remove(oriUser);   
	}	
	
	
	@Ignore
	@Test
	public void findByEmailTest(){
		// Prepare data
		User oriUser = MockObjectUtils.createUser("C_findbyemail", "pass",  "@findbyemail");
	
		userRepo.add(oriUser);
		
		// Verify insert user successfully
		User afterStoreUser = userRepo.findByEmail("@findbyemail");
		Assert.assertNotNull(afterStoreUser);
		
		Assert.assertEquals(oriUser.username(), afterStoreUser.username());
		Assert.assertEquals(oriUser.password(), afterStoreUser.password());
		Assert.assertEquals(oriUser.email(), afterStoreUser.email());
		
		
	    // Roll back data
	    userRepo.remove(oriUser);    
	}	
	
	@Ignore
	@Test
	public void updateTest(){
		// Prepare data
		User user = MockObjectUtils.createUser("C_updateTest1", "pass",  "@mail");
		
		Setting setting = new Setting("alogan1", "en", new FacebookSetting("fb1", true, true), TagSetting.defaultTagSetting());
		user.updateSetting(setting);
				
		userRepo.add(user);
		user = userRepo.findByUsername("C_updateTest1");
			
	    Assert.assertEquals(user.setting().slogan(), "alogan1");
	    Assert.assertEquals(user.setting().lang(), "en");
	    Assert.assertEquals(user.setting().facebookSetting().facebook(), "fb1");
	    Assert.assertTrue(user.setting().facebookSetting().allowPostingActivities());
	    Assert.assertTrue(user.setting().facebookSetting().displayToOthers());
		
	    // update data
	    Setting newSetting = new Setting("AAA", "pt", new FacebookSetting("fb2", false, false), TagSetting.defaultTagSetting());	    
	    user.updateSetting(newSetting);
	    
	    userRepo.store(user);
	    
	    user = userRepo.findByUsername("C_updateTest1");
	    
	    Assert.assertEquals(user.setting().slogan(), "AAA");
	    Assert.assertEquals(user.setting().lang(), "pt");
	    Assert.assertEquals(user.setting().facebookSetting().facebook(), "fb2");
	    Assert.assertFalse(user.setting().facebookSetting().allowPostingActivities());
	    Assert.assertFalse(user.setting().facebookSetting().displayToOthers());
		
		// Roll back
	    userRepo.remove(user);
	}
	
	
//	@Ignore
	@Test
	public void testRegisterNewUser_Successfully(){
		//$2a$10$OyhKUj5nfP4WiFlkjlIcwOsCec9/tsEfYXUZ190LrLLfUl03sj5oy
		User newUser = new User("blueiris", "123456", "blueiris@gmail.com");
		newUser.updateSetting(new Setting("Who am I?", "vi", new FacebookSetting("http://facebook.com/mystory", true, false), TagSetting.defaultTagSetting()));
		
		// store the newly created user to repository
		userRepo.store(newUser);
		
		// get id generated by MongoDB
		String id = newUser.id();
		
		// retrieve this user from repository
		User retrievedUser = userRepo.find(id);
		
		Assert.assertEquals(newUser.username(), retrievedUser.username());
	}
		
	@Ignore
	@Test
	public void addInvitationCodeTest(){

		InvitationCode inviteCode = new InvitationCode("blueiris");
		
		invitationCodeRepo.add(inviteCode);
		
		System.out.println(inviteCode.id() + ".." + inviteCode.code());
	}
	
	@Test
	@Ignore
	public void testFindTopStoryTellers() {
		List<User> topTellers = userRepo.findTopStoryTellers();
		
		Assert.assertEquals(topTellers.size(), 5);
	}
	
	@Test
	@Ignore
	public void testUpdateDefaultTagSetting() {
		List<User> allUsers = userRepo.findAll();
		
		for (User user : allUsers) {
			user.updateSetting(new Setting(user.setting().slogan(), user.setting().lang(), user.setting().facebookSetting(), TagSetting.defaultTagSetting()));
			userRepo.store(user);
		}
	}
}