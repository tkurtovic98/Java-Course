package hr.fer.zemris.java.hw13.servlet;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;


/**
 * {@link HttpServlet} that is used to 
 * print a pie chart of any sort 
 * to the screen  
 * @author Tomislav Kurtović
 *
 */
@WebServlet("/reportImage")
public class PieChartServlet extends HttpServlet{
	/**
	 * Used to make a piechart that is displayed on the screen 
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("img/png");
		ServletOutputStream oStream = resp.getOutputStream();
		JFreeChart chart = createChart();
		ImageIO.write(chart.createBufferedImage(500, 400), "png", oStream);
		oStream.close();
	}

	/**
	 * Sets the pie chart values. 
	 * @return new pie chart with set values
	 */
	private JFreeChart createChart() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Linux", 35);
		dataset.setValue("Windows", 40);
		dataset.setValue("Mac", 25);
		
		JFreeChart chart = ChartFactory.createPieChart3D("", dataset, true, true, false);
		
		return chart;
	}
}
