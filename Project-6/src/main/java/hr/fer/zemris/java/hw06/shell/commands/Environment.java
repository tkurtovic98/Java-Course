package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.ShellIOException;


/**
 * Interface used in {@link MyShell} for 
 * communication with the {@link ShellCommand} commands.
 * It is used to read and write on screen 
 * as well as change the default shell symbols 
 * for prompt, multiline and moreline
 * 
 * @author Tomislav Kurtović
 *
 */
public interface Environment {

	/**
	 * Used to read user input in one line
	 * 
	 * @return readed line
	 * @throws ShellIOException if line is blank or unable to read input
	 */
	 String readLine() throws ShellIOException;
	 
	 /**
	  * Writes the given text on the screen
	  * @param text text to be written
	  * @throws ShellIOException if text is blank 
	  */
	 void write(String text) throws ShellIOException;
	 
	 /**
	  * Writes a line of given text on the screen
	  * @param text text to be written
	  * @throws ShellIOException if text is blank 
	  */
	 void writeln(String text) throws ShellIOException;
	 
	 /**
	  * Returns the commands that the shell can 
	  * use, sorted by command name 
	  * 
	  * @return map of sorted shell commands with name as key
	  */
	 SortedMap<String, ShellCommand> commands();
	 
	 /**
	  * Returns the muliline symbol of the shell
	  * @return multiline symbol
	  */
	 Character getMultilineSymbol();
	 
	 /**
	  * Sets the multilinesymobl of the shell
	  */
	 void setMultilineSymbol(Character symbol);
	 
	 /**
	  * Returns the prompt symbol of the shell
	  * @return prompt symbol 
	  */
	 Character getPromptSymbol();
	 
	 /**
	  * Sets the multilinesymobl of the shell
	  */
	 void setPromptSymbol(Character symbol);
	 
	 /**
	  * Returns the morelines  symbol of the shell
	  * @return morelines symbol 
	  */
	 Character getMorelinesSymbol();
	 
	 /**
	  * Sets the morelines of the shell
	  */
	 void setMorelinesSymbol(Character symbol);
	 
	 /**
	  * Gets current working directory of java programme
	  * @return path to current working directory
	  */
	 Path getCurrentDirectory();
	 
	 /**
	  * Sets current working directory to specified path
	  * @param path path to set current working directory to
	  */
	 void setCurrentDirectory(Path path);
	 
	 /**
	  * Enables commands to get shared data from each other
	  * @param key key of shared data
	  * @return data
	  */
	 Object getSharedData(String key);
	 
	 /**
	  * Enables commands to store shared data for later retrieval
	  * @param key key of shared data to be stored
	  * @param value value of data to store
	  */
	 void setSharedData(String key, Object value);
	 
	
}
