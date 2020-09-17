package hr.fer.zemris.java.hw06.shell.commands.added;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.commands.Environment;
import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ShellStatus;
import hr.fer.zemris.java.hw06.shell.scripting.NameBuilder;
import hr.fer.zemris.java.hw06.shell.scripting.NameBuilderParser;
import hr.fer.zemris.java.hw06.shell.scripting.NameBuilderParserException;
import hr.fer.zemris.java.hw06.shell.util.UtilCommands;

/**
 * A {@link ShellCommand} that is used for renaiming/ transfering files from one directory to another
 * 
 * 
 * @author Tomislav Kurtović
 *
 */
public class MassRename implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isBlank()) {
			env.writeln("Invalid number of arguments for massrename command");
			return ShellStatus.CONTINUE;
		}
		List<String> args;
		try {
			args = UtilCommands.parseArgs(arguments);
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if(args.size() != 4 && args.size() != 5) {
			env.writeln("Invalid number of arguments for massrename command");
			return ShellStatus.CONTINUE;
		}
		
		String dir1 = args.get(0);
		String dir2 = args.get(1);
		String cmd = args.get(2);
		String mask = args.get(3);
		
		Path pathToDir1 = UtilCommands.resolve(env, Paths.get(dir1));
		Path pathToDir2 = UtilCommands.resolve(env, Paths.get(dir2));
		
		if(!pathToDir1.toFile().isDirectory()) {
			env.writeln("Dir1 from arguments is not a directory!");
		}
		
		switch(cmd) {
			case "filter":
				printFiltered(pathToDir1,mask,env);
				break;
			case "groups":
				groups(pathToDir1,mask,env);
				break;
			case "show":
				if(args.size() != 5) {
					env.writeln("Missing argument for massrename - show command");
					return ShellStatus.CONTINUE;
				}
				show(pathToDir1,args.get(4), mask,env);
				break;
			case "execute":
				if(args.size() != 5) {
					env.writeln("Missing argument for massrename - execute command");
					return ShellStatus.CONTINUE;
				}
				execute(pathToDir1, pathToDir2, args.get(4), mask, env);
				break;
			default :
				env.writeln("Invalid command name");
				break;
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Shows information about the files and renamed files on screen
	 * @param dir1 path of first directory (source)
	 * @param expression expression used for renaming
	 * @param mask regular expression used for filtering 
	 * @param env Environment of Shell
	 */
	private void show(Path dir1, String expression, String mask, Environment env) {
		printInfo(dir1, null, expression, mask,env);
	}
	
	/**
	 * Transfers renamed files the files and and prints info on screen
	 * @param dir1 path of first directory (source)
	 * @param dir2 path of first directory (destination)
	 * @param expression expression used for renaming
	 * @param mask regular expression used for filtering 
	 * @param env Environment of Shell
	 */
	private void execute(Path dir1, Path dir2, String expression, String mask, Environment env) {
		printInfo(dir1, dir2, expression, mask,env);
	}
	
	/**
	 * Prints information on screen
	 * @param dir1 path of first directory (source)
	 * @param dir2 path of first directory (destination)
	 * @param expression expression used for renaming
	 * @param mask regular expression used for filtering 
	 * @param env Environment of Shell
	 */
	private void printInfo(Path dir1, Path dir2, String expression, String mask, Environment env) {
		try {
			List<String> names = printOrMoveNames(dir1, dir2, expression, mask);
			if(names == null) {
				env.writeln("Error while getting names!");
			}
			for(String name : names) {
				env.writeln(name);
			}
		}catch(IOException e) {
			env.writeln("Error while moving files!");;
		}
	}
	
	/**
	 * Prints or moves files to another directory
	 * @param dir1
	 * @param dir2
	 * @param expression
	 * @param mask
	 * @return
	 * @throws IOException
	 */
	private List<String> printOrMoveNames(Path dir1, Path dir2, String expression, String mask) throws IOException {
		List<FilterResult> files = filter(dir1, mask);
		if (files == null) {
			return null;
		}
		List<String> names = new ArrayList<>();

		try {
			NameBuilderParser parser = new NameBuilderParser(expression);
			NameBuilder builder = parser.getNameBuilder();
			for (FilterResult file : files) {
				StringBuilder sb = new StringBuilder();
				builder.execute(file, sb);
				String novoIme = sb.toString();
				if (dir2 == null) {
					names.add(file.toString() + " => " + novoIme);
				} else {
					Files.move(dir1.resolve(file.toString()), dir2.resolve(novoIme));
					names.add(dir1.getFileName() + "/" + file.toString() + " => " + dir2.getFileName() + "/" + novoIme);
				}
			}
		} catch (NameBuilderParserException e) {
			return null;
		}

		return names;
	}
	
	/**
	 * Prints filtered results on screen
	 * @param path path
	 * @param mask regular expression
	 * @param env Environment
	 */
	private void printFiltered(Path path, String mask, Environment env) {
		List<FilterResult> result = filter(path, mask);
		if(result == null) {
			env.writeln("No results found!");
		}
		
		for(FilterResult res : result) {
			env.writeln(res.toString());
		}
	}


	/**
	 * Prints groups of file on screen
	 * @param path path
	 * @param mask regular expression
	 * @param env Environment
	 */
	private void groups(Path path, String mask, Environment env) {
		List<FilterResult> result = filter(path, mask);
		if(result == null) {
			env.writeln("No results found");
		}
		
		for(FilterResult res : result) {
			int numberOfGroups = res.numberOfGroups();
			env.write(res.toString());
			for(int i = 0; i < numberOfGroups; i++) {
				if(i == numberOfGroups - 1 ) {
					env.write(" " + i + ": " + res.group(i) + "\n");
					break;
				}
				env.write(" " + i + ": " + res.group(i));
			}
			
		}
	}

	/**
	 * Filteres files based on the given pattern
	 * @param dir source 
	 * @param pattern regulat expression 
	 * @return list of filtered files
	 */
	private static List<FilterResult> filter(Path dir, String pattern){
		File[] files = dir.toFile().listFiles();
		if(files.length == 0) {
			return null;
		}
		
		List<FilterResult> result = new ArrayList<>();
		Pattern p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		for(File file : files) {
			if(file.isDirectory()) continue;
			Matcher matcher = p.matcher(file.getName());
			if(matcher.matches()) {
				FilterResult res = new FilterResult(file,matcher);
				result.add(res);
			}
		}
		
		return result;
	}

	@Override
	public String getCommandName() {
		return "massrename";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Used for renaiming/ transfering files from one directory to another");
		description.add("It takes 4 or 5 arguments");
		return Collections.unmodifiableList(description);
	}

}
