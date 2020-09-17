package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface that is used in communicating 
 * with the database 
 * and retrieve various values 
 * @author Tomislav Kurtović
 *
 */
public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Used to register new user to the blog
	 * if the user does not already exist in the
	 * database
	 * @param user user to add to database
	 *  @throws DAOException if there is an error while adding user 
	 */
	public void registerUser(BlogUser user) throws DAOException;

	/**
	 * Used to login user into the blog 
	 * if the user with provided nick 
	 * and password exists in the database
	 * @param nick nickname of user 
	 * @param password password of user
	 * @return blog user from database if exists, null otherwise
	 */
	public BlogUser loginUser(String nick, String password) ;

	/**
	 * Used to retrieve user from database
	 * given the nickname of the user to retrieve
	 * @param nick nickname of user to retrieve
	 * @return blog user from database if exists, null otherwise
	 */
	public BlogUser getUser(String nick);

	/**
	 * Used to insert new entry into the
	 * database and adding its creator to 
	 * the entry if the creator is registered,
	 * otherwise an exception is thrown
	 * @param blogEntry blog entry to add
	 * @param creatorId creator of the blog entry
	 * @throws DAOException if creator does not exist in database 
	 */
	public void insertNewEntry(BlogEntry blogEntry, Long creatorId) throws DAOException;

	/**
	 * Used to get list of registered blog users 
	 * from the database registered users exist,
	 * empty list if not
	 * @return list of registered blog users
	 */
	public List<BlogUser> getRegisteredUsers();

	/**
	 * Used to update blog entry in database
	 * when user edits the entry
	 * @param blogEntry entry to update
	 * @throws DAOException if something goes wrong while updating entry
	 */
	public void updateBlogEntry(BlogEntry blogEntry) throws DAOException;

	/**
	 * Used to set the user entries from the database
	 * of the user whoose id is provided 
	 * @param id id of user whoose blog entries should be set
	 */
	public void setUserEntries(Long id);

	/**
	 * Used to get the user entries from the database
	 * of the user whoose id is provided 
	 * @param id id of user whoose blog entries should be retrieved
	 */
	public List<BlogEntry> getUserEntries(Long id);

	/**
	 * Used to get comments of the passed 
	 * blog entry 
	 * @param entry blog entry whoose comments should be set
	 * @return list of blog comments for the specified entry
	 */
	public List<BlogComment> getEntryComments(BlogEntry entry);
	/**
	 * Inserts comment into database
	 * @param comment comment to insert
	 * @return 
	 */
	public void setComment(BlogComment comment);
	
}