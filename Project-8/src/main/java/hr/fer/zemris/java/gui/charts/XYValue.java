package hr.fer.zemris.java.gui.charts;
/**
 * Class used to represent X and Y values of 
 * rectangle on chart
 * @author Tomislav Kurtović
 *
 */
public class XYValue {
	/**
	 * horizontal
	 */
	int x;
	/**
	 * vertical
	 */
	int y;
	
	/**
	 * Construcotr
	 * @param x
	 * @param y
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	/**
	 * X getter
	 * @return
	 */
	public int getX() {
		return x;
	}
	/**
	 * y getter
	 * @return
	 */
	public int getY() {
		return y;
	}
}
