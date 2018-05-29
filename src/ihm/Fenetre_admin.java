package ihm;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.postgresql.util.PSQLException;

import controleur.IDException;
import controleur.MaConnexion;
import controleur.Passerelle;
import controleur.UnsuitableFileException;

/**
 * Cette classe représente la fenêtre de l'administrateur.
 * Elle contient un panneau_admin.
 * @see panneau_admin
 * @author François, Céline
 *
 */
@SuppressWarnings("serial")
public class Fenetre_admin extends JFrame
{
	/**
	 * Panneau d'administration lié à la fenetre d'administration
	 */
	private Panneau_admin panneau;
	/**
	 * Constructeur de Fenetre_admin qui instancie une fenêtre administrateur centrée et de dimension un quart
	 * de l'écran.
	 * On ajoute à cette fenêtre une barre de défilement sur le panneau associé.
	 * @throws PSQLException Exception PSQL potentielle à l'utilisation de méthode de la JDBC
	 * @throws SQLException Exception SQL potentielle à l'utilisation de méthode de la JDBC
	 */
	Fenetre_admin () {
		super();
		this.setTitle("Accès à la Base de données");
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension dim=tk.getScreenSize();
		int largeur=dim.width;
		int hauteur=dim.height;
		this.setSize( largeur/2, hauteur/2);
		this.setLocationRelativeTo(null);
		
		panneau = new Panneau_admin(this);
		this.add(panneau);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
	}
	
	/**
	 * Panneau principal de la fenêtre d'administration. Celui-ci contient les différents boutons permettant l'interaction avec l'application.
	 * @author Francois, Céline
	 *
	 */
	private class Panneau_admin extends JPanel {
		
		/**
		 * Lien vers la fenêtre contenant le panneau. Utilisée pour la fermeture de fenêtre.
		 */
		private Fenetre_admin f_admin;
		/**
		 * Texte contenant le path du fichier .shp à utiliser.
		 */
		private JLabel pathLabel;
		
		/**
		 * Constructeur du panneau.
		 * @param f Fenêtre parente qui sera fermé lorsque le client décide de fermer l'application.
		 */
		Panneau_admin(Fenetre_admin f) {
			
			f_admin = f;
			
			JLabel text = new JLabel("Choisissez le fichier .shp disponible en opendata du gouvernement à ouvrir :");
			JPanel p1 = new JPanel();
			p1.add(text);
			
			pathLabel = new JLabel("/");
			JPanel p2 = new JPanel();
			p2.add(pathLabel);
			
			JButton parcours = new JButton("Parcourir");
			parcours.addActionListener(new BoutonListenerParcourir());
			JPanel p3 = new JPanel();
			p3.add(parcours);
			
			JLabel info = new JLabel("(fichiers disponibles sur : https://www.data.gouv.fr/fr/datasets/monreseaumobile/)");
			JPanel p4 = new JPanel();
			p4.add(info);
			
			JButton bouton_quitter=new JButton("Quitter");
			bouton_quitter.addActionListener(new BoutonListenerQuitter());
			JButton bouton_valider=new JButton("Valider");
			bouton_valider.addActionListener(new BoutonListenerValider());
			JPanel p_button = new JPanel();
			p_button.add(bouton_quitter);
			p_button.add(bouton_valider);
			
			JPanel p_formu = new JPanel();
			p_formu.setLayout(new BoxLayout(p_formu, BoxLayout.PAGE_AXIS));
			p_formu.add(Box.createRigidArea(new Dimension(0,10)));
			p_formu.add(p1);
			p_formu.add(Box.createRigidArea(new Dimension(0,10)));
			p_formu.add(p2);
			p_formu.add(Box.createRigidArea(new Dimension(0,10)));
			p_formu.add(p3);
			p_formu.add(Box.createRigidArea(new Dimension(0,15)));
			p_formu.add(p4);
			p_formu.add(Box.createRigidArea(new Dimension(0,40)));
			p_formu.add(p_button);
						
			add(p_formu);
		}
		
		/**
		 * Permet de sélectionner un fichier dans l'arborescence de l'ordinateur et d'obtenir 
		 * son chemin sous forme de chaîne de charactères.
		 * @return Chemin du fichier sélectionné dans l'explorateur de fichier.
		 */
		public String pick() {
	    	String str = "";
	        JFileChooser chooser = new JFileChooser();
	        FileNameExtensionFilter filter = new FileNameExtensionFilter(
	                "shp", "shp");
	        chooser.setFileFilter(filter);
	        int returnVal = chooser.showOpenDialog(null);
	        if(returnVal == JFileChooser.APPROVE_OPTION) {
	        	str = chooser.getSelectedFile().getAbsolutePath();
	        }
			return str;
	    }
		
		/**
		 * La classe qui permet de définir l'action à faire quand on clique sur le bouton Quitter.
		 * Pour cela, elle implémente ActionListener qui gère les évènements liés au clique sur un composant.
		 * @author François
		 */
		private class BoutonListenerQuitter implements ActionListener {
			/**
			 * Cette méthode découle de l'interface ActionListener et gère l'évenement associé au clique sur le bouton Quitter.
			 * Elle permet de quitter l'application.
			 * @param arg0 Evénement lié au clic sur le bouton implémentant l'ActionListener
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MaConnexion.clean();
				System.exit(0);
				
			}
			
		}
		
		/**
		 * La classe qui permet de définir l'action à faire quand on clique sur le bouton Valider.
		 * Pour cela, elle implémente ActionListener qui gère les évènements liés au clique sur un composant.
		 * @author François
		 */
		private class BoutonListenerValider implements ActionListener {
			/**
			 * Cette méthode découle de l'interface ActionListener et gère l'évenement associé au clique sur le bouton Valider.
			 * Elle permet de valider le choix de fichier à faire utiliser par la passerelle.
			 * @param arg0 Evénement lié au clic sur le bouton implémentant l'ActionListener
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					Passerelle.insert(pathLabel.getText());
					
					JOptionPane.showMessageDialog(	f_admin, 
							"L'opération s'est complétée correctement.", 
							"Etat d'opération", 
							JOptionPane.INFORMATION_MESSAGE);
					pathLabel.setText("/");
					
				} catch (FileNotFoundException e) {
					erreur("Le chemin fourni n'aboutit en un fichier existant.");
					e.printStackTrace();
				} catch (SQLException e) {
					erreur("Erreur sql générée par la base de données !");
					e.printStackTrace();
				} catch (IDException e) {
					erreur(e.toString());
					e.printStackTrace();
				} catch (UnsuitableFileException e) {
					erreur(e.toString());
					e.printStackTrace();
				}
				
			}
			
			private void erreur(String str) {
				JOptionPane.showMessageDialog(	f_admin, 
						"L'opération a été interrompu pour la cause : \n"+str, 
						"Etat de l'opération", 
						JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
		/**
		 * La classe qui permet de définir l'action à faire quand on clique sur le bouton Parcourir.
		 * Pour cela, elle implémente ActionListener qui gère les évènements liés au clique sur un composant.
		 * @author François
		 */
		private class BoutonListenerParcourir implements ActionListener {
			/**
			 * Cette méthode découle de l'interface ActionListener et gère l'évenement associé au clique sur le bouton Parcourir.
			 * Elle permet l'appel au gestionnaire de fichier pour choisir un fichier.
			 * @param arg0 Evénement lié au clic sur le bouton implémentant l'ActionListener
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String pathStr = pick();
				pathLabel.setText(pathStr);
				f_admin.pack();
			}
			
		}
	}
}


















