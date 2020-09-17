package hr.fer.zemris.java.hw06.shell.scripting;

public class NameBuilderParserException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NameBuilderParserException() {
		super();
	}

	public NameBuilderParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public NameBuilderParserException(String message) {
		super(message);
	}

	public NameBuilderParserException(Throwable cause) {
		super(cause);
	}
}
