package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;
import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna
 * implementacija očekuje da joj veza stoji na raspolaganju preko
 * {@link SQLConnectionProvider} razreda, što znači da bi netko prije no što
 * izvođenje dođe do ove točke to trebao tamo postaviti. U web-aplikacijama
 * tipično rješenje je konfigurirati jedan filter koji će presresti pozive
 * servleta i prije toga ovdje ubaciti jednu vezu iz connection-poola, a po
 * zavrsetku obrade je maknuti.
 * 
 * @author marcupic
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection connection = SQLConnectionProvider.getConnection();
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM POLLS ORDER BY ID");
			try {
				ResultSet rSet = preparedStatement.executeQuery();

				try {
					while (rSet.next()) {
						String title = rSet.getString(2);
						String message = rSet.getString(3);
						long pollId = rSet.getLong(1);
						Poll poll = new Poll(pollId, title, message);
						polls.add(poll);
					}
				} finally {
					try {
						rSet.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Unable to retrieve information from database", ex);
		}
		return polls;
	}

	@Override
	public List<PollOption> getPollOptions(long id) throws DAOException {
		List<PollOption> pollOptions = new ArrayList<>();
		Connection connection = SQLConnectionProvider.getConnection();
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM POLLOPTIONS  WHERE pollID=?");
			preparedStatement.setLong(1, Long.valueOf(id));
			try {
				ResultSet rSet = preparedStatement.executeQuery();
				try {
					while (rSet != null && rSet.next()) {
						PollOption poll = new PollOption(rSet.getLong(1), rSet.getString(2), rSet.getString(3));
						poll.setVotesCount(rSet.getLong(5));
						pollOptions.add(poll);
					}
				} finally {
					try {
						rSet.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Unable to retrieve information from database", ex);
		}
		return pollOptions;
	}

	@Override
	public Poll getPoll(long id) throws DAOException {
		Poll poll = null;
		Connection connection = SQLConnectionProvider.getConnection();
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM POLLS where ID = ?");
			preparedStatement.setLong(1, id);
			try {
				ResultSet rSet = preparedStatement.executeQuery();
				try {
					while (rSet.next()) {
						poll = new Poll(rSet.getLong(1), rSet.getString(2), rSet.getString(3));
					}
				} finally {
					try {
						rSet.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Unable to retrieve information from database", ex);
		}
		return poll;
	}

	@Override
	public void updateVotesCount(long pollOptionsID) throws DAOException {
		Connection connection = SQLConnectionProvider.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE POLLOPTIONS set votesCount = CASE "
					+ "WHEN votesCount IS NULL OR votesCount = 0 THEN 1 WHEN votesCount >= 1 THEN (votesCount + 1) END WHERE id = ?");
			preparedStatement.setLong(1, pollOptionsID);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception ex) {
			throw new DAOException("ERROR while updating votes count!", ex);
		}
	}

}