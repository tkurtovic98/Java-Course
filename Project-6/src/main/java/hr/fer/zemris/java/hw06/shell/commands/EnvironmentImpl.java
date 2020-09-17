package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.commands.added.Cd;
import hr.fer.zemris.java.hw06.shell.commands.added.DropD;
import hr.fer.zemris.java.hw06.shell.commands.added.ListD;
import hr.fer.zemris.java.hw06.shell.commands.added.MassRename;
import hr.fer.zemris.java.hw06.shell.commands.added.PopD;
import hr.fer.zemris.java.hw06.shell.commands.added.PushD;
import hr.fer.zemris.java.hw06.shell.commands.added.Pwd;


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
	 * Current working dir of process
	 */
	private Path currDir;
	
	/**
	 * Map that lets commands share and store data 
	 */
	private Map<String, Object> sharedData;
	
	/**
	 * Default constructor that populates the commands map and 
	 * instantiates the scanner object
	 */
	public EnvironmentImpl() {
		scanner = new Scanner(System.in);
		commands = new TreeMap<String, ShellCommand>();
		populateMap();
		currDir =  Path.of(System.getProperty("user.dir"));
		sharedData = new HashMap<>();
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
		commands.put("pwd", new Pwd());
		commands.put("cd", new Cd());
		commands.put("dropd",new DropD());
		commands.put("pushd",new PushD());
		commands.put("listd",new ListD());
		commands.put("popd",new PopD());
		commands.put("massrename",new MassRename());
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

	@Override
	public Path getCurrentDirectory() {
		return currDir.toAbsolutePath().normalize();
	}

	@Override
	public void setCurrentDirectory(Path path) {
		if(!path.toFile().exists()) {
			throw new ShellIOException("Specified directory does not exist!");
		}
		currDir = path;
	}

	@Override
	public Object getSharedData(String key) {
		if(sharedData.containsKey(key)) {
			return sharedData.get(key);
		}
		return null;
	}

	@Override
	public void setSharedData(String key, Object value) {
		Objects.requireNonNull(key, "Key can not be null");
		sharedData.put(key, value);
	}
}
