import javax.swing.*;
import java.awt.*;

/**
 * Permet de dessiner le plateau avec les pions.
 *
 * @author Alexandre l'Heritier
 * @version 2.0
 */
public class Dessin extends JPanel {

	/**
	 * Le plateau qui contient les cases.
	 */
	private Grille grille;

	/**
	 * Le nombre de tour effectué dans la partie en cours.
	 */
	private int tour;

	/**
	 * Le nom du joueur qui joue actuellement.
	 */
	private Joueur joueurActuel;

	/**
	 * On garde la valeur du dé.
	 */
	private int de;

	/**
	 * Les differentes couleurs des joueurs.
	 */
	private Color[] couleurPions = {Color.cyan, Color.red, Color.green, Color.orange};

	/**
	 * Liste qui contient tous les joueurs.
	 */
	private Joueur[] joueurs;


	/**
	 * Constructeur
	 * @param grille
	 * 		Le plateau de jeu qui contient toutes les cases.
	 * @param joueurs
	 * 		Les joueurs.
	 */
	public Dessin(Grille grille, Joueur[] joueurs) {
		this.grille = grille;
		this.joueurs = joueurs;
		return;
	}

	/**
	 * Méthode permettant de créer la fenètre et de l'initialiser.
	 */
	public void init(){
		creerFenetre("JGalop");
		update();
		return;
	}

	/**
	 * Méthode permettant de créer la fenètre.
	 *
	 * @param name
	 * 		Le titre de la fenètre.
	 */
	private void creerFenetre(String name) {
		// On crée une JFrame avec le nom de la fenètre.
		JFrame fenetre = new JFrame(name);

		// On donne l'instruction de fermer la fenètre si on clique sur la croix.
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// On ajoute JPanel dans la JFrame.
		fenetre.getContentPane().add(this);

		// On donne une dimension initiale à la fenètre.
		fenetre.setPreferredSize(new Dimension(840, 600));

		// On place le JPanel dans la JFrame.
		fenetre.pack();

		// On rend la fenètre visible.
		fenetre.setVisible(true);
		return;
	}

	/**
	 * Méthode permettant de dessiner tous les éléments du plateau.
	 *
	 * @param g
	 * 		Contient tous les éléments.
	 */
	public void paintComponent(Graphics g){
		// On efface les anciens éléments.
		super.paintComponent(g);

		// On calcul la marge.
		int[] marge = new int[2];
		final int tailleGrille = 15;
		int tailleCase = calculMarge(marge, getHeight() / 10, tailleGrille);

		// On dessine le plateau de jeu.
		drawGrille(g, marge[1], marge[0], getWidth() - marge[1], getHeight() - marge[0], tailleCase, tailleGrille);

		// On dessine les pions des joueurs.
		drawJoueurs(g, tailleCase, marge[0], marge[1]);

		// On dessine les positions podium si necessaire.
		drawVictoire(g, tailleCase, marge[0], marge[1]);

		drawInfos(g, tailleCase);
		return;
	}

	/**
	 * Méthode permettant de calculer les marges autour de la grille selon la taille de la fenètre.
	 *
	 * @param tab
	 * 		Le tableau qui contiendera les deux marges calculées.
	 * @param margeMini
	 * 		La marge minimale à utiliser.
	 * @param tailleGrille
	 * 		La taille de la grille.
	 *
	 *
	 * @return
	 * 		La taille des cases.
	 */
	private int calculMarge(int[] tab, int margeMini, int tailleGrille){

		// Calcul permettant d'inclure la taille des cases en plus de la hauteur (dans le cas ou la grille représenterai un rectangle).
		int tailleCase = 0;

		// Si on a une fenètre "en mode paysage" (en prenant en compte le "mode" de la grille).
		if(getWidth() > getHeight() + tailleCase){

			// On prend la hauteur de la fenètre, on enleve les deux marges donc on aura la hauteur que doit faire
			// la grille. Après, suffit de diviser par le nombre de lignes pour avoir la taille d'une case (que
			// l'on veux carré).
			tailleCase = (getHeight() - margeMini*2) / tailleGrille;

			// Les marges Haute et Basse prennent la marge minimal
			tab[0] = margeMini;

			// On prend la largeur, on enleve la largeur de la grille donc il nous reste les deux marges,
			// on divise par deux pour avoir la marge GaucheDroite.
			tab[1] = (getWidth() - (tailleCase * tailleGrille)) / 2;
		}

		// Si on a une fenètre "en mode portrait".
		else{
			tailleCase = (getWidth() - margeMini*2) / tailleGrille;
			tab[0] = (getHeight() - (tailleCase * tailleGrille)) / 2;
			tab[1] = margeMini;
		}
		return tailleCase;
	}

