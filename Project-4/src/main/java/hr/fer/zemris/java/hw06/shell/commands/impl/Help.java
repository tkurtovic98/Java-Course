package hr.fer.zemris.java.hw06.shell.commands.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw06.shell.commands.Environment;
import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ShellStatus;

/**
 * A {@link ShellCommand} that is used to print information about the
 * commands to the screen
 * @author Tomislav Kurtović
 *
 */
public class Help implements  ShellCommand{
	/**
	 * Used to check what info is to be printed on screen.
	 * If no arguments are found then a list of all valid commands is 
	 * printed out.
	 * If command name is found then the command descriptions
	 * are printed to the screen
	 * 
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Map<String, ShellCommand> map = env.commands();
		if(arguments.isBlank()) {
			env.writeln("Commands supported : ");
			for(String key : map.keySet()) {
				env.writeln(map.get(key).getCommandName());
			}
			return ShellStatus.CONTINUE;
		}
		
		if(map.containsKey(arguments)) {
			env.writeln("Descriptions of selected command is: ");
			for(String description : map.get(arguments).getCommandDescription()) {
				env.writeln(description);
			}
		} else {
			env.writeln("Given command not found!");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("If no arguments passed, lists names of all supported commands");
		description.add("If one argument passed, prints description of command");
		return Collections.unmodifiableList(description);
	}
}
