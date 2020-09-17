package hr.fer.zemris.java.custom.collections;

/**
 * Interface used for testing 
 * @author Tomislav Kurtović
 *
 */
public interface Tester <T> {
	/**
	 * Testing method
	 * @param obj object to test
	 * @return true if test is true,false oterwise
	 */
	boolean test(T obj);
}
