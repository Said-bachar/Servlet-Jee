package myCommands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/ViderPanier")
public class ViderPanier extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public ViderPanier() {
		super();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// clear panier and redirect to achat
		// Get panier
		HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    response.sendRedirect("achat");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}


