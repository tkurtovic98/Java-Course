package hr.fer.zemris.java.hw11.jnotepadpp.local;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * Interface that represents a provider 
 * which can store information about 
 * the current working lanuguage of the {@link JNotepadPP}
 * editor 
 * @author Tomislav Kurtović
 *
 */
public interface ILocalizationProvider {

	/**
	 * Adds {@link ILocalizationListener} to the provider
	 * @param l added {@link ILocalizationListener}
	 */
	void addLocalizationListener(ILocalizationListener l);
	/**
	 * Removes {@link ILocalizationListener} from the provider
	 * @param l removed {@link ILocalizationListener}
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * Gets the translated string from the translation documents
	 * @param key key of value to translate 
	 * @return translated text
	 */
	String getString(String key);
	
	/**
	 * Returns current language of editor
	 * @return current language
	 */
	String getCurrentLanguage();
	
}
