package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.util.PSQLException;

/**
 * Classe mettant en place le design pattern "Singleton".
 * Elle permet l'accès à un objet de type "Connection" de la JDBC et empêche la
 * création de multiples instances.
 * @author Francois, Céline
 */
public class MaConnexion {
	
	/**
	 * <p>Connection à la base de donnée.</p>
	 * <p> Elle est de type statique afin d'être manipulable par getInstance().</p>
	 */
	private static Connection connec;
	
	/**
	 * <p>Url de la base de donnée nécessaire à l'établissement d'une connexion. 
	 * Celle-ci n'est pas modifiable afin d'éviter le dysfonctionnement du système.</p>
	 */
	private final String url = "jdbc:postgresql://postgresql-francois-even.alwaysdata.net:5432/francois-even_projetinsi";
	
	/**
	 * <p>Login utilisé par l'usager pour tenter de se connecter.</p>
	 */
	private static String usr;
	
	/**
	 * <p>Mot de passe utilisé par l'utilisateur pour tenter de se connecter.</p>
	 */
	private static String passwd;

	
	/**
	 * Constructeur privé créant une connexion à l'aide d'un url, d'un mot de passe et d'un login.
	 * @throws SQLException Exception SQL potentielle à l'utilisation de méthode de la JDBC
	 * @throws PSQLException Exception PSQL potentielle à l'utilisation de méthode de la JDBC
	 */
	private MaConnexion() throws SQLException, PSQLException {
		connec = DriverManager.getConnection(url, usr, passwd);
	}
	
	/**
	 * Cette méthode vérifie si il existe déjà une instance de connexion avec la base de données.
	 * Si ce n'est pas le cas une nouvelle est créée avec les identifiants clients actuellements enregistrés en attribut.
	 * Suite à cela, qu'une nouvelle instance est été créée ou non, l'instance de connexion est renvoyée.
	 * @return renvoie un lien ver l'objet "Connection" faisant liaison avec la base de données. 
	 * @throws PSQLException Exception levée lors d'une erreur spécifique à Postgresql.
	 * @throws SQLException Exception levée lors d'une erreur SQL générale.
	 */
	public static Connection getInstance() throws SQLException, PSQLException{
	    if(connec == null){
	      new MaConnexion();
	    } 
	    return connec;
	}
	
	/**
	 * Cette méthode statique permet de mettre à jour les identifiants et mot de passe utilisés pour créer une nouvelle connexion
	 * à la base de données. Aucune vérification n'est faite sur leur validitée.
	 * @param u Valeur affectée à l'attribut {@link MaConnexion#usr}.
	 * @param p Valeur affectée à l'attribut {@link MaConnexion#passwd}.
	 */
	public static void setID(String u, String p) {
		usr = u;
		passwd = p;
	}
	
	/**
	 * @return Renvoie l'attribut {@link MaConnexion#usr}. 
	 */
	public static String getUsr() { return usr;}
	
	/**
	 * Cette méthode ferme la connexion avec la base de données et réinitialise la classe à son état initial.
	 */
	public static void clean() {
		try {
			if (connec != null ) {
				connec.close();
				connec = null;
			}
			usr = "";
			passwd = "";
		} catch (PSQLException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
