package hr.fer.zemris.java.hw17.trazilica;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import hr.fer.zemris.java.hw17.trazilica.commands.SearchCommandException;

/**
 * Class used as a storage for 
 * the query results to later be reused by other
 * commands
 * @author Tomislav Kurtović
 *
 */
public class ResultContainer {
	/**
	 * Maximum number of allowed results 
	 */
	private int MAX_RESULTS ;
	/**
	 * Instance of this class 
	 */
	private static ResultContainer container = new ResultContainer();
	/**
	 * list of results 
	 */
	private List<Entry<Path,Double>> results = new ArrayList<>();
	/**
	 * constructor
	 */
	private ResultContainer() {}
	/**
	 * Returns reference to instance of this class
	 * @return instance of {@link ResultContainer}
	 */
	public static ResultContainer getRC() {
		return container;
	}
	
	/**
	 * Sets the search query results.
	 * It only add the top 10 results
	 * @param results list of all results 
	 */
	public void setResults(Set<Entry<Path,Double>> results) {
		Objects.requireNonNull(results, "Results can not be null");
		MAX_RESULTS = results.size();
		this.results.addAll(results);
	}
	/**
	 * Prints results to screen.
	 * @throws SearchCommandException if no results are stored 
	 */
	public void printResults() throws SearchCommandException {
		checkResult();
		for(int i = 0; i < MAX_RESULTS; i ++) {
			if(i > results.size() - 1) break;
//			System.out.println("["+i+"]"+ results.get(i).getValue() +" "+ results.get(i).getKey());
			System.out.printf("[%d] (%.4f) %s%n", i, results.get(i).getValue(), results.get(i).getKey());
		}
	}
	/**
	 * Returns the path of the specified result from the result list
 	 * @param index position of result in result list
	 * @return path of result 
	 * @throws SearchCommandException if result list is not set or if the index is not legal
	 */
	public Path getResultPath(int index) throws SearchCommandException {
		checkResult();
		if(index < 0 || index >= MAX_RESULTS) {
			throw new SearchCommandException("Invalid index found!");
		}
		
		return results.get(index).getKey();
	}
	/**
	 * Checks if the result list is set 
	 * @throws SearchCommandException if result list is not set 
	 */
	private void checkResult() throws SearchCommandException {
		if(results.isEmpty()) {
			throw new SearchCommandException("No results found");
		}
	}
	
}
