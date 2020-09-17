package hr.fer.zemris.java.gui.layouts;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.CalcLayoutException;
import hr.fer.zemris.java.gui.layouts.RCPosition;

class CalcLayoutTest {

	//Testing for 1 on page 2 in hw
	@Test
	void testIfThrows1() {
		JPanel panel = new JPanel(new CalcLayout());
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel("x"), new RCPosition(0, 1)));
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel("y"), new RCPosition(1, 0)));
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel("z"), new RCPosition(6, 1)));
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel("o"), new RCPosition(1, 8)));
	}
	
	//Testing for 2 on page 2 in hw
	@Test
	void testIfThrows2() {
		JPanel panel = new JPanel(new CalcLayout());
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel("x"), new RCPosition(1, 4)));
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel("y"), new RCPosition(1, 2)));
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel("z"), new RCPosition(1, 3)));
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel("o"), new RCPosition(1, 5)));
	}
	
	//Testing for 3 on page 2 in hw
	@Test
	void testIfThrows3() {
		JPanel panel = new JPanel(new CalcLayout());
		assertThrows(CalcLayoutException.class, () ->{
			panel.add(new JLabel("x"), new RCPosition(1, 1));
			panel.add(new JLabel("y"), new RCPosition(1, 1));
		});
	}
	@Test
	void testIfThrows3a() {
		JPanel panel = new JPanel(new CalcLayout());
		panel.add(new JLabel("x"), new RCPosition(4, 4));
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel("y"), new RCPosition(4, 4)));
	}
	@Test
	void testIfThrows3b() {
		JPanel panel = new JPanel(new CalcLayout());
		panel.add(new JLabel("x"), new RCPosition(5, 3));
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel("y"), new RCPosition(5, 3)));
	}
	
}
