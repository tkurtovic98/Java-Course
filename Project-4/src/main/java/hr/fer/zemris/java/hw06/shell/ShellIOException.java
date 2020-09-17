package hr.fer.zemris.java.hw06.shell;

import hr.fer.zemris.java.hw06.shell.commands.Environment;

/**
 * Exception thrown by {@link Environment} if something foes wrong
 * @author Tomislav Kurtović
 *
 */
public class ShellIOException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ShellIOException() {
		super();
	}

	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShellIOException(String message) {
		super(message);
	}

	public ShellIOException(Throwable cause) {
		super(cause);
	}
}
