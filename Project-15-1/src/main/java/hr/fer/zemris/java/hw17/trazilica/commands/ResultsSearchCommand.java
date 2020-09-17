package hr.fer.zemris.java.hw17.trazilica.commands;

import hr.fer.zemris.java.hw17.trazilica.ResultContainer;
/**
 * {@link SearchCommand} used 
 * to execute the  result command
 * @author Tomislav Kurtović
 *
 */
public class ResultsSearchCommand implements SearchCommand {
	
	@Override
	public void execute(String[] command) throws SearchCommandException {
		if(!command[0].equals("results")) {
			throw new SearchCommandException("Invalid command argument for command: RESULTS");
		}
		
		ResultContainer.getRC().printResults();
	}

}
