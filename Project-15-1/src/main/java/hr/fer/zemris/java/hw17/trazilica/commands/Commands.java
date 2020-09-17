package hr.fer.zemris.java.hw17.trazilica.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used as a connector between the 
 * console and the {@link SearchCommand}s
 * the console has 
 * @author Tomislav Kurtović
 *
 */
public class Commands {
	/**
	 * Map of commands 
	 */
	private static Map<String, SearchCommand> commands = new HashMap<>();
	/**
	 * Initializes all the commands of the console
	 */
	public static void init() {
		commands.put("query", new QuerySearchCommand());
		commands.put("type", new TypeSearchCommand());
		commands.put("results", new ResultsSearchCommand());
	}
	/**
	 * Calls the execute method for the {@link SearchCommand}
	 * from the collection of commands. 
	 * If no command is found then nothing happens 
	 * @param command the name and arguments of the {@link SearchCommand}
	 * @throws SearchCommandException if no command with the specified name exists
	 */
	public static void execute(String[] command) throws SearchCommandException {
		if(!commands.containsKey(command[0])) {
			throw new SearchCommandException("No command found");
		}
		commands.get(command[0]).execute(command);
	}
	
}
