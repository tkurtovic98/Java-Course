package hr.fer.zemris.java.hw17.trazilica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class that represents a cluster 
 * of documents 
 * @author Tomislav Kurtović
 *
 */
public class Cluster {
	/**
	 * Instance of {@link Cluster}
	 */
	private static Cluster cluster = new Cluster();
	/**
	 * Map that is used to store information of number of documents with certain word
	 */
	private Map<String, Integer> howManyDocumentsWithWord = new HashMap<>();
	/**
	 * {@link Document}s of this cluster
	 */
	private List<Document> documents = new ArrayList<>();
	
	/**
	 * Constructor
	 */
	private Cluster() {}

	/**
	 * Returns instance of cluster
	 * @return {@link Cluster} instance
	 */
	public static Cluster getCluster() {
		return cluster;
	}
	
	/**
	 * Returns number of documents in cluster that contain the word 
	 * @param word word to find
	 * @return number of documents
	 */
	public Integer getDocumentCount(String word) {
		return howManyDocumentsWithWord.get(word);
	}
	/**
	 * Used to increment document count for specified word
	 * @param word word whose document count should be incremented
	 */
	public void incrementDocumentCount(String word) {
		howManyDocumentsWithWord.put(word, howManyDocumentsWithWord.containsKey(word) ? howManyDocumentsWithWord.get(word)+1 : 1);
	}
	/**
	 * Adds document to cluster
	 * @param doc {@link Document} to add
	 */
	public void addDocument(Document doc) {
		documents.add(Objects.requireNonNull(doc));
	}
	/**
	 * Returns documents in cluster
	 * @return list of documents in cluster 
	 */
	public List<Document> documentsInCluster(){
		return documents;
	}
}
