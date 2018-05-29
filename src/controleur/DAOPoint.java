package controleur;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import modele.Point;

/**
 * Classe permettant l'interaction avec la table "point" de la base de données.
 * @author Francois, Céline
 */
public class DAOPoint {

	/**
	 * Permet d'insérer une ligne dans la table "point" à partir d'un objet Point.
	 * @param p Point à ajouter dans la table correspondante.
	 * @throws SQLException Exception levée lors d'une erreur SQL générale.
	 * @see modele.Point
	 */
	public void insert(Point p) throws SQLException {
		double[] coo = p.getCoo();
		String query = "INSERT INTO point(id_zone, latitude, longitude, ordre) VALUES ("+p.getId_zone()+","+coo[1]+","+coo[0]+","+p.getOrdre()+")";
		
		Connection conn = MaConnexion.getInstance();
		Statement state = conn.createStatement();
	    state.executeUpdate(query);
	}
}
