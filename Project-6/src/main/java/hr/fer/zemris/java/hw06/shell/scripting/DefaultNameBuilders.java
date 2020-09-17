package hr.fer.zemris.java.hw06.shell.scripting;

/**
 * Class that holds methods used to consturct new 
 * {@link NameBuilder} objects which perform various actions
 * @author Tomislav Kurtović
 *
 */
public class DefaultNameBuilders {

	/**
	 * Used to construct NB object with text appended to its
	 * string builder
	 * @param t text to append
	 * @return new NameBuilder
	 */
	static NameBuilder text(String t) {
		return (result,builder)->{
			builder.append(t);
			};
	}
	/**
	 * Used to construct NB object with group text appended to its
	 * string builder
	 * @param t text to append
	 * @return new NameBuilder
	 */
	static NameBuilder group(int index) {
		return (result,builder) ->{
			builder.append(result.group(index));
		};
	} 
	/**
	 * Used to construct NB object with group text with added
	 * specifications appended to its
	 * string builder
	 * @param t text to append
	 * @return new NameBuilder
	 */
	static NameBuilder group(int index, char padding, int minWidth) {
		return (result,builder)->{
			String group = result.group(index);
			if(group.length()>= minWidth) {
				builder.append(group);
			}else {
				builder.append(String.valueOf(padding).repeat(minWidth-group.length()));
				builder.append(group);
			}
		};
	} 
	
	
}
