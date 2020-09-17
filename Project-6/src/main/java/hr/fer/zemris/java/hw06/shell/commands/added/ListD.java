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
 * A {@link ShellCommand} that prints all directories on stack to the screen if any exist.
 * @author Tomislav Kurtović
 *
 */
public class ListD implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isBlank()) {
			env.writeln("Invalid number of arguments for listD command!");
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
		
		for(Path p : workingDirs) {
			env.writeln(p.toString());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "listd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Prints all directories on stack to the screen if any exist.");
		return Collections.unmodifiableList(description);
	}

}
