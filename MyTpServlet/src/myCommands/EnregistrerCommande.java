package myCommands;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class EnregistrerCommande extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connexion = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;

	
	public EnregistrerCommande() {
		super();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nom = null;
		Cookie[] cookies = request.getCookies();
		nom = Identification.chercheNom(cookies);
		OuvreBase();
		AjouteNomBase(nom);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("<title> votre commande </title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");
		out.println("<h3>" + "Bonjour " + nom + " voici ta nouvelle commande" + "</h3>");
		HttpSession session = request.getSession();
		Enumeration<String> names = session.getAttributeNames();
		while (names.hasMoreElements()) {
			String attribute = (String) names.nextElement();
			String nomDisque = (String) session.getAttribute(attribute);
			out.println(nomDisque + "<br>");
		}
		AjouteCommandeBase(nom, session);
		out.println("<h3>" + "et voici " + nom + " ta commande complete" + "</h3>");
		MontreCommandeBase(nom, out);
		//out.println("-------");
		out.println("<a href=vider> Vous pouvez commandez un autre disque </a><br> ");
		out.println("</body>");
		out.println("</html>");
	}

	protected void OuvreBase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//connexion = DriverManager.getConnection("jdbc:mysql://localhost/magasin","root","");
			connexion = DriverManager.getConnection("jdbc:mysql://mysql-25509-0.cloudclusters.net:25509/mymagasin","said","said0606");
			
			connexion.setAutoCommit(true);
			stmt = connexion.createStatement();
		} catch (Exception E) {
			log(" -------- prolbeme " + E.getClass().getName());
			E.printStackTrace();
		}
	}

	protected void FermeBase() {
		try {
			stmt.close();
			connexion.close();
		} catch (Exception E) {
			log(" -------- probleme " + E.getClass().getName());
			E.printStackTrace();
		}
	}

	protected void AjouteNomBase(String nom) {
		try {
			pstmt = connexion.prepareStatement("select numero from personnel where nom=?");
			pstmt.setString(1, nom);
			ResultSet resultSet = pstmt.executeQuery();
			if (!resultSet.next()) {
				String query = "INSERT INTO `personnel` (`nom`) VALUES (?)";
				PreparedStatement preparedStatement = connexion.prepareStatement(query);
				preparedStatement.setString(1, nom);
				preparedStatement.executeUpdate();
			}
		} catch (Exception E) {
			log(" - probeme " + E.getClass().getName());
			E.printStackTrace();
		}
	}

	protected void AjouteCommandeBase(String nom, HttpSession session) {
		// ajoute le contenu du panier dans la base
		try {
			pstmt = connexion.prepareStatement("select numero from personnel where nom=?");
			pstmt.setString(1, nom);
			ResultSet resultSet = pstmt.executeQuery();
			int numUser = 0;
			if (resultSet.next()) {
				numUser = resultSet.getInt("numero");
			}

			Enumeration<String> names = session.getAttributeNames();
			while (names.hasMoreElements()) {
				String disque = (String) names.nextElement();
				String nomDisque = (String) session.getAttribute(disque);
				String query = "INSERT INTO `commande` (`article`, `qui`) VALUES (?, ?)";
				PreparedStatement preparedStatement = connexion.prepareStatement(query);

				preparedStatement.setString(1, nomDisque);
				preparedStatement.setInt(2, numUser);
				preparedStatement.executeUpdate();
			}

		} catch (Exception E) {
			log(" - probeme " + E.getClass().getName());
			E.printStackTrace();
		}
	}

	protected void MontreCommandeBase(String nom, PrintWriter out) {
		// affiche les produits présents dans la base
		try {
			pstmt = connexion.prepareStatement("SELECT com.article FROM commande com inner join personnel per on per.numero = com.qui WHERE per.nom = ?");
			pstmt.setString(1, nom);
			ResultSet resultSet = pstmt.executeQuery();
			out.println("Utilisateur " + nom + " a commandé : ");
			out.println("<ul>");
			while (resultSet.next()) {
				out.println("<li>" + resultSet.getString("article") + "</li>");
			}
			out.println("</ul>");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}



}
