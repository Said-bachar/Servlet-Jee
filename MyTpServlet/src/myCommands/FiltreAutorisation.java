package myCommands;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class FiltreAutorisation
 */
public class FiltreAutorisation implements Filter {
	private FilterConfig filterConfig = null;
	
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * Default constructor.
	 */
	public FiltreAutorisation() {
	}

	

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String nom = null;
		HttpServletRequest hrequest = (HttpServletRequest) request;
		Cookie[] cookies = hrequest.getCookies();
		// test s'il existe un cookie dont l'attribut est "nom"
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("nom")) {
				nom = cookies[i].getValue();
				break;
			}
		}
		if (nom == null) {
			// appel inscrire
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect("inscrire");
		} else {
			chain.doFilter(request, response);
		}
	}

	
	
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		this.filterConfig = null;
	}
	


}
