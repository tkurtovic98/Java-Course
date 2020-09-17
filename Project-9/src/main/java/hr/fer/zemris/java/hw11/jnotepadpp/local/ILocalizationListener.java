package hr.fer.zemris.java.hw11.jnotepadpp.local;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * Interface used to get changes of 
 * languages in the {@link JNotepadPP} 
 * program 
 * @author Tomislav Kurtović
 *
 */
public interface ILocalizationListener {
	
	/**
	 * Called whenever there is a change in localization settings
	 */
	void localizationChanged();
}
