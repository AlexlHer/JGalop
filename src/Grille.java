/**
 * Représente l'ensemble des cases du plateau.
 *
 * @author Alexandre l'Heritier
 * @version 2.0
 */
public class Grille {

	/**
	 * L'ensemble des cases "parcours" du plateau.
	 */
	private Case[] tabCase;

	/**
	 * L'ensemble des cases spécifiques aux joueurs.
	 */
	private Case[][] tabCaseJoueur;

	/**
	 * L'ensemble des joueurs.
	 */
	private Joueur[] joueurs;

	/**
	 * Les positions x des cases "parcours" et des cases de chaque joueur.
	 * Comme il n'y a pas de logique, les positions sont hardcodées.
	 */
	//region
	private int[][] posx = {{6, 6, 6, 6, 6, 6, 5, 4, 3, 2, 1, 0, 0, 0,
						  1, 2, 3, 4, 5, 6, 6, 6, 6, 6, 6, 7, 8, 8,
						  8, 8, 8, 8, 9, 10, 11, 12, 13, 14, 14, 14,
						  13, 12, 11, 10, 9, 8, 8, 8, 8, 8, 8, 7},
						  {1, 7, 7, 7, 7, 7, 7},
						  {1, 6, 5, 4, 3, 2, 1},
						  {10, 7, 7, 7, 7, 7, 7},
						  {10, 8, 9, 10, 11, 12, 13}};
	//endregion

	/**
	 * Les positions y des cases "parcours" et des cases de chaque joueur.
	 * Comme il n'y a pas de logique, les positions sont hardcodées.
	 */
	//region
	private int[][] posy = {{14, 13, 12, 11, 10, 9, 8, 8, 8, 8, 8, 8,
						  7, 6, 6, 6, 6, 6, 6, 5, 4, 3, 2, 1, 0, 0,
						  0, 1, 2, 3, 4, 5, 6, 6, 6, 6, 6, 6, 7, 8,
						  8, 8, 8, 8, 8, 9, 10, 11, 12, 13, 14, 14},
						  {10, 8, 9, 10, 11, 12, 13},
						  {1, 7, 7, 7, 7, 7, 7},
				  		  {1, 6, 5, 4, 3, 2, 1},
						  {10, 7, 7, 7, 7, 7, 7}};
	//endregion


	/**
	 * Constructeur
	 *
	 * @param nomJoueur
	 * 		La liste des noms des joueurs.
	 */
	public Grille(String[] nomJoueur){
		tabCase = new Case[52];
		tabCaseJoueur = new Case[nomJoueur.length][7];
		joueurs = new Joueur[nomJoueur.length];

		// On crée les cases du parcours avec les positions hardcodées.
		for(int i = 0; i < tabCase.length; i++)
			tabCase[i] = new Case(false, posx[0][i], posy[0][i]);

		// On crée les cases spécifiques aux joueurs avec les positions hardcodées.
		for(int i = 0; i < tabCaseJoueur.length; i++){
			for(int j = 0; j < tabCaseJoueur[i].length; j++)
				tabCaseJoueur[i][j] = new Case(false, posx[i+1][j], posy[i+1][j]);
		}

		// On place les cases spéciales "entrer des joueurs" et on profite de la boucle
		// pour créer les joueurs et placer leurs pions.
		for(int i = 1; i <= 40; i += 13) {
			tabCase[i].setSpe(true);
			if((i-1)%12 < joueurs.length) {
				joueurs[(i - 1) % 12] = new Joueur(nomJoueur[(i - 1) % 12], i, (i - 1) % 12);

				// On place tous les pions sur la case 0 du joueur.
				tabCaseJoueur[(i - 1) % 12][0].addPions(joueurs[(i - 1) % 12].getPions());
			}
		}

		// On place les cases spéciales "avant les sorties".
		for(int i = 9; i <= 48; i += 13)
			tabCase[i].setSpe(true);

		return;
	}

