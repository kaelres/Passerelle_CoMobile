package controleur;

/**
 * Exception représentant une erreur lors du renvoi par postgresql de l'identifiant d'une ligne nouvellement créée.
 * @author Francois, Céline
 *
 */
@SuppressWarnings("serial")
public class IDException extends Exception {
	
	/**
	 * Contient le type d'objet dont l'identifiant n'a pas pu être récupéré.
	 */
	private String type;
	
	/**
	 * Crée un objet IDException avec un message particulier.
	 * @param type Valeur affectée à l'attribut {@link #type}.
	 */
	public IDException(String type) {
		this.type = type;
	}
	
	/**
	 * Redéfinition de la méthode toString afin de pouvoir afficher convenablement l'exception.
	 */
	public String toString() {
		return "L'identifiant de l'objet de type \""+type+"\" n'a pas pu être récupéré.";
	}
}
