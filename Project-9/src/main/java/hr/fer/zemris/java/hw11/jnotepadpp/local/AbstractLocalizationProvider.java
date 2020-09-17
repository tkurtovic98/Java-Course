package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents an {@link ILocalizationProvider}
 * which is used as a parent class for other providers
 * @author Tomislav Kurtović
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * List of listeners
	 */
	private List<ILocalizationListener> listeners;
	
	/**
	 * Constructor of the provider
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		if(!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		if(listeners.contains(l)) {
			listeners.remove(l);
		}
		
	}

	/**
	 * Used to notify all {@link ILocalizationListener}s of language 
	 * change
	 */
	public void fire() {
		for(ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}
}
