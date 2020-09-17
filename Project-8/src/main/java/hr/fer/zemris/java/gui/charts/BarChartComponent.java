package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Class that is used to render all of the 
 * information contained in the {@link BarChart} 
 * on the screen without contining any components
 * @author Tomislav Kurtović
 *
 */
public class BarChartComponent extends JComponent {
	private static final long serialVersionUID = 1L;
	/**
	 * bar chart
	 */
	private BarChart barChart;
	/**
	 * height of y axis on screen
	 */
	private int height;
	/**
	 * Width of one rectangle
	 */
	private int width;
	/**
	 * Start x  (0,0)
	 */
	private int startX;
	/**
	 * Start y (0,0)
	 */
	private int startY;

	/**
	 * constructor
	 * @param barChart
	 */
	public BarChartComponent(BarChart barChart) {
		super();
		this.barChart = barChart;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		paintY(g);
		paintX(g);
		paintTriangles(g);
		paintColumns(g);
	}

	/**
	 * paints the arrows on top of the axis
	 * @param g
	 */
	private void paintTriangles(Graphics g) {
		//Y-axis
		g.drawLine(startX - 10, 20, startX + 10, 20);
		g.drawLine(startX - 10,  20, startX,  0);
		g.drawLine(startX + 10,  20, startX,  0);
		
		//X.axis
		g.drawLine(getWidth() - 20, startY + 10 , getWidth() - 20, startY - 10);
		g.drawLine(getWidth() - 20, startY + 10, getWidth(), startY);
		g.drawLine(getWidth()-  20, startY - 10, getWidth(), startY);
	}

	/**
	 * renders the rectangles on the screen
	 * @param g
	 */
	private void paintColumns(Graphics g) {
		int position = startX + 2;
		int gaps = ((startY - 50) * barChart.getGap())/(barChart.getyMax()-barChart.getyMin());
	
		for(XYValue value : barChart.getListOfValues()) {
			g.setColor(Color.BLUE);
			g.fill3DRect(position +((value.getX()-1)* width), startY - (value.getY() * gaps/barChart.getGap()) - 5, 
					width - 2, value.getY()*gaps/barChart.getGap() + 5, true);
		}
	}
	/**
	 * Paints everythign assosiated to the x axis
	 * @param g
	 */
	private void paintX(Graphics g) {
		g.drawString(barChart.getxDescr(), getWidth()/2 - 4 *startX, getHeight() - height/2);
		g.drawLine(startX, startY, getWidth(), startY);
		
		int numberOfColumns = barChart.getListOfValues().size();
		
		int widths = (getWidth()-2*startX) / numberOfColumns;
		int initialPosition = widths/2 + startX;
		for(int i = 1; i <= numberOfColumns; i++) {
			g.drawString(""+i+"",initialPosition, startY + 15 );
			initialPosition += widths;
		}
		this.width = widths;
	}
	/**
	 * Paints everythign assosiated to the y axis
	 * @param g
	 */
	private void paintY(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		
		String barChartMaxY = String.valueOf(barChart.getyMax());
		int numberAlignmentX = g.getFontMetrics().stringWidth(barChartMaxY)+5;
		
		startX = numberAlignmentX + 5*barChartMaxY.length();
		startY = getHeight() - 50;
		
		AffineTransform defaultAt = graphics.getTransform();
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.rotate(-Math.PI / 2);
		graphics.setTransform(affineTransform);
		graphics.drawString(barChart.getyDescr(), -getHeight()/2, numberAlignmentX/2);
		
		graphics.setTransform(defaultAt);
		graphics.drawLine(startX , 0, startX, startY);
		
		int height = startY;
		int gaps = ((height -50 )* barChart.getGap())/(barChart.getyMax()-barChart.getyMin());
		for(int i = barChart.getyMin(); i <= barChart.getyMax() ; i+=barChart.getGap()) {
			String lengthOfDigit = String.valueOf(i);
			graphics.drawString(""+i+"", numberAlignmentX - 5* lengthOfDigit.length() + 5, height);
			if(i != barChart.getyMin()) {
				graphics.drawString("-", startX, height);
			}
			height-= gaps;
		}
		
		this.height = height;
	}
}
