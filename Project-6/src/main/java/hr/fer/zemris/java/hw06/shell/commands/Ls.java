package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.util.UtilCommands;

/**
 * A {@link ShellCommand} that opens given directory and writes its content to console.
 * 
 * @author Tomislav Kurtović
 *
 */
public class Ls implements ShellCommand {
	
	/**
	 * Prints info of each file or directory to screen
	 * If directory (d), if readable (r), if writable (w), if executable(x)
	 * Prints date and time of creation and file name
	 * 
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isBlank()) {
			env.writeln("Blank argument not supported by ls command!");
			return ShellStatus.CONTINUE;
		}
		boolean isDoubleQuote = false;
		if(arguments.startsWith("\"") || arguments.startsWith("\'")){
			if(arguments.startsWith("\"")) {
				isDoubleQuote = true;
			}
			int indexOfQuote = UtilCommands.getQuotedPath(arguments, env, isDoubleQuote );
			arguments = arguments.substring(1, indexOfQuote);
		} else if(UtilCommands.split(arguments).length != 1) {
			env.writeln("Invalid number of arguments");
			return ShellStatus.CONTINUE;
		}
		
		Path path = Path.of(arguments).toAbsolutePath();
		
		if(path.toFile().isFile()) {
			env.writeln("Invalid argument for ls command, directory needed");
			return ShellStatus.CONTINUE;
		}
		
		File[] files = path.toFile().listFiles();
		if(files == null) {
			env.writeln("Path not found");
			return ShellStatus.CONTINUE;
		}
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0, len = files.length; i < len; i++) {
			builder.setLength(0);
			builder = info(builder,files[i]);
			builder = size(builder,files[i]);
			builder = dateTime(builder,files[i]);
			if(builder == null) {
				env.writeln("Unable to get date and time!");
			}
			builder.append(files[i].getName());
			env.writeln(builder.toString());
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Used to get the date and time of file creation
	 * @param builder string builder that represents one line of output
	 * @param file file to read info 
	 * @return stringbuilder with appended date and time
	 */
	private StringBuilder dateTime(StringBuilder builder, File file) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = file.toPath();
		BasicFileAttributeView faView = Files.getFileAttributeView(
		path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
		);
		BasicFileAttributes attributes;
		try {
			attributes = faView.readAttributes();
		} catch (IOException e) {
			return null;
		}
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		builder.append(formattedDateTime + " ");
		return builder;
	}

	/**
	 * Used to get size of file and append it to stringbuilder
	 * @param builder string builder that represents one line of output
	 * @param file file to read info 
	 * @return stringbuilder with appended size of file in bytes
	 */
	private StringBuilder size(StringBuilder builder, File file) {
		long size = file.length();
		int alignment = 10 - String.valueOf(size).length();
		builder.append(" ".repeat(alignment));
		builder.append(size + " ");
		return builder;
	}

	
	/**
	 * Used to get information about the file and append it to stringbuilder
	 * @param builder string builder that represents one line of output
	 * @param file file to read info 
	 * @return stringbuilder with appended information of file in bytes
	 */
	private StringBuilder info(StringBuilder builder, File file) {
		builder.append(file.isDirectory() ? "d" : "-");
		builder.append(file.canRead() ? "r" : "-");
		builder.append(file.canWrite() ? "w" : "-");
		builder.append(file.canExecute() ? "x " : "- ");
		return builder;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Prints information about directory or file");
		description.add("If directory (d), if readable (r), if writable (w), if executable(x)");
		description.add("Prints date and time of cration and file name");
		return Collections.unmodifiableList(description);
	}
}
