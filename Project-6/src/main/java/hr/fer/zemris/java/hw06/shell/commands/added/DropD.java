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
 * A {@link ShellCommand} that removes top working directory from stack if stack is not empty
 * 
 * @author Tomislav Kurtović
 *
 */
public class DropD implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isBlank()) {
			env.writeln("Invalid number of arguments for dropD command!");
			return ShellStatus.CONTINUE;
		}
		
		if(env.getSharedData("cdstack") == null) {
			env.writeln("No stack with working directories found!");
			return ShellStatus.CONTINUE;
		}
		
		@SuppressWarnings("unchecked")
		Stack<Path> workingDirs = (Stack<Path>) env.getSharedData("cdstack");
		
		if(workingDirs.isEmpty()) {
			env.writeln("No added directories!");
			return ShellStatus.CONTINUE;
		}
		
		workingDirs.pop();
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "dropd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Removes top working directory from stack if stack is not empty");
		return Collections.unmodifiableList(description);
	}

}
