package myCommands;

import java.io.PrintWriter;

public class Stock {

	public Stock() {
	}

	static String[][] leStock = { { "Disque CD - AMOR TICINES", "15", "disque897TR566" },
			{ "Disque CD - Los Mayas", "19", "disque78UUNYT67" }, { "Disque CD - Dick Anglas", "25", "disque87YHG564" },
			{ "Disque CD - Frederic Angonas", "35", "disque98HUYU56" } };

	public static void vente(PrintWriter out) {
		out.println("<table border=1>");
		for (int i = 0; i < leStock.length; i++) {
			out.println("<tr> <td>" + leStock[i][0] + " " + leStock[i][1] + " Euros </td>");
			out.println(" <td><a href=\"commande?element=disque&code=");
			out.println(leStock[i][2] + "&ordre=ajouter&prix=" + leStock[i][1] + "\">");
			out.println("<img src=\"/fcexemple/images/panier.gif\" border=0></a><br> </td> </tr>");
		}
		out.println("</table> </form>");
	}

	public static String[] chercheCode(String code) {
		if (code != null) {
			for (int i = 0; i < leStock.length; i++) {
				String[] disque = leStock[i];
				if (code.equals(disque[2])) {
					return disque;
				}
			}
		}
		return null;
	}

	public static String[] chercheNom(String nom) {
		if (nom != null) {
			for (int i = 0; i < leStock.length; i++) {
				String[] disque = leStock[i];
				if (nom.equals(disque[0])) {
					return disque;
				}
			}
		}
		return null;
	}
}
