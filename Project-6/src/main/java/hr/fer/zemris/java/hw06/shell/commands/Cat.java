package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.util.UtilCommands;

/**
 * A {@link ShellCommand} that opens given file and writes its content to console.
 * Upon instantiation it gets a map of all available charsets which is then
 * used when executing the method
 * 
 * @author Tomislav Kurtović
 *
 */
public class Cat implements ShellCommand{
	/**
	 * Map of available charsets
	 */
	private SortedMap<String, Charset> validCharset;
	/**
	 * Used as flag if given path is in double quotes
	 */
	private boolean isDoubleQuote ;
	
	/**
	 * Default constructor used to retrieve map of
	 * available charsets
	 */
	public Cat() {
		validCharset  =  Charset.availableCharsets();
	}
	
	/**
	 * The method checks if the path in the arguments is in quotes 
	 * and then acts accordingly.
	 * If there are quotes than a method which gets the end of the quotes
	 * is called.
	 * It then opens the file and prints its content on the screen 
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Charset charSet = StandardCharsets.UTF_8;
		Path path;
		isDoubleQuote = false;
		if(arguments.startsWith("\"") || arguments.startsWith("\'")){
			if(arguments.startsWith("\"")) {
				isDoubleQuote = true;
			}
			int indexOfQuote = UtilCommands.getQuotedPath(arguments, env, isDoubleQuote );
			
			if(indexOfQuote < arguments.length() - 1) {
				String charSetValue = arguments.substring(indexOfQuote + 1).trim();
				
				if(validCharset.containsKey(charSetValue)) {
					charSet = validCharset.get(charSetValue);
				} else {
					env.writeln("Character set is not valid");
					return ShellStatus.CONTINUE;
				}
			}
			path = env.getCurrentDirectory().resolve(Path.of(arguments.substring(1,indexOfQuote)));
		} else {
			String[] splitted = UtilCommands.split(arguments);
			if(splitted.length != 2 && splitted.length != 1) {
				env.writeln("Please enter correct path");
				return  ShellStatus.CONTINUE;
			}
			if(splitted.length == 2) {
				if(validCharset.containsKey(splitted[1])) {
					charSet = validCharset.get(splitted[1]);
				} else {
					env.writeln("Character set is not valid");
					return ShellStatus.CONTINUE;
				}
			}
			path = Path.of(splitted[0]);
		}
		try{
			List<String> readedInfo = Files.readAllLines(path,charSet);
			StringBuilder builder = new StringBuilder();
			env.writeln("FILE DATA: ");
			for(String line : readedInfo) {
				if(line.isBlank()) {
					builder.append(line);
					continue;
				} 
				builder.append(line);
				env.writeln(builder.toString());
				builder.setLength(0);
			  }
		   } catch (IOException e) {
			   env.writeln("Error while reading from file!");
		   }
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Opens given file and writes its content to console");
		description.add("First argument is path, second is charset");
		description.add("First argument mandatory, second optional");
		return Collections.unmodifiableList(description);
	}
}
