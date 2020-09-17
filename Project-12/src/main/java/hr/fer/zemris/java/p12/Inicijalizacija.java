package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Class used as listener that acts when the web application 
 * first starts 
 * @author Tomislav Kurtović
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	/**
	 * Upon initialization of the web application the method
	 * reads content in order to establish a connection to the database
	 * and then checks to see if the tables that are supposed to 
	 * be in it are there and whether they are empty or not. 
	 * The method then acts accordingly.
	 * It makes two polls which the user can later pick in the browser.
	 * One poll is a list of bands and another is a list of football teams
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String path = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
		
		List<String> lines = new ArrayList<>();
		try {
			lines = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			try {
				throw new FileNotFoundException("No file found!");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		
		if(lines.size() != 5) {
			throw new IllegalArgumentException("Files does not contain valid information!");
		}
		
		String host = lines.get(0).split("=")[1];
		String port = lines.get(1).split("=")[1];
		String name = lines.get(2).split("=")[1];
		String user = lines.get(3).split("=")[1];
		String password = lines.get(4).split("=")[1];
		
		String connectionURL = "jdbc:derby://"+host+":"+port+"/"+name;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		cpds.setUser(user);
		cpds.setPassword(password);
		cpds.setMinPoolSize(5);
		cpds.setMaxPoolSize(20);
		cpds.setAcquireIncrement(5);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		Connection connection = null;
		try {
			connection = cpds.getConnection();
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet rSet = metaData.getTables(null, null, "POLLS", new String[] {"TABLE","VIEW"});
			long firstPollID = 0;
			long secondPollID = 0;
			try {
				if(!rSet.next()) {
					String create = "CREATE TABLE Polls"
							+ " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
							+ "title VARCHAR(150) NOT NULL,message CLOB(2048) NOT NULL)";
					createTable(connection, create);
				}
				
				if(checkIfEmpty(connection, "POLLS")) {
					String insert = "INSERT INTO Polls(title, message) values (?,?)";
					
					firstPollID = populatePollAndRetrievePollID(
							connection,
							insert,
							"Glasanje za omiljeni bend",
							"Koji Vam je bend od idućih najdraži? Glasajte!"
							);
					
					if(firstPollID == -1) {
						throw new IllegalArgumentException("Error occured while retrieving poll id!");
					}
					
					secondPollID = populatePollAndRetrievePollID(
							connection,
							insert,
							"Glasanje za omiljen nogometni klub",
							"Koji Vam je nogometni klub najdraži? Glasajte!"
							);
				}
				
				rSet = metaData.getTables(null, null, "POLLOPTIONS", new String[] {"TABLE","VIEW"});
				if(!rSet.next()) {
					String create = "CREATE TABLE PollOptions"+
							"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"+
							"optionTitle VARCHAR(100) NOT NULL,"+
							"optionLink VARCHAR(150) NOT NULL,"+
							"pollID BIGINT,"+
							"votesCount BIGINT,"+
							"FOREIGN KEY (pollID) REFERENCES Polls(id))";
					createTable(connection, create);
				}
				if(checkIfEmpty(connection, "POLLOPTIONS")) {
					List<String> candidates = Files.readAllLines(
							Paths.get(sce.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt")));
					
					String insert = "INSERT INTO PollOptions(optionTitle, optionLink,pollID,votesCount) values (?,?,?,?)";
					for(String candidate : candidates) {
						String[] split = candidate.split("\t");
						populatePollOptions(connection, insert, split[1], split[2], firstPollID,0);
					}
					
					candidates = Files.readAllLines(
							Paths.get(sce.getServletContext().getRealPath("/WEB-INF/glasanje-definicija2.txt")));
					
					for(String candidate : candidates) {
						String[] split = candidate.split("\t");
						populatePollOptions(connection, insert, split[1], split[2], secondPollID,0);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try{ rSet.close();} catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Used to check whether the table contains rows or not 
	 * @param connection connection to the database 
	 * @param table name of table to check 
	 * @return true if table contains no rows, false otherwise 
	 */
	private boolean checkIfEmpty(Connection connection, String table) {
		String count = "SELECT COUNT(*) FROM "+ table;
		ResultSet rSet = null;
		int result = 0;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(count);
			rSet = preparedStatement.executeQuery();
			try {
				rSet.next();
				result = rSet.getInt(1);
			} finally {
				try {
					rSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result<1 ? true : false;
	}

	/**
	 * Used to populate the table of {@link PollOption}s if the table 
	 * was just created or if it is empty 
	 * @param connection connection to the database 
	 * @param insert sql statement used to insert values into the table 
	 * @param optionTitle title of the poll option 
	 * @param optionLink link of the poll option 
	 * @param pollID id of the poll option 
	 * @param initVotes initial votes of the poll option (0 or null in this case)
	 */
	private void populatePollOptions(Connection connection, String insert, String optionTitle, String optionLink,
			long pollID, long initVotes) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(insert);
			preparedStatement.setString(1, optionTitle);
			preparedStatement.setString(2, optionLink);
			preparedStatement.setLong(3, pollID);
			preparedStatement.setLong(4, initVotes);
			preparedStatement.executeUpdate();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Used to populate the table of {@link Poll}s if the table 
	 * was just created or if it is empty 
	 * @param connection connection to the database 
	 * @param insert sql statement used to insert values into the table 
	 * @param title title of the poll  
	 * @param message message of the poll  
	 * @return id of the created poll 
	 */
	private long populatePollAndRetrievePollID(Connection connection,String insert, String title, String message) {
		PreparedStatement preparedStatement = null;
		ResultSet rSet = null;
		long generatedKey = -1;
		try {
			preparedStatement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, title);
			preparedStatement.setString(2, message);
		    preparedStatement.executeUpdate();
			rSet = preparedStatement.getGeneratedKeys();
			
			try {
				if(rSet != null && rSet.next()) {
					generatedKey = rSet.getLong(1);
				}
			} finally {
				try {
					rSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return generatedKey;
	}

	/**
	 * Used to create a table
	 * @param connection connection to the database
	 * @param create sql statement to create table 
	 */
	private void createTable(Connection connection, String create)  {
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(create);
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}