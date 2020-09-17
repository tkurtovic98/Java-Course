package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.util.UtilCommands;

/**
 * A {@link ShellCommand} that makes a new directory
 * @author Tomislav Kurtović
 *
 */
public class MkDir implements ShellCommand{

	/**
	 * Used to make new directory with the specified name
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		boolean isDoubleQuote = false;
		if(arguments.startsWith("\"") || arguments.startsWith("\'")){
			if(arguments.startsWith("\"")) {
				isDoubleQuote = true;
			}
			int indexOfQuote = UtilCommands.getQuotedPath(arguments, env, isDoubleQuote );
			arguments = arguments.substring(1, indexOfQuote);
		} else if(UtilCommands.split(arguments).length != 1) {
			env.writeln("Invalid number of arguments");
			return ShellStatus.CONTINUE;
		}
		File dir = new File(arguments);
		dir.mkdir();
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Makes new directory with given name");
		return Collections.unmodifiableList(description);
	}

}
