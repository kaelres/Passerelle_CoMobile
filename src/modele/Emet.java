package modele;

/**
 * Classe permettant de représenter la table "emet" de la base de données.
 * @author Francois, Céline
 *
 */
public class Emet {
	
	/**
	 * Identifiant du site associé à l'émission.
	 */
	private int id_site;
	/**
	 * Nom de la technologie associée à l'émission.
	 */
	private String nom_tech;
	
	/**
	 * Crée un objet emet à partir d'un nom de technologie et d'un identifiant de site.
	 * @param id Valeur affectée à l'attribut {@link #id_site}.
	 * @param tech Valeur affectée à l'attribut {@link #nom_tech}.
	 */
	public Emet(int id, String tech) {
		this.id_site = id;
		this.nom_tech = tech;
	}
	
	/**
	 * 
	 * @return Retourne l'attribut {@link Emet#nom_tech}.
	 */
	public String getNom_tech() {
		return nom_tech;
	}
	
	/**
	 * 
	 * @return Retourne l'attribut {@link Emet#id_site}.
	 */
	public int getId_site() {
		return id_site;
	}
	

}
