package hr.fer.zemris.java.hw06.shell.commands.added;

import java.io.File;
import java.util.Objects;
import java.util.regex.Matcher;

import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;

/**
 * Class used to represent filtered files 
 * @author Tomislav Kurtović
 *
 */
public class FilterResult {
	/**
	 * file
	 */
	private File name;
	/**
	 * matcher
	 */
	private Matcher m;
	
	/**
	 * Default constructor
	 * @param name
	 * @param m
	 */
	public FilterResult(File name, Matcher m) {
		this.name = name;
		this.m = m;
	}

	/**
	 * Number of groups in matcher
	 * @return numebr of groups
	 */
	public int numberOfGroups() {
		return m.groupCount() + 1;
	}
	
	/**
	 * String of group at given index
	 * @param index index of specified group
	 * @return String of group at index
	 */
	public String group(int index) {
		if(index < 0 || index > numberOfGroups() - 1) {
			throw new IllegalArgumentException("Index is invalid");
		}
		return m.group(index);
	}
	
	@Override
	public String toString() {
		return name.getName();
	}
}
