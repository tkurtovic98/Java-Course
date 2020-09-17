package hr.fer.zemris.java.hw06.shell.commands.added;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.commands.Environment;
import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.UtilCommands;

/**
 * A {@link ShellCommand} that changes the current workign directory
 * 
 * @author Tomislav Kurtović
 *
 */
public class Cd implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(UtilCommands.split(arguments).length != 1) {
			env.writeln("Invalid number of arguments for cd command!");
			return ShellStatus.CONTINUE;
		}
		
		env.setCurrentDirectory(env.getCurrentDirectory().resolve(Paths.get(arguments)));
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Changes the current working directory");
		description.add("Takes in path of the new wokring directory as argument");
		return Collections.unmodifiableList(description);
	}

}
