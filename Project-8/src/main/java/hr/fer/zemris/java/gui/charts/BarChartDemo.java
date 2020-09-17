package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class used to represent demo programme of the chart 
 * rendering programme
 * @author Tomislav Kurtović
 *
 */
public class BarChartDemo extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public BarChartDemo() {
		setLocation(20,20);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("my graph");
		setSize(500,500);
	}
	
	/**
	 * Main method that takes in path to file as argument
	 * @param args path to document
	 */
	public static void main(String[] args)  {
		if(args.length != 1) {
			System.out.println("Invalid number of arguments !");
			return;
		}
		
		SwingUtilities.invokeLater(() -> {
			BarChart chart = null;
			
			try {
				Scanner scanner = new Scanner(Files.newInputStream(Paths.get(args[0])));
				String xDescr = scanner.nextLine();
				String yDescr = scanner.nextLine();
				String[] values = scanner.nextLine().split("\\s+");
				int yMin = Integer.parseInt(scanner.nextLine());
				int yMax = Integer.parseInt(scanner.nextLine());
				int gap = Integer.parseInt(scanner.nextLine());
				scanner.close();
				
				List<XYValue> list = new ArrayList<>();
				
				for(String str : values) {
					list.add(new XYValue(Integer.parseInt(str.split(",")[0].strip()), 
							Integer.parseInt(str.split(",")[1].strip())));
				}
				
				chart = new BarChart(list, xDescr, yDescr, yMin, yMax, gap);
			} catch (IOException | NumberFormatException e) {
				System.out.println("Unable to read from file!");
			}
			
			JFrame demo = new BarChartDemo();
			demo.setLayout(new BorderLayout());
			demo.add(new JLabel(args[0]), BorderLayout.PAGE_START);
			demo.add(new BarChartComponent(chart), BorderLayout.CENTER);
			demo.setVisible(true);
		});
		
	}

}
