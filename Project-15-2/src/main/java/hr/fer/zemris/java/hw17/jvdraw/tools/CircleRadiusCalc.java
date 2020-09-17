package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Point;

/**
 * Class that represents holder
 * of static method to calculate radius
 * @author Tomislav Kurtović
 *
 */
public class CircleRadiusCalc {
	public static int calcRadius(Point start, Point end) {
		double x = Math.pow(end.x - start.x, 2);
		double y = Math.pow(end.y - start.y, 2);
		return (int) Math.sqrt(x + y);
	}
}