	/**
	 * Méthode permettant de dessiner la grille avec des éléments dessus pour devenir un plateau de petit chevaux.
	 *
	 * @param g
	 * 		L'objet Graphics qui contient les éléments à dessiner.
	 * @param x0
	 * 		La position x0 de la grille.
	 * @param y0
	 * 		La position y0 de la grille.
	 * @param x1
	 * 		La position x1 de la grille.
	 * @param y1
	 * 		La position y1 de la grille.
	 * @param tailleCase
	 * 		La taille des cases voulu.
	 * @param tailleGrille
	 * 		Hauteur largeur de la grille, en nombre de case.
	 */
	private void drawGrille(Graphics g, int x0, int y0, int x1, int y1, int tailleCase, int tailleGrille){
		// Un peu de trigo pour calculer les positions des lignes à dessiner.
		for(int i = 0; i < tailleGrille+1; i++) {
			int x0temp = x0 + ((x1 - x0) / tailleGrille) * i;
			int y0temp = y0;
			int x1temp = x0 + ((x1 - x0) / tailleGrille) * i;

			// Au lieu de y1, on calcul les positions des derniers points, pour éviter de faire dépasser
			// les lignes.
			int y1temp = y0 + ((y1 - y0) / tailleGrille) * tailleGrille;
			g.drawLine(x0temp, y0temp, x1temp, y1temp);
		}
		for(int i = 0; i < tailleGrille+1; i++) {
			int x0temp = x0;
			int y0temp = y0 + ((y1 - y0) / tailleGrille) * i;
			int x1temp = x0 + ((x1 - x0) / tailleGrille) * tailleGrille;
			int y1temp = y0 + ((y1 - y0) / tailleGrille) * i;
			g.drawLine(x0temp, y0temp, x1temp, y1temp);
		}


		// On dessine le triangle du milieu.
		Polygon p = new Polygon();
		p.addPoint(6 * tailleCase + x0, 9 * tailleCase + y0);
		p.addPoint(9 * tailleCase + x0, 9 * tailleCase + y0);
		p.addPoint(tailleGrille * tailleCase / 2 + x0, tailleGrille * tailleCase / 2 + y0);
		g.setColor(couleurPions[0]);
		g.fillPolygon(p);

		// On dessine le grand carré réserve de couleur joueur.
		g.fillRect(0 * tailleCase + x0 + 1, 9 * tailleCase + y0 + 1, tailleCase * 6 - 1, tailleCase * 6 - 1);

		// On dessine les petits carré départ/fin du joueur.
		g.fillRect(6 * tailleCase + x0 + 1, 13 * tailleCase + y0 + 1, tailleCase - 1, tailleCase - 1);
		for(int i = 9; i < 14; i++)
			g.fillRect(7 * tailleCase + x0 + 1, i * tailleCase + y0 + 1, tailleCase - 1, tailleCase - 1);

		g.setColor(Color.black);

		// On dessine le rond de la case spéciale et le contour du triangle.
		g.drawOval(8 * tailleCase + x0 + 1, 12 * tailleCase + y0 + 1, tailleCase - 2, tailleCase - 2);
		g.drawPolygon(p);

		g.setColor(Color.white);

		// On dessine le carré blanc dans la réserve.
		g.fillRect(1 * tailleCase + x0 + 1, 10 * tailleCase + y0 + 1, tailleCase * 4 - 1, tailleCase * 4 - 1);

		g.setColor(Color.black);

		// On dessine le contour du carré blanc.
		g.drawRect(1 * tailleCase + x0, 10 * tailleCase + y0, tailleCase * 4, tailleCase * 4);


		p = new Polygon();
		p.addPoint(6 * tailleCase + x0, 6 * tailleCase + y0);
		p.addPoint(6 * tailleCase + x0, 9 * tailleCase + y0);
		p.addPoint(tailleGrille * tailleCase / 2 + x0, tailleGrille * tailleCase / 2 + y0);
		g.setColor(couleurPions[1]);
		g.fillPolygon(p);
		g.fillRect(0 * tailleCase + x0 + 1, 0 * tailleCase + y0 + 1, tailleCase * 6 - 1, tailleCase * 6 - 1);
		g.fillRect(1 * tailleCase + x0 + 1, 6 * tailleCase + y0 + 1, tailleCase - 1, tailleCase - 1);
		for(int i = 1; i < 6; i++)
			g.fillRect(i * tailleCase + x0 + 1, 7 * tailleCase + y0 + 1, tailleCase - 1, tailleCase - 1);
		g.setColor(Color.black);
		g.drawOval(2 * tailleCase + x0 + 1, 8 * tailleCase + y0 + 1, tailleCase - 2, tailleCase - 2);
		g.drawPolygon(p);
		g.setColor(Color.white);
		g.fillRect(1 * tailleCase + x0 + 1, 1 * tailleCase + y0 + 1, tailleCase * 4 - 1, tailleCase * 4 - 1);
		g.setColor(Color.black);
		g.drawRect(1 * tailleCase + x0, 1 * tailleCase + y0, tailleCase * 4, tailleCase * 4);

		p = new Polygon();
		p.addPoint(9 * tailleCase + x0, 6 * tailleCase + y0);
		p.addPoint(6 * tailleCase + x0, 6 * tailleCase + y0);
		p.addPoint(tailleGrille * tailleCase / 2 + x0, tailleGrille * tailleCase / 2 + y0);
		g.setColor(couleurPions[2]);
		g.fillPolygon(p);
		g.fillRect(9 * tailleCase + x0 + 1, 0 * tailleCase + y0 + 1, tailleCase * 6 - 1, tailleCase * 6 - 1);
		g.fillRect(8 * tailleCase + x0 + 1, 1 * tailleCase + y0 + 1, tailleCase - 1, tailleCase - 1);
		for(int i = 1; i < 6; i++)
			g.fillRect(7 * tailleCase + x0 + 1, i * tailleCase + y0 + 1, tailleCase - 1, tailleCase - 1);
		g.setColor(Color.black);
		g.drawOval(6 * tailleCase + x0 + 1, 2 * tailleCase + y0 + 1, tailleCase - 2, tailleCase - 2);
		g.drawPolygon(p);
		g.setColor(Color.white);
		g.fillRect(10 * tailleCase + x0 + 1, 1 * tailleCase + y0 + 1, tailleCase * 4 - 1, tailleCase * 4 - 1);
		g.setColor(Color.black);
		g.drawRect(10 * tailleCase + x0, 1 * tailleCase + y0, tailleCase * 4, tailleCase * 4);

		p = new Polygon();
		p.addPoint(9 * tailleCase + x0, 9 * tailleCase + y0);
		p.addPoint(9 * tailleCase + x0, 6 * tailleCase + y0);
		p.addPoint(tailleGrille * tailleCase / 2 + x0, tailleGrille * tailleCase / 2 + y0);
		g.setColor(couleurPions[3]);
		g.fillPolygon(p);
		g.fillRect(9 * tailleCase + x0 + 1, 9 * tailleCase + y0 + 1, tailleCase * 6 - 1, tailleCase * 6 - 1);
		g.fillRect(13 * tailleCase + x0 + 1, 8 * tailleCase + y0 + 1, tailleCase - 1, tailleCase - 1);
		for(int i = 9; i < 14; i++)
			g.fillRect(i * tailleCase + x0 + 1, 7 * tailleCase + y0 + 1, tailleCase - 1, tailleCase - 1);
		g.setColor(Color.black);
		g.drawOval(12 * tailleCase + x0 + 1, 6 * tailleCase + y0 + 1, tailleCase - 2, tailleCase - 2);
		g.drawPolygon(p);
		g.setColor(Color.white);
		g.fillRect(10 * tailleCase + x0 + 1, 10 * tailleCase + y0 + 1, tailleCase * 4 - 1, tailleCase * 4 - 1);
		g.setColor(Color.black);
		g.drawRect(10 * tailleCase + x0, 10 * tailleCase + y0, tailleCase * 4, tailleCase * 4);

		return;
	}

