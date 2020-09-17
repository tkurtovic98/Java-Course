package hr.fer.zemris.java.hw06.shell;

import java.util.Map;

import hr.fer.zemris.java.hw06.shell.commands.Environment;
import hr.fer.zemris.java.hw06.shell.commands.EnvironmentImpl;
import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ShellStatus;

/**
 * Class that represents a Shell program with different 
 * commands a user can use.
 * It instantiates a new environment which is used to 
 * communicate with the user and the 
 * commands. 
 * 
 * @author Tomislav Kurtović
 *
 */
public class MyShell {
	/**
	 * Method used to call all the execution methods of the shell commands.
	 * It runs as long as there is no {@link ShellIOException} thrown by 
	 * the {@link Environment}.
	 * It reads user input and then gets the appropriate command, and if 
	 * command does not exist it continues to get user input.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		StringBuilder builder = new StringBuilder();
		System.out.println("Welcome to MyShell v 1.0");

		Environment env = new EnvironmentImpl();
		Map<String, ShellCommand> commands = env.commands();
		ShellStatus status = ShellStatus.CONTINUE;

		while (status.equals(ShellStatus.CONTINUE)) {
			try {
				System.out.printf("%c", env.getPromptSymbol());
				builder.setLength(0);
				String input = env.readLine();
				
				if(input.isBlank()) {
					continue;
				}
				
				if ("exit".equals(input)) {
					break;
				}
				builder.append(input);
				
				while (input.endsWith(String.valueOf(env.getMorelinesSymbol()))) {
					builder.deleteCharAt(builder.toString().lastIndexOf(env.getMorelinesSymbol()));
					System.out.printf("%c ", env.getMultilineSymbol());
					input = env.readLine().trim();
					builder.append(input);
				}
				
				String action = builder.toString().strip();
				String[] symbol = action.split("\\s+");
				
				if (commands.containsKey(symbol[0])) {
					if(symbol.length == 1) {
						status = commands.get(symbol[0]).executeCommand(env, "");
						continue;
					}
					
					if(symbol[1].equals("help")) {
						action = symbol[1];
					}else {
						action = action.replace(symbol[0], "").trim();
					}
					status = commands.get(symbol[0]).executeCommand(env, action);
				} else {
					System.out.println("Command not found. Try again.");
				}
			} catch (ShellIOException e) {
				System.out.println("ERROR while printing text or getting input. Exiting");
				status = ShellStatus.TERMINATE;
			}
		}
	}
}