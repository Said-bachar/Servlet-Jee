package myCommands;

import javax.servlet.http.Cookie;

public class Identification {
	
	public Identification() {}
    
	static String chercheNom(Cookie[] cookies) {
		// cherche dans les cookies la valeur de celui qui se nomme "nom"
		// retourne la valeur de ce nom au lieu de inconnu
		if (cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("nom")) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}
    
	// La meme chose pour le Mdp
	static String chercheMDP(Cookie[] cookies) {
		// cherche dans les cookies la valeur de celui qui se nomme "motdepasse" et retourne sa valeur
		
		if (cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("motdepasse")) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

    
}