	/**
	 * Méthode permettant de dessiner les pions des joueurs.
	 *
	 * @param g
	 * 		L'objet Graphics qui contient les éléments à dessiner.
	 * @param tailleCase
	 * 		La taille des cases.
	 * @param margeHB
	 * 		La taille des marges haut/bas.
	 * @param margeGD
	 * 		La taille des marges gauche/droite.
	 */
	private void drawJoueurs(Graphics g, int tailleCase, int margeHB, int margeGD){
		int taille;

		// On dessine les pions des cases "parcours".
		for(Case c : grille.getTabCase()){

			// Selon le nombre de pion sur la case, on les place dans le bonne ordre.
			taille = c.getPions().size();
			int[] posPions;
			if (taille == 1) 		posPions = new int[]{5};
			else if (taille == 2) 	posPions = new int[]{7, 3};
			else if (taille == 3)	posPions = new int[]{7, 5, 3};
			else if (taille == 4)	posPions = new int[]{7, 1, 9, 3};
			else if (taille == 5)	posPions = new int[]{7, 1, 9, 3, 5};
			else if (taille == 6) 	posPions = new int[]{7, 8, 9, 1, 2, 3};
			else if (taille == 7) 	posPions = new int[]{7, 8, 9, 1, 2, 3, 5};
			else if (taille == 8) 	posPions = new int[]{7, 8, 9, 1, 2, 3, 4, 6};
			else 					posPions = new int[]{7, 8, 9, 1, 2, 3, 4, 5, 6};

			for(int i = 0; i < c.getPions().size(); i++) {
				placePions(g, posPions[i], c, tailleCase, margeHB, margeGD, 1, c.getPion(i), taille);
			}

		}

		// On dessine les pions des cases "joueurs".
		for(Case[] ct : grille.getTabCaseJoueur()){
			for(Case c : ct) {
				taille = c.getPions().size();
				int[] posPions;
				if (taille == 1) 	  posPions = new int[]{5};
				else if (taille == 2) posPions = new int[]{7, 3};
				else if (taille == 3) posPions = new int[]{7, 5, 3};
				else if (taille == 4) posPions = new int[]{7, 1, 9, 3};
				else if (taille == 5) posPions = new int[]{7, 1, 9, 3, 5};
				else if (taille == 6) posPions = new int[]{7, 8, 9, 1, 2, 3};
				else if (taille == 7) posPions = new int[]{7, 8, 9, 1, 2, 3, 5};
				else if (taille == 8) posPions = new int[]{7, 8, 9, 1, 2, 3, 4, 6};
				else 				  posPions = new int[]{7, 8, 9, 1, 2, 3, 4, 5, 6};

				for (int i = 0; i < c.getPions().size(); i++) {
					// Si la position est -1, c'est que la case est une reserve et donc la case est 4x plus grande.
					if(c.getPion(i).getPos() == -1)
						placePions(g, posPions[i], c, tailleCase, margeHB, margeGD, 4, c.getPion(i), taille);
					else
						placePions(g, posPions[i], c, tailleCase, margeHB, margeGD, 1, c.getPion(i), taille);
				}
			}
		}
	}

