package modele;

/**
 * Classe permettant de représenter la table "site_antenne_relais" de la base de données.
 * @author Francois, Céline
 *
 */
public class Site {
	
	/**
	 * Identifiant du site.
	 */
	private int identifiant;
	/**
	 * identifiant de l'opérateur propriétaire du site.
	 */
	private int id_operateur;
	
	/**
	 * Crée un objet Site avec un identifiant encore inconnu.
	 * @param idO Valeur affectée à l'attribut {@link Site#id_operateur}.
	 */
	public Site(int idO) {
		this.identifiant = -1;
		this.id_operateur = idO;
	}
	
	/**
	 * @return Retourne l'attribut {@link Site#identifiant}.
	 */
	public int getIdentifiant() {
		return identifiant;
	}
	
	/**
	 * Affecte l'identifiant en paramètre à l'attribut {@link Site#identifiant}.
	 * @param id Identifiant de l'opérateur
	 */
	public void set_Identifiant(int id) {
		this.identifiant = id;
	}

	/**
	 * @return Retourne l'attribut {@link Site#id_operateur}.
	 */
	public int getId_operateur() {
		return id_operateur;
	}
}
