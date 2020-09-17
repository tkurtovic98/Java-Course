package hr.fer.zemris.java.hw13.servlet.voting;

import java.io.IOException;
import java.util.Map;

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
 * print out a pie chart of the 
 * voting results
 * @author Tomislav Kurtović
 *
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafika extends HttpServlet {

	/**
	 * Used to display the results obtained from the collection 
	 * used to store votes 
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("img/png");
		ServletOutputStream oStream = resp.getOutputStream();
		JFreeChart chart = createChart(req);
		ImageIO.write(chart.createBufferedImage(500, 400), "png", oStream);
		oStream.close();
	}

	/**
	 * Creates the pie chart with the results 
	 * @param req request 
	 * @return new pie chart 
	 */
	private JFreeChart createChart(HttpServletRequest req) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		@SuppressWarnings("unchecked")
		Map<String, String> voteMap  = (Map<String, String>) req.getSession().getAttribute("voteMap");
		
		for(String key : voteMap.keySet()) {
			dataset.setValue(key, Integer.parseInt(voteMap.get(key)));
		}

		JFreeChart chart = ChartFactory.createPieChart3D("", dataset, true, true, false);

		return chart;
	}

}
