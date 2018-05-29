package controleur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.util.PSQLException;

import modele.Site;

/**
 * Classe permettant l'interaction avec la table "site_antenne_relais" de la base de données.
 * @author Francois, Céline
 */
public class DAOSite {

	/**
	 * Permet d'insérer une ligne dans la table "site_antenne_relais" à partir d'un objet Site. 
	 * L'objet passé en paramètre se voit affecté par la suite l'identifiant de la ligne précédemment ajoutée.
	 * @param s Site à ajouter dans la table correspondante.
	 * @throws PSQLException Exception levée lors d'une erreur spécifique à Postgresql.
	 * @throws SQLException Exception levée lors d'une erreur SQL générale. 
	 * @throws IDException Exception levée lorsqu'aucun ID n'est renvoyé par la requête.
	 * @see modele.Site
	 */
	public void insert(Site s) throws PSQLException, SQLException, IDException {
		String insSite = "INSERT INTO site_antenne_relais(identifiant, id_operateur) VALUES (DEFAULT, "+s.getId_operateur()+") RETURNING identifiant";
		
		Connection conn = MaConnexion.getInstance();
		Statement state = conn.createStatement();
	    ResultSet rs = state.executeQuery(insSite);
	    
	    if (rs.next()) 
	    	s.set_Identifiant(rs.getInt(1));
	    else 
	    	throw new IDException("site");
	}
}
