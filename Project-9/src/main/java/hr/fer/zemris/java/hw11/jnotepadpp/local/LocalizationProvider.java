package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class used as a representation of the {@link ILocalizationProvider} 
 * interface, which is a Singleton that can store information 
 * about the current language
 * @author Tomislav Kurtović
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider{

	/**
	 * Current language
	 */
	private String language;
	/**
	 * bundle to retrieve translations
	 */
	private ResourceBundle bundle;
	
	/**
	 * Instance of this provider
	 */
	private static LocalizationProvider instance = null;
	
	/**
	 * Constructor of this provider
	 */
	private  LocalizationProvider() {
		language = "en";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
	}
	
	/**
	 * Sets the language of the provider
	 * @param language new language
	 */
	public void setLanguage(String language) {
		this.language = language;
		fire();
	}
	/**
	 * returns the instance of this provider 
	 *
	 */
	public static LocalizationProvider getInstance() {
		if(instance == null) {
			instance = new LocalizationProvider();
		}
		return instance;
	}
	
	@Override
	public String getCurrentLanguage() {
		return this.language;
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
	
	@Override
	public void fire() {
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
		super.fire();
	}
	
}
