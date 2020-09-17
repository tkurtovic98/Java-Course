package hr.fer.zemris.java.hw17.trazilica;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that is used as a storage 
 * for all the idf values for the words in the 
 * {@link Vocabulary}
 * @author Tomislav Kurtović
 *
 */
public class IdfVector {
	/**
	 * Number of documents in cluster of documents 
	 */
	private final int NUMBER_OF_DOCUMENTS = Cluster.getCluster().documentsInCluster().size();
	
	/**
	 * Map that stores idf values for words
	 */
	private Map<String, Double> idfVectors = new HashMap<>();
	/**
	 * Instance of {@link IdfVector}
	 */
	private static IdfVector instance = new IdfVector();
	
	/**
	 *  Constructor
	 */
	private IdfVector() {
	}
	
	/**
	 * Returns the instance of the {@link IdfVector}
	 * @return
	 */
	public static IdfVector getIdfVector() {
		return instance;
	}
	
	/**
	 * Adds new word-value pairs to the {@link IdfVector}
 	 * @param word word to add
	 * @param frequency number of documents that contain the word
	 */
	public void addWord(String word, int frequency) {
		double log = Math.log((double)NUMBER_OF_DOCUMENTS/frequency);
		idfVectors.put(word, log);
	}
	
	/**
	 * Returns value of idf for given word
	 * @param word 
	 * @return value of idf for word
	 */
	public Double getIdf(String word) {
		return idfVectors.get(word);
	}
}
