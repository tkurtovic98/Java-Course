package hr.fer.zemris.java.gui.charts;

import java.util.List;
/**
 * Class that is used to hold 
 * information about the chart later to
 * be render on the screen by the {@link BarChartComponent}
 * @author Tomislav Kurtović
 *
 */
public class BarChart {

	/**
	 * values to be rendered
	 */
	private List<XYValue> listOfValues;
	/**
	 * description of x axis
	 */
	private String xDescr;
	/**
	 * description of y axis
	 */
	private String yDescr;
	/**
	 * mimium render y 
	 */
	private int yMin;
	/**
	 * max renderer y
	 */
	private int yMax;
	/*
	 * gap between 2 y values
	 */
	private int gap;
	/**
	 * constructor
	 * @param listOfValues
	 * @param xDescr
	 * @param yDescr
	 * @param yMin
	 * @param yMax
	 * @param gap
	 */
	public BarChart(List<XYValue> listOfValues, String xDescr, String yDescr, int yMin, int yMax, int gap) {
		super();
		checkValues(yMin, yMax, listOfValues);
		this.listOfValues = listOfValues;
		this.xDescr = xDescr;
		this.yDescr = yDescr;
		this.yMin = yMin;
		this.yMax = yMax;
		this.gap = gap;
	}
/**
 * Checks if values are valid
 * @param yMin2
 * @param yMax2
 * @param listOfValues2
 */
	private void checkValues(int yMin2, int yMax2, List<XYValue> listOfValues2) {
		if(yMin2 < 0) {
			throw new IllegalArgumentException("Number can not be negative!");
		}
		if(yMax2 <= yMin2) {
			throw new IllegalArgumentException("Max has to be greater than min!");
		}
		for(XYValue value : listOfValues2) {
			if(value.getY() < yMin2) {
				throw new IllegalArgumentException("Found smaller y than minimum!");
			}
		}
	}
	/**
	 * Retturns the list of positions
	 * @return
	 */
	public List<XYValue> getListOfValues() {
		return listOfValues;
	}
	/**
	 * Retturns x axis description
	 * @return
	 */
	public String getxDescr() {
		return xDescr;
	}
	/**
	 * Retturns y axis description
	 * @return
	 */
	public String getyDescr() {
		return yDescr;
	}
	/**
	 * Retturns minimum value
	 * @return
	 */
	public int getyMin() {
		return yMin;
	}
	/**
	 * Retturns max value
	 * @return
	 */
	public int getyMax() {
		return yMax;
	}
	/**
	 * Retturns gap
	 * @return
	 */
	public int getGap() {
		return gap;
	}
}
