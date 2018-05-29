package modele;

/**
 * Classe permettant de représenter la table "point" de la base de données. 
 * @author Francois, Céline
 *
 */
public class Point {

	/**
	 * Coordonnées du point sous la forme [longitude, latitude].
	 */
	private double[] coo;
	/**
	 * Place du point dans la séquence créant la zone associée.
	 */
	private int ordre;
	/**
	 * Identifiant de la zone associée au point.
	 */
	private int id_zone;
	
	/**
	 * Constructeur prenant en entrée des coordonnées Lambert93. Celles-ci sont transformées
	 * en coordonnées WGPS-84 (latitude/longitude).
	 * @param coordinateTab tableau de coordonnées Lambert[Est, Nord] affectée à l'attribut {@link #coo}.
	 * @param ordre Valeur affectée à l'attribut {@link Point#ordre}.
	 * @param id Valeur affectée à l'attribut {@link Point#id_zone}.
	 */
	public Point( String[] coordinateTab, int ordre, int id) {
		coo = new double[2];
		this.coo[0] = Double.parseDouble(coordinateTab[0]);
		this.coo[1] = Double.parseDouble(coordinateTab[1]);
		lambert93toWGPS();
		this.ordre = ordre;
		this.id_zone = id;
	}
	
	/**
	 * Adaptation du code suivant : https://gist.github.com/blemoine/e6045ed93b3d90a52891
	 * <p>
	 * Le code suivant, en lui fournissant un tableau au des coordonées au format Lambert 93 [coordonée Est, coordonée Nord],
	 * calcule la lattitude et longitude correspondante et transforme le tableau en format GPS [longitude, latitude]
	 * </p>
	 * @param coo Tableau de coordonées au format Lambert 93. Le premier élément doit être la coordonée Est. Celui-ci sera modifié pour contenir les coordonées GPS avec la longitude en première position.
	 */
	private void lambert93toWGPS() {
		
		//constantes
		double GRS80E = 0.081819191042816;
		double LONG_0 = 3;
		double XS = 700000;
		double YS = 12655612.0499;
		double n = 0.7256077650532670;
		double C = 11754255.4261;
		
		
		double lambertE = coo[0];
		double lambertN = coo[1];
		
		
		double delX = lambertE - XS;
		double delY = lambertN - YS;
		double gamma = Math.atan(-delX / delY);
		double R = Math.sqrt(delX * delX + delY * delY);
		double latiso = Math.log(C / R) / n;
		double sinPhiit0 = Math.tanh(latiso + GRS80E * atanh(GRS80E * Math.sin(1)));
		double sinPhiit1 = Math.tanh(latiso + GRS80E * atanh(GRS80E * sinPhiit0));
		double sinPhiit2 = Math.tanh(latiso + GRS80E * atanh(GRS80E * sinPhiit1));
		double sinPhiit3 = Math.tanh(latiso + GRS80E * atanh(GRS80E * sinPhiit2));
		double sinPhiit4 = Math.tanh(latiso + GRS80E * atanh(GRS80E * sinPhiit3));
		double sinPhiit5 = Math.tanh(latiso + GRS80E * atanh(GRS80E * sinPhiit4));
		double sinPhiit6 = Math.tanh(latiso + GRS80E * atanh(GRS80E * sinPhiit5));

		double longRad = Math.asin(sinPhiit6);
		double latRad = gamma / n + LONG_0 / 180 * Math.PI;

		double longitude = latRad / Math.PI * 180;
		double latitude = longRad / Math.PI * 180;
		
		coo[0] = longitude;
		coo[1] = latitude;
	}
	
	/**
	 * Fonction argument tangente hyperbolique.
	 * @param a Paramètre de la fonction.
	 * @return Résultat de l'argument tangente hyperbolique.
	 */
	private static double atanh(double a) {
		return (0.5)*Math.log((1+a)/(1-a));
	}

	/**
	 * @return Retourne l'attribut {@link Point#ordre}.
	 */
	public int getOrdre() {
		return ordre;
	}


	/**
	 * @return Retourne l'attribut {@link Point#id_zone}.
	 */
	public int getId_zone() {
		return id_zone;
	}
	
	/**
	 * @return Retourne l'attribut {@link Point#coo}.
	 */
	public double[] getCoo() { return coo;}

}
