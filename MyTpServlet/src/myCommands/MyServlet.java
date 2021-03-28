package myCommands;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out;
		
		response.setContentType("text/html");
		out = response.getWriter();
		out.println("<html><head><title>");
		out.println("Servlet Project");
		out.println("</title></head><body>");
		out.println("<h1>" + "Hi sir," + "</h1>");
		out.println("<p>I'm : BACHAR Said</p><br />");
		out.println("<a href=/servlet/sinscrire>Click  to open the project</a>");
		out.println("</body></html>");
		out.close();
	}


}
