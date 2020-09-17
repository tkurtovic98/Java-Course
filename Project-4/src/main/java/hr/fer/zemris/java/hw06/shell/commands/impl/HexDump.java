package hr.fer.zemris.java.hw06.shell.commands.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.commands.Environment;
import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.UtilCommands;

/**
 * A {@link ShellCommand} that opens given file and writes its content to console
 * in hex format.
 * 
 * @author Tomislav Kurtović
 *
 */
public class HexDump implements ShellCommand{

	
	/**
	 * Used  to print hex string of given file to screen.
	 * On the left side the number of read bytes is printed and
	 * on the far right the actual content of the file is printed using
	 * default charset ( All char values less than 32 and greater than 127 are 
	 * replaced by '.')
	 * 
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(UtilCommands.split(arguments).length != 1) {
			env.writeln("Invalid number of arguments for hexdump command");
			return ShellStatus.CONTINUE;
		}
		
		File file = new File(arguments);
		if(file.isDirectory()) {
			env.writeln("Argument must not be directory");
			return ShellStatus.CONTINUE;
		}
		
		if(!file.canRead()) {
			env.writeln("File is not readable");
			return ShellStatus.CONTINUE;
		}
		
		StringBuilder builder = new StringBuilder();
		try(InputStream is = Files.newInputStream(file.toPath().toAbsolutePath())){
			byte[] bytes = new byte[16];
			int i = 0;
			while(true) {
				int k = 1;
				int r = is.read(bytes);
				if(r < 1) break;
				
				builder.append(String.format("%08d: ", i*10));
				for(byte b : bytes) {
					builder.append(String.format(" %02X" , b));
					if(k % 8 == 0) {
						builder.append("|");
					}
					k++;
				}
				builder.append(" ");
				char[] lineInChars = new String(bytes).trim().toCharArray();
				for(char c : lineInChars) {
					if(Character.valueOf(c) < 32 || Character.valueOf(c) > 127) {
						builder.append(".");
					} else {
						builder.append(c);
					}
				}
				env.writeln(builder.toString());
				builder.setLength(0);
				i++;
			}
		} catch (IOException e) {
			env.writeln("Unable to dump hex");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Prints out a hex output of given file");
		description.add("Takes one argument, the file name");
		description.add("File must be in current directory");
		return Collections.unmodifiableList(description);
	}
}
