package hr.fer.zemris.java.hw17.trazilica.commands;

/**
 * Exception that is thrown whenever
 * something goes wrong when 
 * executing methods from the 
 * {@link QuerySearchCommand}, {@link ResultsSearchCommand},
 * {@link TypeSearchCommand} or any other {@link SearchCommand}.
 * @author Tomislav Kurtović
 *
 */
public class SearchCommandException extends Exception {
	private static final long serialVersionUID = 1L;

	public SearchCommandException() {
		super();
	}

	public SearchCommandException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SearchCommandException(String message, Throwable cause) {
		super(message, cause);
	}

	public SearchCommandException(String message) {
		super(message);
	}

	public SearchCommandException(Throwable cause) {
		super(cause);
	}
}
