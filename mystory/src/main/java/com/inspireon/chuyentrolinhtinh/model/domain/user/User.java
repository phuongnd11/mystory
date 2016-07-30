package com.inspireon.chuyentrolinhtinh.model.domain.user;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.inspireon.chuyentrolinhtinh.exception.InvalidPasswordException;
import com.inspireon.chuyentrolinhtinh.model.domain.image.ImageGroup;
import com.inspireon.chuyentrolinhtinh.model.domain.shared.Entity;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.i18n.I18NUtils;

@Document(collection="users")
public class User extends Entity<User>{
    
	@Indexed
    private String username;
    
    private String password;
    
    private String email;
    
    private Date joinedDate;
    
    private Setting setting;
    
    private Role role;
    
    private ImageGroup avatar;
    
    private Status status;
    
    private List<BookmarkPost> bookmarks;
    
    private Set<Follower> followers;
    
    private MailBox mailBox; 
    
    private Contribution contribution;
    
    private Date lasViewedNotification;
    
    // ---------------------------------- Constructors --------------------------------- //
    public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
		
		this.status = Status.INACTIVE;
		
		this.joinedDate = new Date();
		
		this.followers = Collections.emptySet();
		
		this.setting = Setting.defaultSetting();	
		this.mailBox = MailBox.emptyMailBox();
		this.avatar = ImageGroup.defaultAvatarImageGroup();
		this.contribution = Contribution.zeroContribution();
		this.role = Role.USER;
		this.lasViewedNotification = new Date();
	}
    
    public User(String username, String email, String password, String facebook, ImageGroup avatar){
    	this.username = username;
    	this.password = password;
		this.email = email;
		
		this.status = Status.ACTIVE;
		
		this.joinedDate = new Date();
		
		this.followers = Collections.emptySet();
		
		FacebookSetting fb = new FacebookSetting(facebook, Boolean.TRUE, Boolean.TRUE);
		this.setting = new Setting(StringUtils.EMPTY, I18NUtils.DEFAULT_LANG, fb, TagSetting.defaultTagSetting());
		
		this.mailBox = MailBox.emptyMailBox();
		this.avatar = avatar;
		this.contribution = Contribution.zeroContribution();
		this.role = Role.USER;
		this.lasViewedNotification = new Date();
    }
    
    public User(){
    	
    }
    
    // ---------------------------------- Business logic --------------------------------- //
	public String username() {
		return username;
	}

	public String password() {
		return password;
	}
	
	public String email() {
		return email;
	}

	public Date joinedDate() {
		return joinedDate;
	}

	public Setting setting() {
		return setting;
	}
	
	public Role role() {
		return role;
	}

	public ImageGroup avatar() {
		return avatar;
	}

	public Status status() {
		return status;
	}

	public Set<Follower> followers() {
		return followers;
	}
	
	public void follow(User other) {
		other.followers.add(new Follower(this.username));
	}

	public void unfollow(User other) {
		other.followers.remove(new Follower(this.username));
	}
	
	public boolean isFollowedBy(String username) {
		return this.followers.contains(new Follower(username));
	}

	public List<BookmarkPost> showBookmarks() {
		return bookmarks;
	}

	public void bookmarkStory(BookmarkPost story) {
		if (!bookmarks.contains(story)) {
			bookmarks.add(story);
		}
	}
	
	public MailBox mailBox() {
		return mailBox;
	}

	public void updateSetting(Setting newSetting) {
		this.setting = newSetting;
	}

	public void updateTagSetting(TagSetting newTagSetting) {
		this.setting.updateTagSetting(newTagSetting);
	}
	
	public void resetPassword(String newPassword) {
		this.password = encodePassword(newPassword);
	}
	
	public Contribution contribution() {
		return contribution;
	}
	
	
	public void activate(){
		if(status == Status.INACTIVE)
			status = Status.ACTIVE;
	}
	

	public void contributeOneMoreStory() {
		
		int currentNumberOfStories = this.contribution.numberOfStories();
		
		int updatedNumberOfStories = ++currentNumberOfStories;
		
		Contribution newContribution = new Contribution(updatedNumberOfStories, this.contribution.numberOfComments(), this.contribution.points());
		
		this.contribution = newContribution;
	}
	
	public void contributeOneMoreComment() {
		
		int currentNumberOfComments = this.contribution.numberOfComments();
		
		int updatedNumberOfComments = ++currentNumberOfComments;
		
		Contribution newContribution = new Contribution(this.contribution.numberOfStories(), updatedNumberOfComments, this.contribution.points());
		
		this.contribution = newContribution;
	}
	
	public void gainPoint() {
		
		int currentPoints = this.contribution.points();
		
		int updatedPoints = ++currentPoints;
		
		Contribution newContribution = new Contribution(this.contribution.numberOfStories(), this.contribution.numberOfComments(), updatedPoints);
		
		this.contribution = newContribution;
	}
	
	public void losePoint() {
		
		int currentPoints = this.contribution.points();
		
		int updatedPoints = --currentPoints;
		
		Contribution newContribution = new Contribution(this.contribution.numberOfStories(), this.contribution.numberOfComments(), updatedPoints);
		
		this.contribution = newContribution;
	}	

	/**
	 * @author Phuong
	 * 
	 *  using bcrypt encoding alorithm to encode
	 *  
	 * @param password
	 * @return 
	 */
	private String encodePassword(String password){
		PasswordEncoder pe = new BCryptPasswordEncoder();
		return pe.encode(password);
	}
	
	/**
	 * When user change his password, he need input old password.
	 * If input old password don't match old password in storage, he can't change password.
	 * 
	 * @param old password
	 * 		old password 
	 * @param newRawPwd
	 * 		new raw password
	 * 
	 * @throws IllegalArgumentException - if input old password don't match stored old password. 
	 * @author Tung 
	 * @throws InvalidPasswordException 
	 */
	public void changePassword (String oldPassword, String newPassword) throws InvalidPasswordException {
		//logical check matching old password stored in the system with old password input by user
		PasswordEncoder pe = new BCryptPasswordEncoder();
		
		if(pe.matches(oldPassword, this.password))
		
			this.password = encodePassword(newPassword);
		else throw new InvalidPasswordException();
	}

	public void receiveMessage(Message message) {
		mailBox.addInboxMessage(message);
	}
	
	public void sendMessage(Message message) {
		mailBox.addSentMessage(message);
		mailBox.markRead(message);
	}	
	
	
	public Date lastViewedNotification(){
		return lasViewedNotification;
	}
	
	public void updateLastViewedNotification(Date date){
		this.lasViewedNotification = date;
	}
	//May implement later 
