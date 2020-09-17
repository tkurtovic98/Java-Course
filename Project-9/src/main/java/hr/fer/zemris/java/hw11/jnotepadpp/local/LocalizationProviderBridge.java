package hr.fer.zemris.java.hw11.jnotepadpp.local;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * Class that is used to enable the garbage collector to 
 * do its job once the {@link JNotepadPP} frame is closed.
 *
 * @author Tomislav Kurtović
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Sets connectivity status of bridge
	 */
	private boolean connected;
	/**
	 * language of provider 
	 */
	private String language;
	/**
	 * reference to provider 
	 */
	private LocalizationProvider provider;
	/**
	 * reference to listener of this bridge
	 */
	private ILocalizationListener listener;
	
	/**
	 * Constructor
	 * @param provider provider of the bridge
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = (LocalizationProvider) provider;
		language = provider.getCurrentLanguage();
	}
	
	/**
	 * Connects itself with the frame and other providers 
	 */
	public void connect() {
		if(connected) return;
		provider.setLanguage(language);
		listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				LocalizationProviderBridge.this.fire();
			}
		};
		provider.addLocalizationListener(listener);
		connected = true;
	}
	/**
	 * Disconnects itself from the frame and other providers 
	 */
	public void disconnect() {
		language = getCurrentLanguage();
		provider.removeLocalizationListener(listener);
		connected = false;
	}
	
	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return provider.getCurrentLanguage();
	}

}
