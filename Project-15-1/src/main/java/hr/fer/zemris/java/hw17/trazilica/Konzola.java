package hr.fer.zemris.java.hw17.trazilica;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw17.trazilica.TfIdfVectors.TFIDFVector;
import hr.fer.zemris.java.hw17.trazilica.commands.Commands;
import hr.fer.zemris.java.hw17.trazilica.commands.SearchCommandException;
import hr.fer.zemris.java.hw17.trazilica.generator.Generator;

/**
 * Class used as the main program of the console 
 * that communicates with the user 
 * @author Tomislav Kurtović
 *
 */
public class Konzola {
	/**
	 * Main method that initializes the {@link Commands}
	 * and also calls methods used to generate 
	 * the {@link Vocabulary} of the set of documents.
	 * It also generates the {@link TFIDFVector}s of all the
	 * documents by calling appropriate methods
	 * @param args argumenss passed to the method. Here it should be path to folder that contains all the documents
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Invalid number of arguments");
			return;
		}
		List<String> validCommands = Arrays.asList(new String[]{"query","type","results"});
		
		Generator.generateVocabulary(Paths.get(args[0]));
		Generator.generateVectors(Paths.get(args[0]));
		System.out.println("Size of vocabulary:" + Vocabulary.getVocab().getWords().size());
		
		Commands.init();
		
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.printf("Enter commmand > ");
			if(sc.hasNextLine()) {
				String input = sc.nextLine();
				
				if(input.equals("exit")) {
					break;
				}
				
				String[] splitCommand = input.split("\\s+");
				if(validCommands.contains(splitCommand[0])) {
					try {
						Commands.execute(splitCommand);
					} catch (SearchCommandException e) {
						System.out.println(e.getMessage());
					}
					continue;
				}
				
				System.out.println("Unknown command.");
			}
			
		}
		sc.close();
	}
}
