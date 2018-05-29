package modele;

/**
 * Classe permettant de représenter la table "zone" de la base de données.
 * @author Francois, Céline
 *
 */
public class Zone {
	
	/**
	 * Identifiant de la zone.
	 */
	private int identifiant;
	/**
	 * Identifiant du site auquel la zone est associé.
	 */
	private int id_site;
	
	/**
	 * Crée un objet Zone avec un identifiant encore inconnu.
	 * @param id Valeur affectée à l'attribut {@link Zone#id_site}.
	 */
	public Zone(int id) {
		this.id_site = id;
		this.identifiant = -1;
	}

	/**
	 * @return Retourne l'attribut id_site.{@link Zone#id_site}.
	 */
	public int getId_site() {
		return id_site;
	}

	/**
	 * @return Retourne l'attribut {@link Zone#identifiant}.
	 */
	public int getIdentifiant() {
		return identifiant;
	}

	/**
	 * @param identifiant L'identifiant de la zone à affecter à l'attribut {@link Zone#identifiant}.
	 */
	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}


}
