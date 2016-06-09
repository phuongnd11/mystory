package com.inspireon.mystory.model.domain.story;

import java.text.Normalizer;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.inspireon.mystory.exception.UserAlreadyVoteStoryException;
import com.inspireon.mystory.exception.VoterNotFoundInStory;
import com.inspireon.mystory.model.domain.comment.Comment;
import com.inspireon.mystory.model.domain.image.ImageGroup;
import com.inspireon.mystory.model.domain.shared.Entity;
import com.inspireon.mystory.model.domain.shared.Report;
import com.inspireon.mystory.model.domain.shared.Voter;

@Document(collection="stories")
public class Story extends Entity<Story>{
    
    private static final int  TITLE_MAX_LENGTH = 200;

	private String title;
    
    private String content;
    
    private Date submittedDate;
    
    private String author;
    
    private String originalStoryId;
    
    private ImageGroup featuredImage;
    
    private int views;
    
    private int commentCount;
    
    private Set<Voter> upVoters;
    
    private Set<Voter> downVoters;
    
    private int point;
    
    private Tag tag;

    private String lastEditedBy;
    
    private Date lastEditedTime;
    
    private List<Version> versions;
    
    private List<Report> reports;
    
    private Date lastCommentedTime;

    private Status status;
    
    private String friendlyUrl;
    // ---------------------------------- Constructors --------------------------------- //
	public Story(String author, String title, String content, 
			String originalStoryId, ImageGroup featuredImage, Tag tag) {
		Validate.notEmpty(author, "Author is required");
		Validate.notEmpty(title, "Title is required");
		Validate.isTrue(title.length() <= TITLE_MAX_LENGTH);
		Validate.notEmpty(content, "Content is required");
		Validate.notNull(tag, "Tag is required");
		
		this.author = author;
		this.title = title;
		this.content = content;
		
		this.originalStoryId = originalStoryId;
		this.featuredImage = (featuredImage == null)? ImageGroup.defaultFeturedImageGroup() : featuredImage;
		this.tag = tag;
		
		this.submittedDate = new Date();
		this.lastEditedTime = submittedDate;
		this.lastEditedBy = author;
		
		this.lastCommentedTime = new Date();
		
		this.status = Status.ACTIVE;
		this.views = 0;
		this.commentCount = 0;
		
		this.point = 0;
		this.upVoters = Collections.<Voter>emptySet();
		this.downVoters = Collections.<Voter>emptySet();
		this.versions = Collections.<Version>emptyList();
		this.reports = Collections.<Report>emptyList();
	}

	// ---------------------------------- Business logic --------------------------------- //
	public String title() {
		return title;
	}

	public String content() {
		return content;
	}

	public Date submittedDate() {
		return submittedDate;
	}

	public String author() {
		return author;
	}

	public String originalStoryId() {
		return originalStoryId;
	}

	public ImageGroup featuredImage() {
		return featuredImage;
	}

	public int views() {
		return views;
	}

	public int commentCount() {
		return commentCount;
	}

	public Set<Voter> upVoters() {
		return upVoters;
	}

	public Set<Voter> downVoters() {
		return downVoters;
	}

	public Tag tag() {
		return tag;
	}
	
	public String friendlyUrl() {
		return friendlyUrl;
	}

	public void setFriendlyUrl(String friendlyUrl) {
		this.friendlyUrl = friendlyUrl;
	}

	public Date lastCommentedTime() {
		return lastCommentedTime;
	}
	
	public String lastEditedBy() {
		return lastEditedBy;
	}

	public Date lastEditedTime() {
		return lastEditedTime;
	}

	public List<Version> versions() {
		return versions;
	}

	public List<Report> reports() {
		return reports;
	}

	public Status status() {
		return status;
	}
	
	public void updateTitle(String newTitle) {
		Validate.notEmpty(newTitle, "Title is required");
		Validate.isTrue(newTitle.length() <= TITLE_MAX_LENGTH);
		this.title = newTitle;
	}

	public void updateContent(String newContent) {
		Validate.notEmpty(newContent, "Content is required");
		this.content = newContent;
	}

	public void updateOriginalStoryId(String newOriginalStoryId) {
		this.originalStoryId = newOriginalStoryId;
	}

	public void updateTags(Tag newTag) {
		Validate.notNull(newTag, "Tag is required");
		this.tag = newTag;
	}

	public void updateLastCommentedTime(Date lastCommentedTime) {
		this.lastCommentedTime = lastCommentedTime;
	}

	public void updateLastEditedBy(String lastEditedBy) {
		this.lastEditedBy = lastEditedBy;
	}

