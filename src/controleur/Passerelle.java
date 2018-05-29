package controleur;

import java.io.*;
import java.util.*;

import modele.Emet;
import modele.Point;
import modele.Site;
import modele.Zone;

import java.sql.SQLException;

/**
 * Cette classe met à disposition une méthode statique dont le but est d'insérer dans la base de données des informations relatives 
 * à la couverture téléphonique dans les Bouches-du-Rhône à partir d'un fichier ".shp". Un fichier "output.csv" sera créé dans le
 * même dossier que le fichier ".shp". Une fois l'opération terminé (par un succès ou un échec) celui-ci peut être supprimé sans conséquences. 
 * @author Francois, Céline
 */
public class Passerelle {

	
	/**
	 * Fonction permettant à l'application d'extraire les données du fichier shp à l'aide d'un parser, de les récupérer/formatter 
	 * puis de les insérer en base de données à l'aide des objets DAO.
	 * @param path Adresse du fichier .shp à utiliser
	 * @throws SQLException Exception levée par la base de donnée
	 * @throws IDException	Exception levée si aucun id ne correspond à l'antenne
	 * @throws UnsuitableFileException Exception levée si le fichier est vide ou pas du format attendu
	 * @throws FileNotFoundException Exception levée lorsque le fichier dont l'adresse est donnée par la variable path est introuvable
	 */
	public static void insert(String pathShp) throws SQLException, IDException, UnsuitableFileException, FileNotFoundException {
		
		String pathCsv = pathShp.replaceAll("[a-zA-Z0-9-_.]*$", "");
		pathCsv = pathCsv + "output.csv";
		
		String csv = ShapefileParser.parse(pathShp);			
		
		PrintWriter pw = new PrintWriter(pathCsv);
		pw.write(csv);
		pw.close();

		File file = new File(pathCsv);
	    Scanner scanner;
	    
	    Site site;
	    Zone zone;
	    String line;										//ligne actuelle
	    String op, dep, tech, coordinate;					//caractéristiques
	    String bloc1Raw, bloc1Cut, sousBloc1, bloc1, bloc2;			
	    String[] columns, bloc1DoubleParenthese, bloc1SimpleParenthese, meta, coordinateTab;
	    boolean fini = false;								//Permet de s'arrêter une fois les antennes du 13 insérées
	    int numDep;											//Numéro du département sous forme de chiffre et non de chaîne de caractère
	    
	    DAOSite daoS = UsineDAO.getDAOSite();
	    DAOEmet daoE = UsineDAO.getDAOEmet();
	    DAOPoint daoP = UsineDAO.getDAOPoint();
	    DAOZone daoZ = UsineDAO.getDAOZone();	   
	    
		
		scanner = new Scanner(file);
		scanner.useDelimiter("\\n");
			
		if (scanner.hasNext()) scanner.next();
		else {
			scanner.close();
			throw new UnsuitableFileException(pathCsv);
		}
			
			
		while(scanner.hasNext() && !fini){
				
			//Séparation des éléments
			line = scanner.next();
			if (!line.equals("") ) {
				columns = line.split("\"");
				
				
				//traitement second bloc
					
				bloc2 = columns[2];
				meta = bloc2.split(",");		

				op = meta[1];
				op = op.replace("-", "");
				tech = meta[3];
				dep = meta[6];
				
				numDep = Integer.parseInt(dep);
				
				System.out.println(op+" "+tech+" "+dep+"\n");
					
				if ( numDep == 13) {
					
					site = new Site(Integer.parseInt(op));
					
					daoS.insert(site);
					daoE.insert(new Emet(site.getIdentifiant(), tech));
						
					//traitement 1er bloc
						
					//transformation des coordonnées en données utilisable
					bloc1Raw = columns[1];
					bloc1Cut = bloc1Raw.substring(16, bloc1Raw.length()-3); //suppression du nom et des parenthèses
						
					bloc1DoubleParenthese = bloc1Cut.split("\\)\\), \\(\\(");
					ArrayList<String> ArrayDoublePar = new ArrayList<>(Arrays.asList(bloc1DoubleParenthese));
					ListIterator<String> iteDoublePar = ArrayDoublePar.listIterator();
						
					while (iteDoublePar.hasNext()) {
						
						sousBloc1 = iteDoublePar.next();
						bloc1SimpleParenthese = sousBloc1.split("\\), \\(");
						ArrayList<String> ArraySimplePar = new ArrayList<>(Arrays.asList(bloc1SimpleParenthese));
						ListIterator<String> iteSimplePar = ArraySimplePar.listIterator();
						
						while (iteSimplePar.hasNext()) {
							
							bloc1 = iteSimplePar.next();
							
							bloc1 = bloc1.replaceAll(",\\s", ",");				//suppression des espaces après les virgules
							
							coordinateTab = bloc1.split(",");
							ArrayList<String> array1 = new ArrayList<>(Arrays.asList(coordinateTab));
							ListIterator<String> li1 = array1.listIterator();
							
							zone = new Zone(site.getIdentifiant());
							daoZ.insert(zone);
								
							int ordre = 1;
							while (li1.hasNext()) {
								coordinate = li1.next();
								coordinateTab = coordinate.split("\\s");
									
								daoP.insert(new Point(coordinateTab, ordre, zone.getIdentifiant()));
									
								ordre++;
							}
						}
						
						
					}								
						
					
					
					
					
				} else if (numDep > 13){
					fini = true; // Les antennes sont triées par departement croissant. Inutile de regarder au dela de 13
				}
					
			}	    
			
		}
		scanner.close();
	}
}


