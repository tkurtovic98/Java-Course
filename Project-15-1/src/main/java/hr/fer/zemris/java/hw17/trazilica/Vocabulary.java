package hr.fer.zemris.java.hw17.trazilica;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Class that represents the vocabulary
 * generated from multiple documents
 * @author Tomislav Kurtović
 *
 */
public class Vocabulary {
	/**
	 * Instance of this class to be used as a reference
	 */
	private static Vocabulary vocab = new Vocabulary();
	/**
	 * Unique words of the vocabulary
	 */
	private Set<String> words;
	/**
	 * indicates whether the vocabulary is generated
	 */
	private boolean generated = false;
	/**
	 * Constructor of vocabulary
	 */
	private Vocabulary(){}
	/**
	 * Sets the words of the vocabulary
	 * @param words collection of words to add to the vocabulary
	 */
	public void setWords (Set<String> words) {
		this.words = new LinkedHashSet<>(words);
	}
	/**
	 * Returns all words from vocabulary
	 * @return
	 */
	public Set<String> getWords() {
		return words;
	}
	/**
	 * Returns single instance of {@link Vocabulary}
	 * @return instance of {@link Vocabulary}
	 */
	public static Vocabulary getVocab() {
		return vocab;
	}
	/**
	 * Used to check if word is found in vocabulary
	 * @param s word to check
	 * @return true if word can be found in vocabulary, false otherwise
	 */
	public  boolean contains(String s) {
		return words.contains(s);
	}
	/**
	 * Used to change status of vocabulary from not generated to generated
	 */
	public void finish() {
		generated = true;
	}
	/**
	 * Returns the state of the vocabulary
 	 * @return true if vocabulary is generated, false otherwise
	 */
	public boolean isGenerated() {
		return generated;
	}

}