	public void updateLastEditedTime(Date lastEditedTime) {
		this.lastEditedTime = lastEditedTime;
	}

	public void updateFeaturedImage(ImageGroup featuredImage) {
		this.featuredImage = featuredImage;
	}
	
	public void addVersion(Version newVersion) {
		this.versions.add(newVersion);
	}

	public void close() {
		this.status = Status.CLOSED;
	}
	
	public void view() {
		this.views++;
	}
	
	public boolean isAlreadyVotedUpBy(String username) {
		return upVoters.contains(new Voter(username));
	}
	
	public boolean isAlreadyVotedDownBy(String username) {
		return downVoters.contains(new Voter(username));
	}
	
	public void upVote(String newVoter) throws UserAlreadyVoteStoryException {
		if (isAlreadyVotedUpBy(newVoter)) {
			throw new UserAlreadyVoteStoryException(newVoter, this.id());
		} else {
			Voter upVoter = new Voter(newVoter);
			this.upVoters.add(upVoter);
			this.point++;
		}
	}
	
	public void downVote(String newVoter) throws UserAlreadyVoteStoryException {
		if (isAlreadyVotedDownBy(newVoter)) {
			throw new UserAlreadyVoteStoryException(newVoter, this.id());
		} else {
			Voter downVoter = new Voter(newVoter);
			this.downVoters.add(downVoter);
			this.point--;
		}
	}
	
	public void unUpVote(String voter) throws VoterNotFoundInStory {
		if (isAlreadyVotedUpBy(voter)) {
			Voter unUpVoter = new Voter(voter);
			this.upVoters.remove(unUpVoter);
			this.point--;
		} else {
			throw new VoterNotFoundInStory(voter, this.id());
		}
	}
	
	public void unDownVote(String voter) throws VoterNotFoundInStory {
		if (isAlreadyVotedDownBy(voter)) {
			Voter unDownVoter = new Voter(voter);
			this.downVoters.remove(unDownVoter);
			this.point++;
		} else {
			throw new VoterNotFoundInStory(voter, this.id());
		}
	}
	
	public int point() {
		return this.point;
	}
	
	public long hotScore() {
		
		double pointScore = log(Math.max(Math.abs(this.point), 1), 2);
		
		short sign;
		
		if(this.point < 0) {
			sign = -1;
		} else {
			sign = 1;
		}
		
		long seconds = this.submittedDate().getTime()/1000 - 1134028003;
		
		return Math.round((pointScore + (sign * seconds)/(double)45000) * 1000);
	}
	
	private double log(int x, int base) {
	    return Math.log(x) / Math.log(base);
	}
	
	public int controversialScore() {
		return (this.upVoters().size() + this.downVoters().size()) / Math.max(Math.abs(this.point()), 1);
	}
	
	public void addComment(Comment comment) {
		this.lastCommentedTime = comment.submittedDate();
		this.commentCount++;
	}
	
	// -------------------------------- Common method ---------------------------------//
	@Override
	public boolean sameIdentityAs(Story other) {
		return other != null && this.id().equals(other.id());
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final Story other = (Story) object;
		return sameIdentityAs(other);
	}

	/**
	 * @return Hash code of id
	 */
	@Override
	public int hashCode() {
		return id().hashCode();
	}

	@Override
	public String toString() {
		return "Story [title=" + title + ", author=" + author + ", submittedDate=" + submittedDate + "]";
	}
	
	/*public Story(String title, String content, Date submittedDate, String author, String originalStoryId, int views,
			int commentCount, Set<Voter> upVoters, Set<Voter> downVoters, int point, Tag tag){
		Validate.notEmpty(author, "Author is required");
		Validate.notEmpty(title, "Title is required");
		Validate.isTrue(title.length() <= TITLE_MAX_LENGTH);
		Validate.notEmpty(content, "Content is required");
		Validate.notNull(tag, "Tag is required");
		
		this.author = author;
		this.title = title;
		this.content = content;
		
		this.originalStoryId = originalStoryId;
		this.tag = tag;
		
		this.submittedDate = submittedDate;
		this.lastEditedTime = submittedDate;
		this.lastEditedBy = author;
		
		this.status = Status.ACTIVE;
		this.views = views;
		this.commentCount = commentCount;
		this.point = point;
		
		this.upVoters = upVoters;
		this.downVoters = downVoters;
		this.versions = Collections.<Version>emptyList();
		this.reports = Collections.<Report>emptyList();
		
	}
	
	public Story(){
		
	}*/
}
