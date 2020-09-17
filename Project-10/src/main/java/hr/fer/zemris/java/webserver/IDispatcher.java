package hr.fer.zemris.java.webserver;

/**
 * Dispacher used to dispatch the 
 * actions from the given path 
 * @author Tomislav Kurtović
 *
 */
public interface IDispatcher {
	/**
	 * Dispaches request from file that is 
	 * specified by user and acts according 
	 * to the request 
	 * @param urlPath url path of actions 
	 * @throws Exception if something foes wrong 
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
