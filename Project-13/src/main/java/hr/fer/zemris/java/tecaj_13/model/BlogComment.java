package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class that represents user blog comments 
 * that are stored in the database
 * @author Tomislav Kurtović
 *
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/**
	 * Comment id
	 */
	private Long id;
	/**
	 * Comment blog entry
	 */
	private BlogEntry blogEntry;
	/**
	 * Users email who posted the comment 
	 */
	private String usersEMail;
	/**
	 * comment message
	 */
	private String message;
	/**
	 * posted on date
	 */
	private Date postedOn;
	
	/**
	 * Retrieves id of comment
	 * @return id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	/**
	 * Sets id of comment
	 * @param id id of comment
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Blog entry that holds this comment
	 * @return blog entry 
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	/**
	 * Sets blog entry for this comment
	 * @param blogEntry blog entry 
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}
	/**
	 * Gets email of user who posted comment
	 * @return user email
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}
	/**
	 * Sets user email who posted comment
	 * @param usersEMail user email
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}
	/**
	 * Gets message of comment
	 * @return comment message
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}
	/**
	 * Sets message of comment 
	 * @param message message of comment
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * Gets date the comment was posted
	 * @return date of comment post
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}
	/**
	 * Sets date comment was posted
	 * @param postedOn
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}