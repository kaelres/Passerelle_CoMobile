package controleur;

/**
 * Fonction ayant pour but de permettre l'accès via ses fonctions statiques
 * aux différents objets du design pattern "Data As Object" utilisés dans l'application.
 * @author Francois, Céline
 *
 */
public class UsineDAO {

	/**
	 * @return Nouvel objet DAO portant sur la table "site_antenne_relais"
	 */
	public static DAOSite getDAOSite() {
		return new DAOSite();
	}
	/**
	 * @return Nouvel objet DAO portant sur la table "point".
	 */
	public static DAOPoint getDAOPoint() {
		return new DAOPoint();
	}
	/**
	 * @return Nouvel objet DAO portant sur la table "emet".
	 */
	public static DAOEmet getDAOEmet() {
		return new DAOEmet();
	}
	/**
	 * @return Nouvel objet DAO portant sur la table "zone".
	 */
	public static DAOZone getDAOZone() {
		return new DAOZone();
	}
}
