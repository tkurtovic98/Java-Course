package hr.fer.zemris.java.hw06.shell.commands.added;

import java.nio.file.Path;


import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.commands.Environment;
import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.UtilCommands;

/**
 * A {@link ShellCommand} that pushes the path of
 *  the current working  directory to stack.
 * 
 * @author Tomislav Kurtović
 *
 */
public class PushD implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments.isBlank()) {
			env.writeln("Invalid number of arguments for pushD!");
			return ShellStatus.CONTINUE;
		}
		
		if(arguments.startsWith("\"")) {
			int index = UtilCommands.getQuotedPath(arguments, env, true);
			arguments = arguments.substring(1, index);
		} else if(UtilCommands.split(arguments).length != 1) {
			env.writeln("Invalid number of arguments for pushD!");
			return ShellStatus.CONTINUE;
		}
		
		Path newWorkingDir = UtilCommands.resolve(env, Paths.get(arguments));
		
		if(!newWorkingDir.toFile().exists() && !newWorkingDir.toFile().isDirectory()) {
			env.writeln("New path for working directory is either a file or that directory does not exist!");
			return ShellStatus.CONTINUE;
		}
		
		if(env.getSharedData("cdstack") == null) {
			env.setSharedData("cdstack", new Stack<Path>());
		}
		
		@SuppressWarnings("unchecked")
		Stack<Path> workingDirs = (Stack<Path>) env.getSharedData("cdstack");
		workingDirs.add(env.getCurrentDirectory());
		env.setCurrentDirectory(newWorkingDir);
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pushd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Pushes the path of the current working  directory to stack.");
		description.add("Takes directory path as arguments");
		return Collections.unmodifiableList(description);
	}

}
