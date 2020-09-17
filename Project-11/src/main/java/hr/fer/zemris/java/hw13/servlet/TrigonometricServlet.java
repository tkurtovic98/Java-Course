package hr.fer.zemris.java.hw13.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * {@link HttpServlet} that is used to 
 * print the values of 
 * sin and cos functions on given 
 * interval
 * @author Tomislav Kurtović
 *
 */
@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Retrieves the passed parameters and calculates the sin and cos of the 
	 * numbers and stores the values in collections that are later used 
	 * for various reasons
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("a");
		String b = req.getParameter("b");

		Integer varA = 0;
		Integer varB = 360;
		
		try {
			if(a != null) {
				varA  = Integer.valueOf(a);
			}
		}catch(Exception ignorable) {}
		
		try {
			if(b != null) {
				varB  = Integer.valueOf(b);
			}
		}catch(Exception ignorable) {}
		
		if(varA > varB) {
			Integer tmp = varB;
			varB = varA;
			varA = tmp;
		}
		
		if(varB > varA + 720) {
			varB = varA + 720;
		}
		
		List<Double> sin = new ArrayList<>();
		List<Double> cos = new ArrayList<>();
		
		for(int i = varA; i < varB; i++) {
			sin.add((Math.sin(i*Math.PI/180)));
			cos.add((Math.cos(i*Math.PI/180)));
		}
		
		req.setAttribute("sin", sin);
		req.setAttribute("cos", cos);
		
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

}
