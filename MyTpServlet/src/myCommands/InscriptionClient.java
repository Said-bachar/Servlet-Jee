package myCommands;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class InscriptionClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InscriptionClient() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nomRecu = request.getParameter("nom");
		String motPasseRecu = request.getParameter("motdepasse");
		String nomCookie = null;
		String motPasseCookie = null;
		try {
			Cookie[] cookies = request.getCookies();
			nomCookie = Arrays.stream(cookies).filter(t -> "nom".equals(t.getName())).findFirst().get().getValue();
			motPasseCookie = Arrays.stream(cookies).filter(t -> "motdepasse".equals(t.getName())).findFirst().get()
					.getValue();
		} catch (Exception e) {
		}
		// initialisation cookies et paramètres
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if (nomCookie == null && nomRecu == null) {
			// Cas 1 : cas où il n'y a ni de cookies ni de parametres
			showForm(out, nomRecu, motPasseRecu, nomCookie, motPasseCookie);
		} else if (nomCookie == null && nomRecu != null) {
			// Cas 2 : cas où il n'y a pas de cookies mais les paramètres nom et mot de  passes sont présents :
			Cookie nameCookie = new Cookie("nom", nomRecu);
			response.addCookie(nameCookie);
			Cookie passwordCookie = new Cookie("motdepasse", motPasseRecu);
			response.addCookie(passwordCookie);
			showForm(out, nomRecu, motPasseRecu, nomCookie, motPasseCookie);
		} else if (identique(nomRecu, nomCookie) && identique(motPasseRecu, motPasseCookie)) {
			// Cas 4 : cas où le nom et le mot passe sont correctes, appel à la servlet  achat
			response.sendRedirect("/servlet/achat");
		} else {
			// Cas 3 : les cookies sont présents demande de s'identifier
			showForm(out, nomRecu, motPasseRecu, nomCookie, motPasseCookie);
		}
	}

	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void showForm(PrintWriter out, String nomRecu, String motPasseRecu, String nomCookie,
			String motPasseCookie) {
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("<title> inscription d'un client </title>");
		out.println("</head>");
		out.println("<body bgcolor='white' >");
		out.println(nomRecu + " | " + motPasseRecu + " | " + nomCookie + " | " + motPasseCookie);
		out.println("<h3>" + "Bonjour, vous devez vous inscrire " + "</h3>");
		out.println("<h3 style= \"color: red;\">" + "Attention mettre nom et le mot de passe avec plus de 3 caracteres" + "</h3>");
		out.println("<form action='sinscrire' method='GET'>");
		out.println("nom :");
		out.println("<input type='text' size='20' name='nom'>");
		out.println("<br /> <br />");
		out.println("mdp :");
		out.println("<input type='password' size='20' name='motdepasse'><br>");
		out.println("<br /> <br />");
		out.println("<input style= \"color: orange \" type='submit' value='inscription'>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");

	}

	boolean identique(String recu, String cookie) {
		return ((recu != null) && (recu.length() > 3) && (cookie != null) && (recu.equals(cookie)));
	}

}
