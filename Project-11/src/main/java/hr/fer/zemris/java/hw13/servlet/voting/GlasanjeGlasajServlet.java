package hr.fer.zemris.java.hw13.servlet.voting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link HttpServlet} that is used to 
 * update the voting information 
 * in the voting file
 * @author Tomislav Kurtović
 *
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Used to obtain the voting data and update the same data
	 * accordingly given the user sent values (clicks)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path filePath = Paths.get(fileName);
		
		if(!Files.exists(filePath)) {
			Files.createFile(filePath);
		}
		
		updateInfo(filePath, req.getParameter("id"));
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

	/**
	 * Updates the info in the file that stores all the voting values 
	 * @param filePath path of voting file
	 * @param id id of sent click
	 * @throws IOException if something  goes wrong while writing to file
	 */
	private void updateInfo(Path filePath, String id) throws IOException {
		List<String> lines = Files.readAllLines(filePath);
		Map<String, Integer> map = new HashMap<>();
		
		//If document is empty
		if(lines.isEmpty()) {
			Files.writeString(filePath, id + "\t1");
			return;
		}
		
		//Getting the lines in a map
		for(String line : lines) {
			String[] splitedLine = line.split("\t");
			map.put(splitedLine[0], Integer.parseInt(splitedLine[1]));
		}
		
		//Go through map and increment vote count for the id
		if(map.containsKey(id)) {
			map.put(id, map.get(id)+1);
		} else {
			map.put(id, 1);
		}
		
		//Sort the values by vote count
		List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
		entries.sort((e1,e2)-> e2.getValue().compareTo(e1.getValue()));
		
		StringBuilder builder = new StringBuilder();
		//Write to file
		for(Map.Entry<String, Integer> entry : entries) {
			builder.append(entry.getKey()+"\t"+entry.getValue()+"\n");
		}
		Files.writeString(filePath, builder.toString());
	}
}
