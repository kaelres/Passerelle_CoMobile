package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.postgresql.util.PSQLException;
import controleur.MaConnexion;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;

/**
 * Fenetre_auth est la fenêtre d'authentification.
 * Elle est caractérisée par un panneau d'authentification.
 * @author François, Céline
 * @see Panneau_authentification
 */
@SuppressWarnings("serial")
public class Fenetre_authentification extends JFrame
{
	/**
	 * Le panneau d'authentification associé à la fenêtre d'authentification
	 */
	private Panneau_authentification panneau;
	/**
	 * Le constructeur de la fenêtre d'authentification
	 * <p>
	 * Quand on instancie la fenêtre,elle est centrée sur l'écran, on lui
	 *  donne une taille de 1/4 de l'écran et on lui ajoute une instance
	 *  d'un panneau d'authentification.
	 * </p>
	 *  @see Panneau_authentification
	 */
	public Fenetre_authentification ()
	{
		super();
		this.setTitle("Authentification");
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension dim=tk.getScreenSize();
		int largeur=dim.width;
		int hauteur=dim.height;
		this.setSize( largeur/2, hauteur/2);
		this.setLocationRelativeTo(null);
		panneau=new Panneau_authentification(this); 
		add(panneau);
		JScrollPane scrollPane = new JScrollPane(panneau);
		add(scrollPane);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true); 
	}
	
	
	
	/**
	 * Panneau_authentification correspond au panneau de connexion à la base de données. 
	 * Il est contenu dans la fenêtre d'authentification
	 * @author François, Céline
	 * @see Fenetre_authentification
	 */
	private class Panneau_authentification extends JPanel
	{
		/**
		 * La fenêtre conteneur de panneau_authentification
		 * @see Fenetre_admin
		 */
		private Fenetre_authentification f_auth;
		
		private JLabel label_login;
		/**
		 * Le champ de saisie du login
		 */
		private JTextField champ_login;
		
		private JLabel label_mdp;
		/**
		 * Le champ de saisie crypté du mot de passe
		 */
		private JPasswordField champ_mdp;
		/**
		 * Le bouton de validation de connexion  la base de données
		 */
		private JButton bouton_valider;
		/**
		 * Le bouton pour quitter l'application
		 */
		private JButton bouton_quitter;

		
		/**
		 * Le constructeur de Panneau_authentification
		 * <p>
		 * Il est composé de deux sous panneaux : un pour le formulaire de saisie de compte
		 *  et l'autre pour les boutons.
		 *  Le bouton valider va permettre de naviguer soit dans la fenêtre client
		 *  si c'est un compte client soit dans la fenêtre administrateur si le compte est administrateur.
		 * </p>
		 * @param f La fenêtre qui contient le panneau afin de pouvoir la fermer
		 * @see Fenetre_authentification
		 */
		Panneau_authentification(Fenetre_authentification f)
		{
			f_auth=f;
			
			setLayout(new BorderLayout());
			
			Panel p0=new Panel();
			p0.setLayout(new BoxLayout(p0,BoxLayout.LINE_AXIS));
			label_login=new JLabel("Login :");
			champ_login=new JTextField(10);
			p0.add(label_login);
			p0.add(Box.createRigidArea(new Dimension(10,0)));
			p0.add(champ_login);
			
			Panel p1=new Panel();
			p1.setLayout(new BoxLayout(p1,BoxLayout.LINE_AXIS));
			label_mdp=new JLabel("Mot de Passe :");
			champ_mdp=new JPasswordField(10);
			p1.add(label_mdp);
			p1.add(Box.createRigidArea(new Dimension(10,0)));
			p1.add(champ_mdp);
			
			Panel p2=new Panel();
			p2.setLayout(new BoxLayout(p2,BoxLayout.PAGE_AXIS));
			p2.add(Box.createRigidArea(new Dimension(0,50)));
			p2.add(p0);
			p2.add(Box.createRigidArea(new Dimension(0,10)));
			p2.add(p1);
			
			Panel p3=new Panel();
			p3.add(p2);
			add(p3,BorderLayout.CENTER);
			
			Panel p_boutons=new Panel();
			bouton_valider=new JButton("Valider");
			bouton_valider.addActionListener(new BoutonListenerValider());
			bouton_quitter=new JButton("Quitter");
			bouton_quitter.addActionListener(new BoutonListenerQuitter());
			p_boutons.add(bouton_quitter);
			p_boutons.add(bouton_valider);
			add(p_boutons,BorderLayout.SOUTH);
		}
		/**
		 * La classe qui permet de définir l'action à faire quand on clique sur le bouton Valider.
		 * Pour cela, elle implémente ActionListener qui gère les évènements liés au clique sur un composant.
		 * @author François
		 *
		 */
		private class BoutonListenerValider implements ActionListener
		{

			@SuppressWarnings("unused")
			@Override
			/**
			 * Cette méthode découle de l'interface ActionListener et gère l'évenement associé au clique sur le bouton Valider.
			 * Elle permet de lancer une connexion administrateur ou client selon les privilèges du compte.
			 * Elle vérifie que le compte rentré dans le formulaire de compte existe et dans le cas contraire
			 * affiche un message d'erreur.
			 * En cas de compte existant une fenêtre d'administration est instanciée.		
			 * @param arg0 Evénement lié au clic sur le bouton implémentant l'ActionListener
			 * @see Fenetre_admin
			 */	
			public void actionPerformed(ActionEvent e) {
				String usr = champ_login.getText();
				String passwd = new String(champ_mdp.getPassword());
				MaConnexion.setID(usr, passwd);
				
				try {
					Connection conn = MaConnexion.getInstance();
					new Fenetre_admin();
					f_auth.dispose();
					
				} catch (PSQLException e2) {
					JOptionPane.showMessageDialog(	f_auth, 
													"Mauvais Login et/ou mot de passe", 
													"Erreur", 
													JOptionPane.ERROR_MESSAGE);
					MaConnexion.clean();
					e2.printStackTrace();
				} catch  (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		/**
		 * La classe qui permet de définir l'action à faire quand on clique sur le bouton Quitter.
		 * Pour cela, elle implémente ActionListener qui gère les évènements liés au clique sur un composant.
		 * @author François
		 *
		 */
		private class BoutonListenerQuitter implements ActionListener {
			/**
			 * Cette méthode découle de l'interface ActionListener et gère l'évenement associé au clique sur le bouton Quitter.
			 * Elle permet de quitter l'application.
			 * @param arg0 Evénement lié au clic sur le bouton implémentant l'ActionListener
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
			
		}
	}

}
