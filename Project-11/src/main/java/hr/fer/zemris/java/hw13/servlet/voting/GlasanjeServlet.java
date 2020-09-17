package hr.fer.zemris.java.hw13.servlet.voting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * {@link HttpServlet} that is used to 
 * store values used for voting
 * @author Tomislav Kurtović
 *
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Used to read from file that contains information
	 * about the voting candidates and store them 
	 * in appropriate collections
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		Map<String, String> bandMap = new LinkedHashMap<>();
		Map<String, String> linkMap = new LinkedHashMap<>();
		
		for(String line : lines) {
			String[] splitedLine = line.split("\t");
			bandMap.put(splitedLine[0], splitedLine[1]);
			linkMap.put(splitedLine[1], splitedLine[2]);
		}

		req.setAttribute("bandMap", bandMap);
		req.getSession().setAttribute("bandMap", bandMap);
		req.getSession().setAttribute("linkMap", linkMap);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