	/**
	 *
	 * @param g
	 * 		L'objet Graphics qui contient les éléments à dessiner.
	 * @param pos
	 * 		La position du pion sur la case (case représentée par le pavé numérique et
	 * 		chaque position, un chiffre sur celui-ci).
	 * @param casse
	 * 		La case où le pion est placé.
	 * @param tailleCases
	 * 		La taille des cases.
	 * @param margeHB
	 * 		La taille des marges haut/bas.
	 * @param margeGD
	 * 		La taille des marges gauche/droite.
	 * @param ratio
	 * 		Le ratio d'agrandissement de la case du pion (case de taille 3x3 cases, par exemple).
	 * @param pion
	 * 		Le pion à placer.
	 * @param nombrePionSurCase
	 * 		Le nombre de pion qu'il y a sur la case.
	 */
	private void placePions(Graphics g, int pos, Case casse, int tailleCases, int margeHB, int margeGD, int ratio, Pion pion, int nombrePionSurCase){
		// Selon la position qu'on veux, on donne un x et un y.
		int x = 0, y = 0;

		int tailleCaseCible = tailleCases * ratio;
		nombrePionSurCase = nombrePionSurCase == 1 ? tailleCaseCible / 5 : 0;

		if(pos == 7 || pos == 8 || pos == 9) y = casse.getdY() * tailleCases + margeHB + tailleCaseCible * 3 / 16 - (tailleCaseCible / 6);
		if(pos == 4 || pos == 5 || pos == 6) y = casse.getdY() * tailleCases + margeHB + tailleCaseCible / 2 - (tailleCaseCible / 6);
		if(pos == 1 || pos == 2 || pos == 3) y = casse.getdY() * tailleCases + margeHB + tailleCaseCible * 13 / 16 - (tailleCaseCible / 6);

		if(pos == 7 || pos == 4 || pos == 1) x = casse.getdX() * tailleCases + margeGD + tailleCaseCible * 3 / 16 - (tailleCaseCible / 6);
		if(pos == 8 || pos == 5 || pos == 2) x = casse.getdX() * tailleCases + margeGD + tailleCaseCible / 2 - (tailleCaseCible / 6);
		if(pos == 9 || pos == 6 || pos == 3) x = casse.getdX() * tailleCases + margeGD + tailleCaseCible * 13 / 16 - (tailleCaseCible / 6);

		// Et on place le pion à la position voulu (rond plein coloré et rond vide bord noir).
		g.setColor(couleurPions[pion.getNumJoueur()]);
		g.fillOval(x - nombrePionSurCase, y - nombrePionSurCase, tailleCaseCible / 3 + nombrePionSurCase * 2, tailleCaseCible / 3 + nombrePionSurCase * 2);
		g.setColor(Color.black);
		g.drawOval(x - nombrePionSurCase, y - nombrePionSurCase, tailleCaseCible / 3 + nombrePionSurCase * 2, tailleCaseCible / 3 + nombrePionSurCase * 2);

		// On ajoute un rond autour pour signifier qu'on peut le deplacer.
		if(pion.isSelected())
			g.drawOval(x - tailleCaseCible/15 - nombrePionSurCase,
					   y - tailleCaseCible/15 - nombrePionSurCase,
					tailleCaseCible / 3 + (tailleCaseCible/15) * 2 + nombrePionSurCase * 2,
					tailleCaseCible / 3 + (tailleCaseCible/15) * 2 + nombrePionSurCase * 2);

		// On dessine le numéro dans les pions.
		String texte = Integer.toString(pion.getNumPion());
		Font fontCase = new Font("Arial", Font.BOLD, (tailleCaseCible / 3 + (tailleCaseCible/15) * 2 + nombrePionSurCase * 2) / 2);

		// On dessine le texte.
		g.setColor(Color.black);
		g.setFont(fontCase);

		g.drawString(texte,
				x - nombrePionSurCase - ((tailleCaseCible / 3 + (tailleCaseCible/15) * 2 + nombrePionSurCase * 2) / 2)/4 + (tailleCaseCible / 3 + nombrePionSurCase * 2)/2,
				y - nombrePionSurCase - ((tailleCaseCible / 3 + (tailleCaseCible/15) * 2 + nombrePionSurCase * 2) / 2)/3 + (tailleCaseCible / 3 + nombrePionSurCase * 2));

		return;
	}

