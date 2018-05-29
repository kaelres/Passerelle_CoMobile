package controleur;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import modele.Emet;

/**
 * Classe permettant l'interaction avec la table "emet" de la base de données.
 * @author Francois, Céline
 */
public class DAOEmet {

	/**
	 * Permet d'insérer une ligne dans la table "emet" à partir d'un objet Emet.
	 * @param e Objet emet à ajouter dans la table correspondante.
	 * @throws SQLException Exception levée lors d'une erreur SQL générale.
	 * @see modele.Emet
	 */
	public void insert(Emet e) throws SQLException {
		
		Connection conn = MaConnexion.getInstance();
		Statement state = conn.createStatement();
		
   		state.executeUpdate("INSERT INTO emet VALUES ('"+e.getNom_tech()+"',"+e.getId_site()+")");
	       	
		
	}
}
