package searching.slagalica;

import java.util.Arrays;

/**
 * Class used to store information about
 * the configuration of the jigsaw
 * @author Tomislav Kurtović
 *
 */
public class KonfiguracijaSlagalice {
	
	/**
	 * array of numbers
	 */
	private int[] configuration;

	/**
	 * Default constructor
	 * @param configuration
	 */
	public KonfiguracijaSlagalice(int[] configuration) {
		super();
		this.configuration = configuration;
	}

	/**
	 * Returns the configuration array
	 * @return configuration array
	 */
	public int[] getPolje() {
		return Arrays.copyOf(configuration,configuration.length);
	}
	
	/**
	 * Returns index of '0'
	 * @return
	 */
	public int indexOfSpace() {
		for(int i : configuration) {
			if(configuration[i] == 0) return i;
		}
		return -1;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		int index = indexOfSpace();
		
		for(int i = 0; i < configuration.length; i++) {
			if(i % 3 == 0) {
				builder.append("\n");
			}
			if(i == index) {
				builder.append("*");
				continue;
			}
			
			builder.append(configuration[i]);
		}
		
		return builder.toString();
	}
}
