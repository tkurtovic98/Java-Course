package hr.fer.zemris.java.webserver;

/**
 * Interface that is used to procces the 
 * request the user wants to be executed. 
 * It is not the same as other actions 
 * because these actions have their own rules 
 * and user can not change them as much
 * as the other actions 
 * @author Tomislav Kurtović
 *
 */
public interface IWebWorker {

	/**
	 * Processes the given request so that 
	 * it writes to the context passed to it.
	 * It can write messages on screen, print parameters of context in different
	 * ways, calculate and more 
	 * @param context context used as output 
	 * @throws Exception if something goes wrong 
	 */
	public void processRequest(RequestContext context) throws Exception;
}