	/**
	 * Méthode permettant de dessiner la position des joueurs une fois qu'ils ont terminé leur partie.
	 *
	 * @param g
	 * 		L'objet Graphics qui contient les éléments à dessiner.
	 * @param tailleCase
	 * 		La taille des cases.
	 * @param margeHB
	 * 		La taille des marges haut/bas.
	 * @param margeGD
	 * 		La taille des marges gauche/droite.
	 */
	private void drawVictoire(Graphics g, int tailleCase, int margeHB, int margeGD){
		int x, y;

		// Pour tous les joueurs.
		for(int i = 0; i < joueurs.length; i++){

			// S'il a terminé la partie.
			if(joueurs[i].getWin() != 0){
				// On calcul les x et les y pour centrer le chiffre dans la réserve..
				x = grille.getTabCaseJoueur()[i][0].getdX() * tailleCase + margeGD + tailleCase * 3 / 2;
				y = grille.getTabCaseJoueur()[i][0].getdY() * tailleCase + margeHB + tailleCase * 3 / 2;

				// On dessine le chiffre.
				String texte = Integer.toString(joueurs[i].getWin());
				Font fontCase = new Font("Arial", Font.BOLD, tailleCase*3);

				// On dessine le texte.
				g.setColor(Color.black);
				g.setFont(fontCase);

				g.drawString(texte, x - tailleCase/3, y + tailleCase*3/2);
			}
		}
	}

