package hr.fer.zemris.java.hw17.trazilica.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.Set;

import hr.fer.zemris.java.hw17.trazilica.IdfVector;
import hr.fer.zemris.java.hw17.trazilica.ResultContainer;
import hr.fer.zemris.java.hw17.trazilica.TfIdfVectors;
import hr.fer.zemris.java.hw17.trazilica.TfIdfVectors.TFIDFVector;
import hr.fer.zemris.java.hw17.trazilica.Vocabulary;
/**
 * {@link SearchCommand} used 
 * to execute the user query
 * @author Tomislav Kurtović
 *
 */
public class QuerySearchCommand implements SearchCommand {

	/**
	 * The method validates the words from 
	 * the user query and makes appropriate vector representation 
	 * of the query.
	 * It then calculates the similarities between all the documents and
	 * the query to find the most similar documents 
	 */
	@Override
	public void execute(String[] command) throws SearchCommandException {
		if (!command[0].equals("query")) {
			throw new SearchCommandException("Invalid command argument for command: QUERY");
		}

		Set<String> validWords = new LinkedHashSet<>();
		for (int i = 1, len = command.length; i < len; i++) {
			if (Vocabulary.getVocab().contains(command[i])) {
				validWords.add(command[i]);
			}
		}

		System.out.printf("Query is : [" + String.join(",", validWords) + "]%n");

		Map<String, TFIDFVector> queryVector = new HashMap<>();
		for (String word : validWords) {
			queryVector.put(word, new TFIDFVector(word, 1, IdfVector.getIdfVector().getIdf(word)));
		}

		double dot = 0;
		double normDoc = 0;
		
		Map<Path, Double> similarities = new LinkedHashMap<>();
		
		for (Path path : TfIdfVectors.getTfIdfVectors().getDocumentsTFIDFs().keySet()) {
			List<TFIDFVector> docVectors = TfIdfVectors.getTfIdfVectors().getDocumentsTFIDFs().get(path);
			normDoc = 0;
			for (TFIDFVector vector : docVectors) {
				normDoc += Math.pow(vector.getTFIDF(),2); 
				if (validWords.contains(vector.getWord())) {
					dot += vector.getTFIDF() * queryVector.get(vector.getWord()).getTFIDF();
				}
			}
			
			if(dot < 0.00001) continue;
			double normQuery = 0;
			for(TFIDFVector vector : queryVector.values()) {
				normQuery += Math.pow(vector.getTFIDF(),2);
			}
			similarities.put(path, dot/(Math.sqrt(normDoc)*Math.sqrt(normQuery)));
			dot = 0;
		}
		
		
		List<Entry<Path, Double>> resultsSorted = new ArrayList<>(similarities.entrySet());
		resultsSorted.sort(Entry.comparingByValue((v1,v2) -> Double.compare(v2, v1)));
		
		Set<Entry<Path,Double>> set = new LinkedHashSet<>(resultsSorted.subList(0, resultsSorted.size() > 10 ? 10 : resultsSorted.size()));
		
		ResultContainer.getRC().setResults(set);
		ResultContainer.getRC().printResults();
	}
}