//	public void removeInboxMessage(Message message) {
//		mailBox.removeInboxMessage(message);
//	}
//
//	public void removeInboxMessage(List<Message> messages) {
//		for (Message message : messages) {
//			mailBox.removeInboxMessage(message);
//		}	
//	}
//	
//	public void removeSentMessage(Message message) {
//		mailBox.removeSentMessage(message);		
//	}
//
//	public void removeSentMessage(List<Message> messages) {
//		for (Message message : messages) {
//			mailBox.removeSentMessage(message);
//		}		
//	}
	
	public Message readInboxMessage(String from, Date submittedTime) {
		for (Message message : mailBox.inboxes()) {
			if (message != null 
					&& message.from().equalsIgnoreCase(from) 
					&& message.submittedTime().equals(submittedTime)) {
				return message;
			}
		}
		
		return null;
	}

	public Message readSendMessage(String to, Date submittedTime) {
		for (Message message : mailBox.sentItems()) {
			if (message != null 
					&& message.to().equalsIgnoreCase(to) 
					&& message.submittedTime().equals(submittedTime)) {
				return message;
			}
		}
		
		return null;
	}
	
	public void markRead(Message message) {
		mailBox.markRead(message);
	}
	
	public void changeAvatar(ImageGroup avatar) {
		this.avatar = avatar; 
	}
	
	// -------------------------------- Common method ---------------------------------//
	@Override
	public boolean sameIdentityAs(User other) {
		return other != null && this.username.equals(other.username);
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final User other = (User) object;
		return sameIdentityAs(other);
	}

	/**
	 * @return Hash code of username
	 */
	@Override
	public int hashCode() {
		return username.hashCode();
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email + ", joinedDate=" + joinedDate + "]";
	}
	
//	public static void main(String[] args) {
//		
//		//BCryptPasswordEncoder bb = new BCryptPasswordEncoder();
//		PasswordEncoder pe = new BCryptPasswordEncoder();
//		System.out.println(pe.encode("123456"));
//	}
//	

	// used temporary for migration
	/*public User(String username, String password, String email, Status status, Date joinedDate, Contribution contribution, Role role){
		this.username = username;
		this.password = password;
		this.email = email;
		
		this.status = status;
		
		this.joinedDate = joinedDate;
		
		this.followers = Collections.emptySet();
		
		this.setting = Setting.defaultSetting();	
		this.mailBox = MailBox.emptyMailBox();
		this.avatar = ImageGroup.defaultImageGroup();
		this.contribution = contribution;
		this.role = role;
		this.lasViewedNotification = new Date();
	}
	
	public User(){
		
	}*/
}

