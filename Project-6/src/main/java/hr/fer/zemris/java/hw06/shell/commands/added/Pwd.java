package hr.fer.zemris.java.hw06.shell.commands.added;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.commands.Environment;
import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.UtilCommands;


/**
 * A {@link ShellCommand} that prints the path of the current working  directory
 * 
 * @author Tomislav Kurtović
 *
 */
public class Pwd implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(UtilCommands.split(arguments).length != 1) {
			env.writeln("Invalid number of arguments for pwd");
			return ShellStatus.CONTINUE;
		}
		env.writeln("Current working directory is: " + env.getCurrentDirectory());
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pwd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Prints the path of the current working  directory ");
		return Collections.unmodifiableList(description);
	}
}