	/**
	 * Méthode permettant de dessiner la ligne d'info.
	 *
	 * @param g
	 * 		L'objet Graphics qui contient les éléments à dessiner.
	 * @param tailleCase
	 * 		La taille des cases.
	 */
	private void drawInfos(Graphics g, int tailleCase){
		// Couleur des lignes.
		g.setColor(new Color(182, 182, 182));

		// Dessin des lignes.
		g.fillRect(0, 0, this.getWidth(), tailleCase);
		g.fillRect(0, this.getHeight() - tailleCase / 2, this.getWidth(), tailleCase);

		// On cherche la position du joueur dans la liste de joueur.
		int posJoueur;
		for(posJoueur = 0; posJoueur < joueurs.length; posJoueur++){
			if(joueurs[posJoueur] == joueurActuel)
				break;
		}

		// On crée le texte à afficher, la police et on calcul la taille du texte.
		String aEcrire = "C'est au tour de : " + joueurActuel.getNom();

		Font fontCase = new Font("Arial", Font.BOLD, tailleCase/2);
		g.setFont(fontCase);
		int tailleTexte = g.getFontMetrics().stringWidth(aEcrire);

		// On dessine le texte.
		g.setColor(Color.black);
		g.drawString(aEcrire, this.getWidth() / 2 - tailleTexte/2, tailleCase / 2 + tailleCase/4);

		// On dessine le pion du joueur à coté de son nom (this.getWidth() / 100 pour séparer le pion du texte).
		// tailleCase - (tailleCase / 3) = 2*tailleCase/3
		// (tailleCase - (2 * tailleCase / 3))/2 = tailleCase / 6
		g.setColor(couleurPions[posJoueur]);
		g.fillOval(this.getWidth() / 2 + tailleTexte / 2 + this.getWidth() / 100, tailleCase / 6, 2 * tailleCase / 3, 2 * tailleCase / 3);
		g.setColor(Color.black);
		g.drawOval(this.getWidth() / 2 + tailleTexte / 2 + this.getWidth() / 100, tailleCase / 6, 2 * tailleCase / 3, 2 * tailleCase / 3);
	}

	/**
	 * Méthode permettant de redessiner le plateau en prenant en compte les modifications dans les differents éléments.
	 */
	public void update() {
		repaint();
		return;
	}

	/**
	 * Méthode permettant de mettre à jour le nombre de tour puis de redessiner le plateau.
	 *
	 * @param tour
	 * 		Le nouveau nombre de tour.
	 */
	public void updateTour(int tour){
		this.tour = tour;
		repaint();
		return;
	}

	/**
	 * Méthode permettant de mettre à jour le joueur actuel puis de redessiner le plateau.
	 *
	 * @param j
	 * 		Le joueur qui joue actuellement.
	 */
	public void updateJoueurActuel(Joueur j){
		this.joueurActuel = j;
		repaint();
		return;
	}

	/**
	 * Méthode permettant de mettre à jour la valeur du dé puis de redessiner le plateau.
	 *
	 * @param valeur
	 * 		La nouvelle valeur du dé.
	 */
	public void updateDe(int valeur){
		this.de = valeur;
		repaint();
		return;
	}
}