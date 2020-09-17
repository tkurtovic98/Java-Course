package hr.fer.zemris.java.hw17.trazilica.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import hr.fer.zemris.java.hw17.trazilica.ResultContainer;
/**
 * {@link SearchCommand} used 
 * to execute user type command 
 * @author Tomislav Kurtović
 *
 */
public class TypeSearchCommand implements SearchCommand {
	/**
	 * The method finds the appropriate document from the query results 
	 * and prints its content to the screen.
	 * If the arguments passed to the method are incorrect 
	 * an exception is thrown 
	 */
	@Override
	public void execute(String[] command) throws SearchCommandException {
		if(!command[0].equals("type")) {
			throw new SearchCommandException("Invalid command argument for command: TYPE");
		}
		
		if(command.length != 2) {
			throw new SearchCommandException("Invalid number of arguments for command: TYPE");
		}
		int index;

		try {
			index = Integer.parseInt(command[1]);
		}catch(NumberFormatException e) {
			throw new SearchCommandException("Invalid argument for result number");
		}
		
		Path printFile =  ResultContainer.getRC().getResultPath(index);
			
		System.out.println("Dokument : " + printFile.toString());
		System.out.println("--------------------------------------------");
		
		try {
			System.out.println(Files.readString(printFile));
		} catch (IOException e) {
			System.out.println("No file found!");
		}
		
	}
}
