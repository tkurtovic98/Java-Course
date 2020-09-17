package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class that represents blog entry of user 
 * @author Tomislav Kurtović
 *
 */
@NamedQueries({
	@NamedQuery(name="BlogEntry.upit1",query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when")
})
@Entity
@Table(name="blog_entries")
@Cacheable(false)
public class BlogEntry {
	/**
	 * ID of entry
	 */
	private Long id;
	/**
	 * comments of entry
	 */
	private List<BlogComment> comments = new ArrayList<>();
	/**
	 * date of entry
	 */
	private Date createdAt;
	/**
	 * date last modified of entry
	 */
	private Date lastModifiedAt;
	/**
	 * title of entry
	 */
	private String title;
	/**
	 * text of entry
	 */
	private String text;
	/**
	 * creator of entry
	 */
	private BlogUser creator;
	
	/**
	 * Returns id of entry
	 * @return
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	/**
	 * Sets id of entry
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Gets entry comments 
	 * @return comments of entry
	 */
	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	/**
	 * Sets comments of entry
	 * @param comments comments of entry
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}
	/**
	 * Returns date created 
	 * @return date created
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}
	/**
	 * Sets creation date
	 * @param createdAt creation date
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	/**
	 * Returns date last modified
	 * @return date last modified
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}
	/**
	 * Gets creator of entry
	 * @return creator of entry
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogUser getCreator() {
		return creator;
	}
	/**
	 * Sets creator of entry
	 * @param user creator of entry
	 */
	public void setCreator(BlogUser user) {
		this.creator = user;
	}
	/**
	 * Sets last modified at
	 * @param lastModifiedAt date last modified
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
	/**
	 * Returns title of entry
	 * @return title of entry
	 */
	@Column(length=200,nullable=false)
	public String getTitle() {
		return title;
	}
	/**
	 * Sets title of entry
	 * @param title title of entry
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Gets text of entry
	 * @return text of entry
	 */
	@Column(length=4096,nullable=false)
	public String getText() {
		return text;
	}
	/**
	 * Sets text of entry  
	 * @param text entry text
	 */
	public void setText(String text) {
		this.text = text;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}