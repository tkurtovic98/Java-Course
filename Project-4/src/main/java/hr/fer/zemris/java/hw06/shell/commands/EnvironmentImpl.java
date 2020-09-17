package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.commands.impl.Cat;
import hr.fer.zemris.java.hw06.shell.commands.impl.Charsets;
import hr.fer.zemris.java.hw06.shell.commands.impl.Copy;
import hr.fer.zemris.java.hw06.shell.commands.impl.Help;
import hr.fer.zemris.java.hw06.shell.commands.impl.HexDump;
import hr.fer.zemris.java.hw06.shell.commands.impl.Ls;
import hr.fer.zemris.java.hw06.shell.commands.impl.MkDir;
import hr.fer.zemris.java.hw06.shell.commands.impl.Symbol;
import hr.fer.zemris.java.hw06.shell.commands.impl.Tree;


/**
 * Class that represents the implementation of the
 * {@link Environment} interface.
 * It is instantiated when the {@link MyShell} is 
 * started and upon instantiating populates the 
 * command map with valid commands  
 * 
 * @author Tomislav Kurtović
 *
 */
public class EnvironmentImpl implements Environment{

	/**
	 * Used by shell to accept multi-line input
	 */
	private static Character MORELINESSYMBOL = '\\';

	/**
	 * symbol printed on first line of input
	 */
	private static Character PROMPTSYMBOL = '>';

	/**
	 * Prompt symbol printed on every line except first of multiline input
	 */
	private static Character MULTILINESYMBOL = '|';
	/**
	 * Map of valid shell commands
	 */
	private SortedMap<String,ShellCommand> commands;
	/**
	 * scanner of user input 
	 */
	private Scanner scanner;
	
	/**
	 * Default constructor that populates the commands map and 
	 * instantiates the scanner object
	 */
	public EnvironmentImpl() {
		scanner = new Scanner(System.in);
		commands = new TreeMap<String, ShellCommand>();
		populateMap();
	}
	
	/**
	 * Used to populate the commands map
	 */
	private void populateMap() {
		commands.put("charsets", new Charsets());
		commands.put("cat", new Cat());
		commands.put("ls", new Ls());
		commands.put("tree", new Tree());
		commands.put("copy",new Copy());
		commands.put("mkdir", new MkDir());
		commands.put("hexdump",new HexDump());
		commands.put("help", new Help());
		commands.put("symbol",new Symbol());
	}

	@Override
	public String readLine() throws ShellIOException {
		String line = "";
		if (scanner.hasNext()) {
			if (scanner.hasNextLine()) {
				line = scanner.nextLine().trim();
			}
		} else {
			scanner.close();
			throw new ShellIOException("Line can not be blank");
		}
		return line;
	}
	@Override
	public void write(String text) throws ShellIOException {
		checkText(text);
		System.out.printf(text);
	}
	@Override
	public void writeln(String text) throws ShellIOException {
		checkText(text);
		System.out.println(text);
	}
	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}
	@Override
	public Character getMultilineSymbol() {
		return MULTILINESYMBOL;
	}
	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTILINESYMBOL = symbol;
	}
	@Override
	public Character getPromptSymbol() {
		return PROMPTSYMBOL;
	}
	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPTSYMBOL = symbol;
	}
	@Override
	public Character getMorelinesSymbol() {
		return MORELINESSYMBOL;
	}
	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORELINESSYMBOL = symbol;
	}
	
	/**
	 * Checks the given text to see if {@link ShellIOException} should be thrown
	 * @param text text to check
	 */
	private void checkText(String text) {
		if(text.isBlank()) {
			scanner.close();
			throw new ShellIOException("Unable to print text");
		}
	}
}
