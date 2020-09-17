package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class used to represent blog user
 * with its fields that are 
 * stored in the database
 * @author Tomislav Kurtović
 *
 */
@Entity
@Table(name = "blog_users") 
public class BlogUser {
	/**
	 * ID of user
	 */
	private long id;
	/**
	 * firstName of user
	 */
	private String firstName;
	/**
	 * lastName of user
	 */
	private String lastName;
	/**
	 * nick of user
	 */
	private String nick;
	/**
	 * email of user
	 */
	private String email;
	/**
	 * password of user
	 */
	private String passwordHash;
	/**
	 * user entries
	 */
	private List<BlogEntry> userEntries = new ArrayList<>();
	/**
	 * constructor
	 */
	public BlogUser() {};
	
	/**
	 * Returns user id
	 * @return user id
	 */
	@Id @GeneratedValue
	public long getId() {
		return id;
	}

	/**
	 * Returns user first name
	 * @return user first name
	 */
	@Column(nullable = false, length = 50)
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Returns user last name
	 * @return user last name
	 */
	@Column(nullable = false, length = 50)
	public String getLastName() {
		return lastName;
	}
	/**
	 * Returns user nick
	 * @return user nick
	 */
	@Column(nullable = false, length = 50, unique = true)
	public String getNick() {
		return nick;
	}
	/**
	 * Returns user email
	 * @return user email
	 */
	@Column(nullable = false, length = 150)
	public String getEmail() {
		return email;
	}
	/**
	 * Returns user password
	 * @return user password
	 */
	@Column(nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}
	/**
	 * Returns user entries
	 * @return user entries
	 */
	@OneToMany(mappedBy="creator")
	public List<BlogEntry> getUserEntries() {
		return userEntries;
	}
	/**
	 * Sets user entries
	 * @param userEntries list of user entries
	 */
	public void setUserEntries(List<BlogEntry> userEntries) {
		this.userEntries = userEntries;
	}
	/**
	 * @param id id of user
	 * Sets user id
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * Sets user first name
	 * @param firstName firstName of user
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * Sets user last name
	 * @param lastName last name of user
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * Sets user nick
	 * @param nick nick of user
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	/**
	 * Sets user email
	 * @param email of user
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Sets user password
	 * @param passwordHash hashed password of user
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}
