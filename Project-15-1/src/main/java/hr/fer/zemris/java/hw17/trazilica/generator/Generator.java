package hr.fer.zemris.java.hw17.trazilica.generator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.java.hw17.trazilica.Cluster;
import hr.fer.zemris.java.hw17.trazilica.Document;
import hr.fer.zemris.java.hw17.trazilica.IdfVector;
import hr.fer.zemris.java.hw17.trazilica.TfIdfVectors;
import hr.fer.zemris.java.hw17.trazilica.Vocabulary;
import hr.fer.zemris.java.hw17.trazilica.TfIdfVectors.TFIDFVector;
/**
 * Class used to generate all appropriate 
 * {@link TFIDFVector}s of all the documents and 
 * also the {@link Vocabulary} of the set of 
 * documents (cluster)
 * @author Tomislav Kurtović
 *
 */
public class Generator {
	/**
	 * List of words that are not to be added when generating vocabulary
	 */
	private static List<String> wordStop = new ArrayList<>();
	/**
	 * Class that represents a {@link FileVisitor}
	 * that traverses the directory in order 
	 * to get information about the documents in
	 * it 
	 * @author Tomislav Kurtović
	 *
	 */
	private static class Visitor extends SimpleFileVisitor<Path> {
		private Set<String> vocabWords = new LinkedHashSet<>();

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			if (!Vocabulary.getVocab().isGenerated()) {
				Vocabulary.getVocab().setWords(vocabWords);
			}
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			String docText = Files.readString(file);
			Document doc = null;
			if (Vocabulary.getVocab().isGenerated()) {
				doc = new Document(file);
			}

			docText = docText.replaceAll("[^A-Za-zŠĐŽĆČšđžćč]", " ");
			for (String word : docText.split("\\s+")) {
				word = word.toLowerCase();
				if (wordStop.contains(word.trim()))
					continue;
				if (!Vocabulary.getVocab().isGenerated()) {
					vocabWords.add(word);
				} else {
					doc.incrementWordFrequency(word);
				}
			}

			if (doc != null) {
				Cluster.getCluster().addDocument(doc);
				for(String word : doc.getFreq().keySet()) {
					Cluster.getCluster().incrementDocumentCount(word.toLowerCase());
				}
			}

			return FileVisitResult.CONTINUE;
		}

	}
	/**
	 * Method used to generate the {@link Vocabulary} of 
	 * the set of documents later used in evaluating queries and
	 * other things 
	 * @param root path of folder with documents 
	 */
	public static void generateVocabulary(Path root) {
		URL resource = Generator.class.getClassLoader().getResource("hrvatski_stoprijeci.txt");
		try {
			wordStop = Files.readAllLines(Paths.get(resource.toURI()));
			FileVisitor<Path> visitor = new Visitor();
			Files.walkFileTree(root, visitor);
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}

		Vocabulary.getVocab().finish();
	}
	
	/**
	 * Method used to generate the document vectors.
	 * It also generates the idf vector for 
	 * all the documets and stores them in 
	 * the {@link IdfVector}
	 * @param root path of folder with documents
	 */
	public static void generateVectors(Path root) {
		FileVisitor<Path> visitor = new Visitor();
		try {
			Files.walkFileTree(root, visitor);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//generate idfVector first
		for(String word : Vocabulary.getVocab().getWords()) {
			IdfVector.getIdfVector().addWord(word, Cluster.getCluster().getDocumentCount(word));
		}
		
		//Generating tfidf vectors for all documents
		for(Document doc : Cluster.getCluster().documentsInCluster()) {
			List<TFIDFVector> vectors = new ArrayList<>();
			for(String word : doc.getFreq().keySet()) {
				int tf  = doc.getWordFrequency(word);
				double idf = IdfVector.getIdfVector().getIdf(word);
				TFIDFVector vector = new TFIDFVector(word, tf, idf);
				vectors.add(vector);
			}
			TfIdfVectors.getTfIdfVectors().addTFIDFToDocument(doc.getPathToDoc(), vectors);
		}
	}
}
