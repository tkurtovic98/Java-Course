package hr.fer.zemris.java.hw14.model;

/**
 * Class that represents the different options 
 * a certain poll has 
 * @author Tomislav Kurtović
 *
 */
public class PollOption {
	/**
	 * id of poll option
	 */
	private long pollID;
	/**
	 * name of poll option
	 */
	private String name;
	/**
	 * link of poll option
	 */
	private String link;
	/**
	 * votes count of poll option
	 */
	private long votesCount;
	
	/**
	 * Constructor of poll option
	 * @param pollID id of poll option
	 * @param name name of poll option 
	 * @param link link of poll option 
	 */
	public PollOption(long pollID, String name, String link) {
		super();
		this.pollID = pollID;
		this.name = name;
		this.link = link;
	}
	
	/**
	 * Retrieves the id of the poll option
	 * @return id of poll option 
	 */
	public long getPollID() {
		return pollID;
	}
	/**
	 * Retrieves the name of the poll option
	 * @return name of poll option 
	 */
	public String getName() {
		return name;
	}
	/**
	 * Retrieves the link of the poll option
	 * @return link of poll option 
	 */
	public String getLink() {
		return link;
	}
	
	/**
	 * Retrieves the votes count of the poll option
	 * @return votes count of poll option 
	 */
	public long getVotesCount() {
		return votesCount;
	}
	
	/**
	 * Sets the votes count of the poll option
	 * @param votesCount votes count of the poll
	 */
	public void setVotesCount(Long votesCount) {
		if(votesCount == null) {
			votesCount = (long) 0;
		}
		this.votesCount = votesCount.longValue();
	}
}


