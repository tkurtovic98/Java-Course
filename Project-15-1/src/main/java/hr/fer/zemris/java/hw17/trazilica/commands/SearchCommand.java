package hr.fer.zemris.java.hw17.trazilica.commands;

/**
 * Interface used as base for all the
 * commands the console has 
 * @author Tomislav Kurtović
 *
 */
public interface SearchCommand {
	/*
	 * Used to execute command the user 
	 * has typed into the console.
	 * The commands can be type, query and results.
	 * Other commands are yet to be added 
	 * 
	 */
	void execute(String[] command) throws SearchCommandException;
}
