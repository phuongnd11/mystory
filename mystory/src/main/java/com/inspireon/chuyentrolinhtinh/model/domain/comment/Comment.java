package com.inspireon.chuyentrolinhtinh.model.domain.comment;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.inspireon.chuyentrolinhtinh.exception.UserAlreadyVoteCommentException;
import com.inspireon.chuyentrolinhtinh.exception.VoterNotFoundInComment;
import com.inspireon.chuyentrolinhtinh.model.domain.shared.Entity;
import com.inspireon.chuyentrolinhtinh.model.domain.shared.Report;
import com.inspireon.chuyentrolinhtinh.model.domain.shared.Voter;

@Document(collection="comments")
public class Comment extends Entity<Comment>{

	private String content;
    
    private Date submittedDate;
    
    private String author;
    
    private String storyId;
    
    private Set<Voter> upVoters;
    
    private Set<Voter> downVoters;
    
    private int point;

    private String lastEditedBy;
    
    private Date lastEditedTime;
    
    private List<Version> versions;
    
    private List<Report> reports;
    
    private Status status;
    
    // ---------------------------------- Constructors --------------------------------- //
	public Comment(String content, String author, String storyId) {
		Validate.notEmpty(author, "Author is required");
		Validate.notEmpty(storyId, "storyId is required");
		Validate.notEmpty(content, "Content is required");
		
		this.content = content;
		this.author = author;
		this.storyId = storyId;
		
		this.submittedDate = new Date();
		this.lastEditedTime = submittedDate;
		this.lastEditedBy = author;
		
		this.status = Status.ACTIVE;
		
		this.upVoters = Collections.<Voter>emptySet();
		this.downVoters = Collections.<Voter>emptySet();
		this.versions = Collections.<Version>emptyList();
		this.reports = Collections.<Report>emptyList();
	}

	// ---------------------------------- Business logic --------------------------------- //
	public String content() {
		return content;
	}

	public Date submittedDate() {
		return submittedDate;
	}

	public String author() {
		return author;
	}

	public String storyId() {
		return storyId;
	}

	public Set<Voter> upVoters() {
		return upVoters;
	}

	public Set<Voter> downVoters() {
		return downVoters;
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

	public void addVersion(Version newVersion) {
		this.versions.add(newVersion);
	}

	public void updateContent(String content) {
		Validate.notEmpty(content, "Content is required");
		this.content = content;
	}

	public void updateLastEditedBy(String lastEditedBy) {
		Validate.notEmpty(lastEditedBy, "lastEditedBy is required");
		this.lastEditedBy = lastEditedBy;
	}

	public void updateLastEditedTime(Date lastEditedTime) {
		this.lastEditedTime = lastEditedTime;
	}
	
	public boolean isAlreadyVotedUpBy(String username) {
		return upVoters.contains(new Voter(username));
	}
	
	public boolean isAlreadyVotedDownBy(String username) {
		return downVoters.contains(new Voter(username));
	}
	
	public int point() {
		return point;
	}

	public void upVote(String newVoter) throws UserAlreadyVoteCommentException {
		
		if (isAlreadyVotedUpBy(newVoter)) {
			throw new UserAlreadyVoteCommentException(newVoter, this.id());
		} else {
			Voter upVoter = new Voter(newVoter);
			this.upVoters.add(upVoter);
			this.point++;
		}
	}
	
	public void downVote(String newVoter) throws UserAlreadyVoteCommentException {
		
		if (isAlreadyVotedDownBy(newVoter)) {
			throw new UserAlreadyVoteCommentException(newVoter, this.id());
		} else {
			Voter downVoter = new Voter(newVoter);
			this.downVoters.add(downVoter);
			this.point--;
		}
	}
	
	public void unUpVote(String voter) throws VoterNotFoundInComment {
		
		if (isAlreadyVotedUpBy(voter)) {
			Voter unUpVoter = new Voter(voter);
			this.upVoters.remove(unUpVoter);
			this.point--;
		} else {
			throw new VoterNotFoundInComment(voter, this.id());
		}
	}
	
	public void unDownVote(String voter) throws VoterNotFoundInComment {
		
		if (isAlreadyVotedDownBy(voter)) {
			Voter unDownVoter = new Voter(voter);
			this.downVoters.remove(unDownVoter);
			this.point++;
		} else {
			throw new VoterNotFoundInComment(voter, this.id());
		}
	}
	
	// -------------------------------- Common method ---------------------------------//
	@Override
	public boolean sameIdentityAs(Comment other) {
		return other != null && this.id().equals(other.id());
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final Comment other = (Comment) object;
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
		return "Comment [content=" + content + ", author=" + author + ", submittedDate=" + submittedDate + "]";
	}
	
	/*public Comment(String content, Date submittedDate, String author, String storyId, Set<Voter> upVoters, Set<Voter> downVoters,
			int point){
		
		Validate.notEmpty(author, "Author is required");
		Validate.notEmpty(storyId, "storyId is required");
		Validate.notEmpty(content, "Content is required");
		
		this.content = content;
		this.submittedDate= submittedDate;
		this.author = author;
		this.storyId = storyId;
		this.point = point;

		
		this.submittedDate = submittedDate;
		this.lastEditedTime = submittedDate;
		this.lastEditedBy = author;
		
		this.status = Status.ACTIVE;
		
		this.upVoters = upVoters;
		this.downVoters = downVoters;
		this.versions = Collections.<Version>emptyList();
		this.reports = Collections.<Report>emptyList();
		
	}
	
	public Comment(){
		
	}*/
}
