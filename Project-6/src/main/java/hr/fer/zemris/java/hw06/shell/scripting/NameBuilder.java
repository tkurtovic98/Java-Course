package hr.fer.zemris.java.hw06.shell.scripting;

import hr.fer.zemris.java.hw06.shell.commands.added.FilterResult;

/**
 * Interface used in building different objects 
 * that perform various manipulations on names 
 * @author Tomislav Kurtović
 *
 */
public interface NameBuilder {
	/**
	 * Used to execute given command on 
	 * the result. 
	 * The result is a file the has already
	 * been filtered
	 * @param result filtered file
	 * @param sb string builder
	 */
	void execute(FilterResult result, StringBuilder sb);
}
