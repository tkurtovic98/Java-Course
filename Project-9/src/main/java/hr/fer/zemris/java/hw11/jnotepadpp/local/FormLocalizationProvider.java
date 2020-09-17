package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * Class that represents an {@link ILocalizationProvider} 
 * that establishes a connection with the 
 * {@link LocalizationProviderBridge} in order to 
 * enable the Garbage collector to do its job after 
 * the frame of the {@link JNotepadPP} is closed 
 * @author Tomislav Kurtović
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge{

	/**
	 * Constructor of this provider 
	 * @param provider another provider that is used to get language changes and store language info
	 * @param frame framer to which the provider is attached to
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		super.connect();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				deregister();
			}
		});
	}

	protected void deregister() {
		super.disconnect();
	}
}
