package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

/**
 * Interface that represents the shell commands 
 * 
 * @author Tomislav Kurtović
 */
public interface ShellCommand {
	
	/**
	 * Used to execute wanted shell command with given
	 * arguments and environment
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	/**
	 * Returns name of command
	 * @return name of command
	 */
	String getCommandName();
	/**
	 * Returns list that can not be modified 
	 * that is populated by the command descriptions
	 * @return list of command descriptions 
	 */
	List<String> getCommandDescription();
}