	/**
	 * Méthode permettant de déplacer un pion d'une case à une autre.
	 *
	 * @param p
	 * 		Le pion à déplacer.
	 * @param depart
	 * 		La case de départ.
	 * @param arrive
	 * 		La case d'arrivée.
	 */
	public void movePion(Pion p, int depart, int arrive){

		// Si depart/arrive est < 0, on a une position dans le tableau "joueur"
		// sinon c'est une position "parcours".
		if(depart < 0)
			tabCaseJoueur[p.getNumJoueur()][-(depart+1)].delPion(p);
		else
			tabCase[depart].delPion(p);

		if(arrive < 0){
			tabCaseJoueur[p.getNumJoueur()][-(arrive+1)].addPion(p);
			p.setPos(arrive);
		}
		else{
			tabCase[arrive].addPion(p);
			p.setPos(arrive);
		}
		return;
	}

	/**
	 * Méthode permettant d'avancer un pion de plusNb case(s).
	 *
	 * @param p
	 * 		Le pion à avancer.
	 * @param plusNb
	 * 		Le nombre de cases que le pion doit traverser.
	 */
	public void movePion(Pion p, int plusNb){
		// Si la position est < 0, on est dans les cases du joueur.
		// Ici, pas de verif du dépassement des cases du joueur avec plusNb.
		if(p.getPos() < 0){
			tabCaseJoueur[p.getNumJoueur()][-(p.getPos()+1)].delPion(p);
			tabCaseJoueur[p.getNumJoueur()][-(p.getPos()+1+plusNb)].addPion(p);
			p.setPos(p.getPos()+plusNb);
		}

		// Si le joueur est à coté de ses cases spéciales.
		else if(p.getPos() <= joueurs[p.getNumJoueur()].getPosCaseDeFin() &&
				p.getPos() + plusNb > joueurs[p.getNumJoueur()].getPosCaseDeFin()){
			tabCase[p.getPos()].delPion(p);
			tabCaseJoueur[p.getNumJoueur()]
					[- (-7 + (p.getPos() + plusNb - joueurs[p.getNumJoueur()].getPosCaseDeFin()))]
					.addPion(p);
			p.setPos(-8 + (p.getPos() + plusNb - joueurs[p.getNumJoueur()].getPosCaseDeFin()));
		}

		// Sinon on avance sur le parcours.
		else{
			tabCase[p.getPos()].delPion(p);
			tabCase[(p.getPos()+plusNb)%52].addPion(p);
			p.setPos((p.getPos()+plusNb)%52);
		}

	}

	/**
	 * Accesseur permettant de renvoyer le tableau de joueurs.
	 *
	 * @return
	 * 		Le tableau de joueurs.
	 */
	public Joueur[] getJoueurs() {
		return joueurs;
	}

	/**
	 * Accesseur permettant de renvoyer un joueur selon la position demandée.
	 *
	 * @param pos
	 * 		La position du joueur à renvoyer.
	 *
	 * @return
	 * 		Le joueur demandé.
	 */
	public Joueur getJoueur(int pos) {
		return joueurs[pos];
	}

	/**
	 * Accesseur permettant de renvoyer le tableau de cases "parcours".
	 *
	 * @return
	 *		Le tableau de cases "parcours".
	 */
	public Case[] getTabCase() {
		return tabCase;
	}

	/**
	 * Accesseur permettant de renvoyer le tableau de cases joueur.
	 *
	 * @return
	 * 		Le tableau de cases joueur.
	 */
	public Case[][] getTabCaseJoueur() {
		return tabCaseJoueur;
	}

	/**
	 * Accesseur permettant de renvoyer la case sur laquelle est situé le pion.
	 *
	 * @param p
	 * 		Le pion qui est posé sur la case demandé.
	 *
	 * @return
	 * 		La case du pion.
	 */
	public Case getCase(Pion p){
		// Si p.getPos() est < 0, on a une position dans le tableau "joueur"
		// sinon c'est une position "parcours".
		if(p.getPos() < 0){
			return tabCaseJoueur[p.getNumJoueur()][-(p.getPos()+1)];
		}
		else{
			return tabCase[p.getPos()];
		}
	}

	/**
	 * Accesseur permettant de renvoyer une case du tableau "parcours", de la position demandée.
	 *
	 * @param pos
	 * 		La position de la case demandée.
	 *
	 * @return
	 * 		La case demandée.
	 */
	public Case getCase(int pos){
		return tabCase[pos];
	}
}
