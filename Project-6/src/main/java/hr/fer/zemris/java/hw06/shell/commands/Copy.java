package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.util.UtilCommands;

/**
 * A {@link ShellCommand} that opens given file and copies ts content to another file
 * or directory.
 * 
 * @author Tomislav Kurtović
 *
 */
public class Copy implements ShellCommand{
	/**
	 * Used as flag if given path is in double quotes
	 */
	private boolean isDoubleQuote ;
	
	/**
	 * Used to copy data from source file to destination file.
	 * If the source is a directory, the command return to the main
	 * shell.
	 * If the destination file exists, user is asked if it can 
	 * be overwriten. 
	 * If destination is directory, than file is copied to it
	 * with same name as source file
	 */
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Path source;
		Path destination;
		isDoubleQuote = false;
		
		if(arguments.contains("\"") || arguments.contains("\'")) {
			if(arguments.contains("\"")) {
				isDoubleQuote = true;
			}
			
			int indexOfOpeningQuote = arguments.indexOf(isDoubleQuote ? '\"' : '\'');
			int indexOfClosingQuote = UtilCommands.getQuotedPath(arguments, env, isDoubleQuote);
			
			if(indexOfOpeningQuote == 0 && indexOfClosingQuote + 2 > arguments.length()) {
				env.writeln("Invalid number of arguments for copy command");
				return ShellStatus.CONTINUE;
			}
			//Is source quoted or not or both quoted 
			if(indexOfOpeningQuote == 0) {
				String quoteSource = arguments.substring(1,indexOfClosingQuote).trim();
				source = UtilCommands.resolve(env, Paths.get(quoteSource));
				String dest = arguments.substring(indexOfClosingQuote+1).trim();
				dest = dest.substring(1,dest.length()-1);
				destination =UtilCommands.resolve(env, Paths.get(dest));
			} else {
				//Destination is quoted
				String src = arguments.substring(0,indexOfOpeningQuote).trim();
				source = UtilCommands.resolve(env, Paths.get(src));
				String quoteDest = arguments.substring(indexOfOpeningQuote + 1,indexOfClosingQuote).trim();
				destination = UtilCommands.resolve(env, Paths.get(quoteDest));
			}
		} else {
			String[] splitted = UtilCommands.split(arguments);
			if(splitted.length != 2) {
				env.writeln("Invalid number of arguments for copy command");
				return ShellStatus.CONTINUE;
			}
			source = UtilCommands.resolve(env, Paths.get(splitted[0]));
			destination = UtilCommands.resolve(env, Paths.get(splitted[1]));
		}
	
		if(source.toFile().isDirectory()) {
			env.writeln("Source must be one file, no directories allowed!");
			return ShellStatus.CONTINUE;
		}
		
		if(destination.toFile().isDirectory()) {
			 File copiedFile = new File(destination.toFile(),source.toFile().getName());
			 if(!write(source, copiedFile.toPath())) {
				 env.write("Unable to copy file to directory");
			 }
			 return ShellStatus.CONTINUE;
		}
		
		if(destination.toFile().exists()) {
			env.writeln("Am allowed to overwrite file?  Y/N");
			String allowed = env.readLine();
			if(allowed.equals("Y") || allowed.equals("y")) {
				if(!write(source,destination)) {
					env.writeln("Failed to overwrite file!");
				}
			} else {
				env.writeln("Canceled");
			}
			return ShellStatus.CONTINUE;
		}
		
		if(!write(source,destination)) {
			env.writeln("Failed to write to file");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Used to write source data to destination file or directory
	 * @param source source file 
	 * @param destination destination directory or file
	 * @return true if everything went as planned, false otherwise
	 */
	private boolean write(Path source, Path destination) {
		try(InputStream input = Files.newInputStream(source);
				OutputStream output = Files.newOutputStream(destination)){
			byte[] buff = new byte[1024];
			while(true) {
				int r = input.read(buff);
				if(r < 1) break;
				output.write(buff,0,r);
			}
			output.flush();
		} catch(IOException ex) {
			return false;
		}
		return true;
	}
	
	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Copies data from source file do destination file");
		description.add("First argument is source path, second is destination path");
		description.add("Both arguments mandatory and paths can be written in quotes");
		return Collections.unmodifiableList(description);
	}
}
