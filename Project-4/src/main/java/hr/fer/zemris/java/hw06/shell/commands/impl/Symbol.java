package hr.fer.zemris.java.hw06.shell.commands.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.commands.Environment;
import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.UtilCommands;

/**
 * A {@link ShellCommand} that is used to print shell symbol info
 * or change the symbols
 * 
 * @author Tomislav Kurtović
 *
 */
public class Symbol implements ShellCommand {
	
	/**
	 * Used to change or print the symbols of the shell 
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] splitted = UtilCommands.split(arguments);
		
		if(splitted.length != 1 && splitted.length != 2) {
			env.writeln("Invalid number of arguments for symbol command");
			return ShellStatus.CONTINUE;
		}
		
		if(splitted.length == 1) {
			printInfo(splitted[0],env);
		}
		
		if(splitted.length == 2) {
			changeCommand(splitted[0], splitted[1], env);
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Prints information about the symbols used by the shell");
		description.add("Can modify symbols");
		return Collections.unmodifiableList(description);
	}
	
	/**
	 * Changes the symbol of the shell.
	 * It can change the prompt, multiline and moreline symbol into
	 * any character specified by user 
	 * @param symbolType type of symbol
	 * @param newSymbol new symbol
	 * @param env environment of shell
	 */
	private static void changeCommand(String symbolType, String newSymbol, Environment env) {
		if(symbolType.isBlank() || newSymbol.isBlank()) {
			env.writeln("Blank input found!");
			return;
		}
		char symbol;
		
		switch(symbolType) {
			case "PROMPT":
				symbol = env.getPromptSymbol();
				env.setPromptSymbol(newSymbol.charAt(0));
				break;
			case "MULTILINE":
				symbol = env.getMultilineSymbol();
				env.setMultilineSymbol(newSymbol.charAt(0));
				break;
			case "MORELINES":
				symbol = env.getMorelinesSymbol();
				env.setMorelinesSymbol(newSymbol.charAt(0));
				break;
			default:
				System.out.println("Invalid symbol type");
				return;
		}
		env.write("Symbol for " + symbolType + " changed from '" + symbol +"' to '" +  newSymbol + "'%n");
	}

	/**
	 * Prints the symbol of the shell.
	 * @param symbolType type of symbol
	 * @param env environment of shell
	 */
	private static void printInfo(String symbolType, Environment env) {
		if(symbolType.isBlank()) {
			env.writeln("Invalid command!");
			return;
		}
		char symbol ;
		
		switch(symbolType) {
			case "PROMPT":
				symbol = env.getPromptSymbol();
				break;
			case "MORELINES":
				symbol = env.getMorelinesSymbol();
				break;
			case "MULTILINE":
				symbol = env.getMultilineSymbol();
				break;
			default:
				env.writeln("Invalid symbol type");
				return;
		}
		
		env.write("Symbol for " + symbolType +" is '" + symbol + "'%n");
	}
}
