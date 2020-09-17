package hr.fer.zemris.java.hw06.shell.commands.added;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.commands.Environment;
import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ShellStatus;

/**
 * A {@link ShellCommand} that Returns the top path of directory 
 * from the stack and sets it as the current working directory
 * 
 * @author Tomislav Kurtović
 *
 */
public class PopD implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isBlank()) {
			env.writeln("Invalid number of arguments for popd command!");
			return ShellStatus.CONTINUE;
		}
		
		if(env.getSharedData("cdstack") == null) {
			env.writeln("No stack with working directories found!");
			return ShellStatus.CONTINUE;
		}
		
		@SuppressWarnings("unchecked")
		Stack<Path> workingDirs = (Stack<Path>) env.getSharedData("cdstack");
		
		if(workingDirs.isEmpty()) {
			env.writeln("Stack of working directories is empty!");
			return ShellStatus.CONTINUE;
		}
		
		Path peek = workingDirs.peek();
		
		if(peek.toFile().exists()) {
			env.setCurrentDirectory(peek);
			return ShellStatus.CONTINUE;
		}
		
		workingDirs.remove(peek);
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "popd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Returns the top path ofdirectory from the stack and sets it as the current working directory.");
		return Collections.unmodifiableList(description);
	}

}
