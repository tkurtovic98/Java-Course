package hr.fer.zemris.java.p12.dao;


import java.util.List;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {

	/**
	 * Used to retrieve a list of {@link Poll}s from
	 * the database   
	 * @return list of {@link PollOption}s
	 * @throws DAOException if something goes wrong while retrieving data
	 */
	public List<Poll> getPolls() throws DAOException;
	
	/**
	 * Used to retrieve a list of {@link PollOption}s from
	 * the database given the current poll the user is working with  
	 * @param id id of current poll
	 * @return list of {@link PollOption}s
	 * @throws DAOException if something goes wrong while retrieving data
	 */
	public List<PollOption> getPollOptions(long id) throws DAOException;
	
	/**
	 * Used to retrieve a  {@link Poll} from
	 * the database given the current poll the user is working with  
	 * @param id id of current poll
	 * @return current {@link Poll}
	 * @throws DAOException if something goes wrong while retrieving data
	 */
	public Poll getPoll(long id) throws DAOException;

	/**
	 * Used to update the votes count in the database given 
	 * the polloption id to update 
	 * @param pollOptionsID id of selected polloption
	 * @throws DAOException if something goes wrong while updating the votes count
	 */
	public void updateVotesCount(long pollOptionsID) throws DAOException;

}