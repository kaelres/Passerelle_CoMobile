package controleur;

/**
 * Exception representant une erreur au niveau du fichier. Celle-ci peut être dû à une erreur de format, un fichier vide,...
 * @author Francois, Céline
 *
 */
@SuppressWarnings("serial")
public class UnsuitableFileException extends Exception {
	
	/**
	 * Contient l'adresse du fichier ayant déclenché l'erreur.
	 */
	private String message;
	
	/**
	 * Crée un objet UnsuitableFileException.
	 * @param str Valeur affectée à l'attribut {@link #message}.
	 */
	public UnsuitableFileException(String str) {
		message = str;
	}
	
	/**
	 * Redéfinition de la méthode toString afin de pouvoir afficher convenablement l'exception.
	 */
	public String toString() {
		return "Le fichier "+this.message+" est vide ou ne correspond pas au format attendu.";
	}
}