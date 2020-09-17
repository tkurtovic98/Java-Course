package hr.fer.zemris.java.hw06.shell.util;

import hr.fer.zemris.java.hw06.shell.commands.Environment;
import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;


/**
 * Util class used by different {@link ShellCommand}.
 * It contains methods that are used to split the command 
 * arguments.
 * Some commands split their arguments differently 
 * @author Tomislav Kurtović
 */

public class UtilCommands {
	
		
		/**
		 * Used to split command argument in a String array
		 * @param arguments arguments of command to be splitted
		 * @return array of  arguments
		 */
		public static String[] split(String arguments) {
			return arguments.split("\\s+");
		}
		
		
		/**
		 * Used to get the end quote of a quoted path
		 * 
		 * @param arguments arguments of commmand 
		 * @param env environment of shell
		 * @param isDoubleQuote flag to see if arguments are double quoted or not
		 * @return index of closing double or single quote
		 */
		public static int getQuotedPath(String arguments, Environment env, boolean isDoubleQuote) {
			char[] quote = arguments.trim().toCharArray();
			int index = isDoubleQuote ? arguments.indexOf("\"") + 1 : arguments.indexOf("\'") + 1;
			StringBuilder builder = new StringBuilder();
			char quoteExit = isDoubleQuote ? '\"' : '\'';
			
			while(index < arguments.length() && quote[index] != quoteExit){
				if(quote[index] == '\\' && isDoubleQuote) {
					if(!isEscape(quote,index)) {
						builder.append(quote[index]);
					}
					index++;
				}
				builder.append(quote[index]);
				index++;
			}
			
			if(quote[index] != quoteExit) {
				return -1;
			}
			
			if(index < arguments.length() - 1 && !Character.isWhitespace(quote[index + 1])) {
				env.writeln("There must be a space after closing the quotes");
			}
			
			return index;
		}
		
		
		/**
		 * Checks if escape sequence should be treated as ecape sequence or
		 * regular characters
		 * @param quote array of characters
		 * @param index current index
		 * @return true if escape sequence has "\\" or "\"" and false if not
		 */
		private static boolean isEscape(char[] quote, int index) {
			if(quote[index + 1] == '\\' || quote[index + 1] == '\"') {
				return true;
			}
			return false;
		}
	
}
