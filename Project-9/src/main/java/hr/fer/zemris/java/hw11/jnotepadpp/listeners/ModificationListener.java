package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import javax.swing.ImageIcon;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;

public class ModificationListener implements SingleDocumentListener {
	private ImageIcon mod;
	private ImageIcon notMod;
	private DefaultMultipleDocumentModel instance;
	
	
	public  ModificationListener(ImageIcon mod, ImageIcon notMod, DefaultMultipleDocumentModel model) {
		this.mod = mod;
		this.notMod = notMod;
		this.instance = model;
	}
	
	@Override
	public void documentModifyStatusUpdated(SingleDocumentModel model) {
		instance.setIconAt(instance.getSelectedIndex(), model.isModified() ? mod : notMod);
	}

	@Override
	public void documentFilePathUpdated(SingleDocumentModel model) {
		instance.setToolTipTextAt(instance.getSelectedIndex(), model.getFilePath().toString());
		instance.setTitleAt(instance.getSelectedIndex(), model.getFilePath().getFileName().toString());
	}
}
