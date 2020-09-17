package hr.fer.zemris.java.hw14.model;

/**
 * Class that represents a poll which 
 * the user can choose 
 * @author Tomislav Kurtović
 *
 */
public class Poll {

	/**
	 * Id of the poll
	 */
	private long pollID;
	/**
	 * title of the poll
	 */
	private String title;
	/**
	 * message of the poll
	 */
	private String message;
	
	/**
	 * Constructor of the poll
	 * @param pollID id of poll
	 * @param title title of poll
	 * @param message message of poll
	 */
	public Poll(long pollID, String title, String message) {
		super();
		this.pollID = pollID;
		this.title = title;
		this.message = message;
	}

	/**
	 * Retrieves the id of the poll
	 * @return id of poll
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Retrieves the title of the poll
	 * @return title of poll
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Retrieves the message of the poll
	 * @return message of poll
	 */
	public String getMessage() {
		return message;
	}
}
