package hr.fer.zemris.java.tecaj_13.model.other;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * {@link Filter} used to 
 * prevent unauthorized access of 
 * new and edit pages if 
 * user is not logged in 
 * @author Tomislav Kurtović
 *
 */
@WebFilter(urlPatterns = {"/newEntry" , "/editEntry"})
public class MyFilter implements Filter  {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(request.getAttribute("OK") == null) {
			request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
