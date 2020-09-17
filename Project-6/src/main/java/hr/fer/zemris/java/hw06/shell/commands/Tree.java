package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.util.UtilCommands;

/**
 * A {@link ShellCommand} that opens given directory and writes its content to console
 * in a tree form.
 * 
 * @author Tomislav Kurtović
 *
 */
public class Tree implements ShellCommand{

	/**
	 * Used to write directory content to console in tree form.
	 * It gets the path of the directory and opens it
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isBlank()) {
			env.writeln("Invalid input for tree command!");
			return ShellStatus.CONTINUE;
		}
		boolean isDoubleQuote = false;
		if(arguments.startsWith("\"") || arguments.startsWith("\'")){
			if(arguments.startsWith("\"")) {
				isDoubleQuote = true;
			}
			int indexOfQuote = UtilCommands.getQuotedPath(arguments, env, isDoubleQuote );
			arguments = arguments.substring(1, indexOfQuote);
		} 
		Path path = Paths.get(arguments).toAbsolutePath();
		
		if(path.toFile().isFile()) {
			env.writeln("Can not get file tree from file, only directory");
			return ShellStatus.CONTINUE;
		}
		
		if(!path.toFile().exists()) {
			env.writeln("Directory does not exist in current working directory");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.walkFileTree(path, new WalkTree(env));
		}catch(IOException ex){
			env.writeln("Unable to get file tree");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Prints file and directory tree");
		return Collections.unmodifiableList(description);
	}
	
	/**
	 * Inner class used to print the tree of the specified
	 * directory 
	 * @author Tomislav Kurtović
	 *
	 */
	private static class WalkTree implements FileVisitor<Path>{
		/**
		 * Level at which the visitor is at
		 */
		private int level;
		
		/**
		 * Environment of shell
		 */
		private Environment env;
		
		/**
		 * Constructor 
		 * @param env
		 */
		private WalkTree(Environment env) {
			this.env = env;
		}
		
		/**
		 * Before visiting a direcrory the level is incremented by 1 to ensure correct printing
		 */
		
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			env.writeln(" ".repeat(2*level) + dir.getFileName());
			level++;
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Prints file with correct spacing
		 */
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.writeln(" ".repeat(2*level) + file.getFileName());
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Decrements level by 1 when directory is left for correct printing
		 */
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}
	}
}
