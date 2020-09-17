package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

/**
 * A {@link ShellCommand} that gets map of all available charsets which is then
 * printed on the screen
 * 
 * @author Tomislav Kurtović
 *
 */
public class Charsets implements ShellCommand{
	/**
	 * map of available charsets 
	 */
	private SortedMap<String,Charset> charset ;
	
	/**
	 * Default constructor used to retrieve avaliable charsets
	 */
	public Charsets() {
		charset = Charset.availableCharsets();
	}
	
	/**
	 * Prints available charsets to screen
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		for(String key : charset.keySet()) {
			env.writeln(charset.get(key).toString());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Lists names of supported charsets");
		description.add("No arguments");
		return Collections.unmodifiableList(description);
	}
}
