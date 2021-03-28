package myCommands;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CommanderUnDisque
 */
public class CommanderUnDisque extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommanderUnDisque() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nom = null;
		Cookie[] cookies = request.getCookies();
		nom = Identification.chercheNom(cookies);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("<title> votre commande </title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");
		out.println("<h3>" + "Bonjour " + nom + " voici votre commande" + "</h3>");
		// affichage de tous les disques présents dans le panier (éléments de la
		// session)
		HttpSession session = request.getSession();
		Enumeration<String> names = session.getAttributeNames();
		// Nombre de disques dans la session
		int nbDisques = Collections.list(names).size();
		// S'il ya des disques dans la session (panier)
		out.println("<ul>");
		if (nbDisques != 0) {
			for (int i = 1; i <= nbDisques; i++) {
				String nomDisque = (String) session.getAttribute("disque" + i);
				String[] infosDisque = Stock.chercheNom(nomDisque);
				out.println("<li>Disque" + " Nom=" + infosDisque[0] + " Prix=" + infosDisque[1] + "</li>");
			}
		}
		// si parametre ordre == ajouter affichage du disque à ajouter au panier
		try {
			if (request.getParameter("ordre").equals("ajouter")) {
				String code = request.getParameter("code");
				String[] disque = Stock.chercheCode(code);
				out.println("<li>Disque" + " Nom=" + disque[0] + " Prix=" + disque[1] + "</li>");
				// Incrementer le nombre de disques
				nbDisques++;
				session.setAttribute("disque" + nbDisques, disque[0]);
			}
		} catch (Exception e) {
		}
		out.println("</ul>");
		out.println("<a href=achat> Vous pouvez commandez un autre disque </a><br> ");
		out.println("<a href=enregistre> Vous pouvez enregistrer votre commande </a><br> ");
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
