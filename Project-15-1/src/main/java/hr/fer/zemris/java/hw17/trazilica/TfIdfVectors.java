package hr.fer.zemris.java.hw17.trazilica;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to represent a collection of {@link TFIDFVector}s
 * used in finding similarities between 
 * documents
 * @author Tomislav Kurtović
 *
 */
public class TfIdfVectors {
	private static TfIdfVectors vectors = new TfIdfVectors();
	/**
	 * Map that contains paths of documents  the tfidf vectors 
	 * of those documents
	 */
	private Map<Path, List<TFIDFVector>> documentsTFIDF = new HashMap<>();
	
	/**
	 * Constructor of vectors collection
	 */
	private TfIdfVectors() {}
	
	public static TfIdfVectors getTfIdfVectors() {
		return vectors;
	}
	
	public Map<Path, List<TFIDFVector>> getDocumentsTFIDFs () {
		return documentsTFIDF;
	}
	
	/**
	 * Used to store the {@link TFIDFVector} of the document for the word
	 * @param path path of document that is being connected to the word 
	 * @param values list of {@link TFIDFVector}s
	 */
	public void addTFIDFToDocument(Path path, List<TFIDFVector> values ) {
		documentsTFIDF.put(path, values);
	}
	
	/**
	 * Helper class used to represent TF-IDF vector for words from
	 * the {@link Vocabulary}
	 * @author Tomislav Kurtović
	 *
	 */
	public static class TFIDFVector {
		/**
		 * Word to add
		 */
		private String word;
		/**
		 * Value of tfidf 
		 */
		private Double tfidf;
		/**
		 * Value of tf
		 */
		private int tf;
		/**
		 * Value of idf
		 */
		private double idf;
		
		/**
		 * Constructor 
		 * @param word word to add
		 * @param tf value of tf
		 *  @param idf value of idf
		 */
		public TFIDFVector(String word, int tf, double idf) {
			super();
			this.word = word;
			this.tfidf = tf * idf;
			this.tf = tf;
			this.idf = idf;
		}
		
		/**
		 * Gets the word
		 * @return word
		 */
		public String getWord() {
			return word;
		}
		/**
		 * Gets the tfidf value
		 * @return tfidf value
		 */
		public Double getTFIDF() {
			return tfidf;
		}
		/**
		 * Gets the tf value
		 * @return tf value
		 */
		public double getIdf() {
			return idf;
		}
		/**
		 * Gets the idf value
		 * @return idf value
		 */
		public int getTf() {
			return tf;
		}
	}
}
