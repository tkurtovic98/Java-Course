package hr.fer.zemris.java.tecaj_13.model.other;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Class used to store 
 * information user sent.
 * The information is then later inserted into the database
 * @author Tomislav Kurtović
 *
 */
public class Form {
	/**
	 * Maximum number of characters allowed in name
	 */
	private final int NAME_LENGTH = 50;
	/**
	 * first name of user
	 */
	private String firstName;
	/**
	 * last name of user
	 */
	private String lastName;
	/**
	 * email of user
	 */
	private String email;
	/**
	 * password of user
	 */
	private String password;
	/**
	 * nickname of user
	 */
	private String nick;
	/**
	 * errors that occur
	 */
	private Map<String, String> errors = new HashMap<>();

	/**
	 * constructor
	 */
	public Form() {
	}
	/**
	 * Sets first name 
	 * @param firstName first name of user
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * Sets last name 
	 * @param lastName last name of user
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * Sets email  
	 * @param email email of user
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Sets password
	 * @param password password of user
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Sets nickname
	 * @param nick nickname of user
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	/**
	 * Sets error if error occured
	 * @param key field where error occured
	 * @param message message of error
	 */
	public void setError(String key, String message) {
		errors.put(key, message);
	}
	/**
	 * Gets nickname 
	 * @return nickname of user from form
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Checks if all form fields are of 
	 * valid format
	 * @return true if form is valid, false otherwise
	 */ 
	public boolean isValid() {
		errors.clear();

		// Validating first name
		validateNames(this.firstName, "firstName", "First name");

		// Validating last name
		validateNames(this.lastName, "lastName", "Last name");

		// Validating nickname
		validateNames(this.nick, "nick", "Nick");

		// Validating password
		if (password.isEmpty()) {
			this.errors.put("password", "Password can not be blank");
		}

		// Validating email
		if (this.email.isEmpty()) {
			errors.put("eMail", "Email can not be blank");
		} else {
			int l = this.email.length();
			int p = this.email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				errors.put("eMail", "EMail is not of valid format");
			}
		}

		return this.errors.size() > 0 ? false : true;
	}
	
	/**
	 * Checks if errors map has any errors
	 * @param key field of error
	 * @return true if the field contains erorrs, false otherwise
	 */
	public boolean containsError(String key) {
		return this.errors.containsKey(key);
	}
	/**
	 * Gets error message for the specified 
	 * field 
	 * @param key name of field
	 * @return error message
	 */
	public String getErrorMsg(String key) {
		return this.errors.get(key);
	}
	/**
	 * Validates names in form.
	 * It validates nick, firstName, lastName
	 * @param name field to validate
	 * @param errorKey field that has error
	 * @param errorMsg error message
	 */
	private void validateNames(String name, String errorKey, String errorMsg) {
		if (name.isEmpty()) {
			this.errors.put(errorKey, errorMsg + " can not be empty");
		}

		if (name.length() > NAME_LENGTH) {
			this.errors.put(errorKey, errorMsg + " can not be longer than " + NAME_LENGTH + " characters ");
		}
	}
	/**
	 * Creates new blog user from form 
	 * @return new blog user
	 */
	public BlogUser createBlogUser() {
		BlogUser user = new BlogUser();

		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);
		user.setEmail(this.email);
		user.setNick(this.nick);
		user.setPasswordHash(this.password);

		return user;
	}

}
