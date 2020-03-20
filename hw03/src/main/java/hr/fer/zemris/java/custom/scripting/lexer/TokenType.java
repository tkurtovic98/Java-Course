package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Different Token Types
 * @author TomislavKurtović
 *
 */
public enum TokenType {
	BEGINOFTAG,
	ENDOFTAG,
	TAGNAME,
	END,
	TEXT,
	UNKNOWN,
	VARIABLE,
	OPERATOR,
	FUNCTION,
	STRING,
	NUMBER,
	EOF
}
