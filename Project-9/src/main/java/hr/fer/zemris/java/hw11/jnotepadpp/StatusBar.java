package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

public class StatusBar extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel position;
	private JLabel length;
	private JLabel time;
	
	private volatile boolean stopTime = false;
	
	public StatusBar() {
		setLayout(new GridLayout(1,0));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		length = new JLabel();
		length.setHorizontalAlignment(SwingConstants.LEFT);
		add(length);
		
		position = new JLabel();
		position.setHorizontalAlignment(SwingConstants.RIGHT);
		add(position);
		
		time = new JLabel();
		time.setHorizontalAlignment(SwingConstants.RIGHT);
		add(time);
		
		setTime();
		
		setVisible(true);
	}
	
	public void setLabelText(int col, int line, int sel) {
		position.setText("Col: " + col + " ln: " + line + " Sel:" + sel );
	}
	
	public void setLengthText(int length) {
		this.length.setText("Length: " + length);
	}
	
	public void stopTime() {
		stopTime = true;
	}
	private void setTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("yyyy/MM/dd HH:mm:ss"));
		
		Thread t = new Thread(()->{
			while(true) {
				try {
					Thread.sleep(500);
				} catch(Exception ex) {}
				if(stopTime) break;
				SwingUtilities.invokeLater(()->{
					time.setText(formatter.format(LocalDateTime.now()));
				});
			}
		});
		t.setDaemon(true);
		t.start();
	}
}
