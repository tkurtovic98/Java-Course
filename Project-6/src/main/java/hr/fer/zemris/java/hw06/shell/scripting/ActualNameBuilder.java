package hr.fer.zemris.java.hw06.shell.scripting;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.commands.added.FilterResult;

/**
 * Class used to construct a new
 * {@link}NameBuilder that holds references 
 * to other such objects 
 * @author Tomislav Kurtović
 *
 */
public class ActualNameBuilder implements NameBuilder {
	/**
	 * List of references
	 */
	private List<NameBuilder> nameBuilders;
	/**
	 * Default constructor
	 * @param nameBuilders list of references
	 */
	public ActualNameBuilder(List<NameBuilder> nameBuilders) {
		this.nameBuilders = nameBuilders;
	}
	
	/**
	 * Calls all the other NameBuilders' execute method
	 */
	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		for(NameBuilder builder : nameBuilders) {
			builder.execute(result, sb);
		}
	}

}
