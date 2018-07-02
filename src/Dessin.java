// -----------------------------
// JGalop
// Par : Alexandre l'Heritier
// -----------------------------
// Classe Dessin : Permet de dessiner le plateau avec les pions.
// -----------------------------

import javax.swing.*;
import java.awt.*;

public class Dessin extends JPanel {

	private Grille grille;
	private int tour;
	private String joueurActuel;
	private Color[] couleurPions = {Color.cyan, Color.red, Color.green, Color.orange};
	private Joueur[] joueurs;

	public Dessin(Grille grille, Joueur[] joueurs) {
		this.grille = grille;
		this.joueurs = joueurs;
		return;
	}

	public void init(){
		creerFenetre("Projet chevaux");
		update();
	}

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

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		int[] marge = new int[2];

		int tailleGrille = 15;

		int tailleCase = calculMarge(marge, /*getHeight() / 10*/ 10, tailleGrille);

		drawGrille(g, marge[1], marge[0], getWidth() - marge[1], getHeight() - marge[0], tailleCase, tailleGrille);

		drawJoueurs(g, tailleCase, marge[0], marge[1]);

		drawVictoire(g, tailleCase, marge[0], marge[1]);

		return;
	}

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


		Polygon p = new Polygon();
		p.addPoint(6 * tailleCase + x0, 9 * tailleCase + y0);
		p.addPoint(9 * tailleCase + x0, 9 * tailleCase + y0);
		p.addPoint(tailleGrille * tailleCase / 2 + x0, tailleGrille * tailleCase / 2 + y0);
		g.setColor(couleurPions[0]);
		g.fillPolygon(p);
		g.fillRect(0 * tailleCase + x0 + 1, 9 * tailleCase + y0 + 1, tailleCase * 6 - 1, tailleCase * 6 - 1);
		g.fillRect(6 * tailleCase + x0 + 1, 13 * tailleCase + y0 + 1, tailleCase - 1, tailleCase - 1);
		for(int i = 9; i < 14; i++)
			g.fillRect(7 * tailleCase + x0 + 1, i * tailleCase + y0 + 1, tailleCase - 1, tailleCase - 1);
		g.setColor(Color.black);
		g.drawOval(8 * tailleCase + x0 + 1, 12 * tailleCase + y0 + 1, tailleCase - 2, tailleCase - 2);
		g.drawPolygon(p);
		g.setColor(Color.white);
		g.fillRect(1 * tailleCase + x0 + 1, 10 * tailleCase + y0 + 1, tailleCase * 4 - 1, tailleCase * 4 - 1);
		g.setColor(Color.black);
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

	private void drawJoueurs(Graphics g, int tailleCase, int margeHB, int margeGD){
		int taille;
		for(Case c : grille.getTabCase()){
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

			for(int i = 0; i < c.getPions().size(); i++){
				placePions(g, posPions[i], c, tailleCase, margeHB, margeGD, 1, c.getPion(i), taille);
			}

		}

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
					if(c.getPion(i).getPos() == -1)
						placePions(g, posPions[i], c, tailleCase, margeHB, margeGD, 4, c.getPion(i), taille);
					else
						placePions(g, posPions[i], c, tailleCase, margeHB, margeGD, 1, c.getPion(i), taille);
				}
			}
		}
	}

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

		if(pion.isSelected())
			g.drawOval(x - tailleCaseCible/15 - nombrePionSurCase,
					   y - tailleCaseCible/15 - nombrePionSurCase,
					tailleCaseCible / 3 + (tailleCaseCible/15) * 2 + nombrePionSurCase * 2,
					tailleCaseCible / 3 + (tailleCaseCible/15) * 2 + nombrePionSurCase * 2);

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

	private void drawVictoire(Graphics g, int tailleCase, int margeHB, int margeGD){
		int x, y;
		for(int i = 0; i < joueurs.length; i++){
			if(joueurs[i].getWin() != 0){
				x = grille.getTabCaseJoueur()[i][0].getdX() * tailleCase + margeGD + tailleCase * 3 / 2;
				y = grille.getTabCaseJoueur()[i][0].getdY() * tailleCase + margeHB + tailleCase * 3 / 2;

				String texte = Integer.toString(joueurs[i].getWin());
				Font fontCase = new Font("Arial", Font.BOLD, tailleCase*3);

				// On dessine le texte.
				g.setColor(Color.black);
				g.setFont(fontCase);

				g.drawString(texte, x - tailleCase/3, y + tailleCase*3/2);
			}
		}
	}

	public void update() {
		repaint();
		return;
	}

	public void updateTour(int tour){
		this.tour = tour;
		repaint();
		return;
	}

	public void updateJoueurActuel(String j){
		this.joueurActuel = j;
		repaint();
		return;
	}
}