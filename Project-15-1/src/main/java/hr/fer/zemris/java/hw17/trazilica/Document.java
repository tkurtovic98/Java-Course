package hr.fer.zemris.java.hw17.trazilica;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents document
 * @author Tomislav Kurtović
 *
 */
public class Document {
	/**
	 * Map that holds information about frequency of words in document
	 */
	private Map<String, Integer> freq;
	/**
	 * Path to document on disk
	 */
	private Path pathToDoc;
	
	/**
	 * Constructor of document
	 * @param path path to document
	 */
	public Document(Path path) {
		freq = new HashMap<>();
		pathToDoc = path;
	}
	/**
	 * Returns number of times a word is repeated in document text
	 * @param word word to find number of occurrences
	 * @return number of occurrences 
	 */
	public Integer getWordFrequency(String word) {
		return freq.get(word);
	}
	
	/**
	 * Used to increment frequency of word in document
	 * @param word word whose frequency is to be incremented
	 */
	public void incrementWordFrequency(String word) {
		freq.put(word, freq.containsKey(word) ? freq.get(word) + 1 : 1);
		
	}
	/**
	 * Returns document path
	 * @return path of document
	 */
	public Path getPathToDoc() {
		return pathToDoc;
	}
	/**
	 * Returns all the important words and their frequencies in the text
	 * @return collection of word and frequencies
	 */
	public Map<String, Integer> getFreq(){
		return Collections.unmodifiableMap(freq);
	}
}
