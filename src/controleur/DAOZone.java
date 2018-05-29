package controleur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.util.PSQLException;

import modele.Zone;

/**
 * Classe permettant l'interaction avec la table "zone" de la base de données.
 * @author Francois, Céline
 */
public class DAOZone {

	/**
	 * Permet d'insérer une ligne dans la table "zone" à partir d'un objet Zone. 
	 * L'objet passé en paramètre se voit affecté par la suite l'identifiant de la ligne précédemment ajoutée.
	 * @param z Zone à ajouter dans la table correspondante.
	 * @throws PSQLException Exception levée lors d'une erreur spécifique à Postgresql.
	 * @throws SQLException Exception levée lors d'une erreur SQL générale. 
	 * @throws IDException Exception levée lorsqu'aucun ID n'est renvoyé par la requête.
	 * @see modele.Zone
	 */
	public void insert(Zone z) throws PSQLException, SQLException, IDException {
		
		String query = "INSERT INTO zone(identifiant, id_site) VALUES (DEFAULT, "+z.getId_site()+") RETURNING identifiant";
		
		Connection conn = MaConnexion.getInstance();
		Statement state = conn.createStatement();
		ResultSet rs = state.executeQuery(query);
	    
	    if (rs.next()) 
	    	z.setIdentifiant(rs.getInt(1));
	    else 
	    	throw new IDException("zone");
	}

}
