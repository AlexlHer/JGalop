// -----------------------------
// JGalop
// Par : Alexandre l'Heritier
// -----------------------------
// Classe Grille : Représente l'ensemble des cases du plateau.
// -----------------------------

public class Grille {
	private Case[] tabCase;
	private Case[][] tabCaseJoueur;
	private Joueur[] joueurs;

	// Obliger de hardcoder les positions des cases car leurs positions ne suivent pas une logique.
	private int[][] posx = {{6, 6, 6, 6, 6, 6, 5, 4, 3, 2, 1, 0, 0, 0,
						  1, 2, 3, 4, 5, 6, 6, 6, 6, 6, 6, 7, 8, 8,
						  8, 8, 8, 8, 9, 10, 11, 12, 13, 14, 14, 14,
						  13, 12, 11, 10, 9, 8, 8, 8, 8, 8, 8, 7},
						  {1, 7, 7, 7, 7, 7, 7},
						  {1, 6, 5, 4, 3, 2, 1},
						  {10, 7, 7, 7, 7, 7, 7},
						  {10, 8, 9, 10, 11, 12, 13}};

	private int[][] posy = {{14, 13, 12, 11, 10, 9, 8, 8, 8, 8, 8, 8,
						  7, 6, 6, 6, 6, 6, 6, 5, 4, 3, 2, 1, 0, 0,
						  0, 1, 2, 3, 4, 5, 6, 6, 6, 6, 6, 6, 7, 8,
						  8, 8, 8, 8, 8, 9, 10, 11, 12, 13, 14, 14},
						  {10, 8, 9, 10, 11, 12, 13},
						  {1, 7, 7, 7, 7, 7, 7},
				  		  {1, 6, 5, 4, 3, 2, 1},
						  {10, 7, 7, 7, 7, 7, 7}};

	private String[] nom = {"aaa", "bbb", "ccc", "ddd"};


	/*
	 * Constructeur
	 */
	public Grille(){
		tabCase = new Case[52];
		tabCaseJoueur = new Case[3][7];
		joueurs = new Joueur[1];

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
				joueurs[(i - 1) % 12] = new Joueur(nom[(i - 1) % 12], tabCase[i], i, (i - 1) % 12);

				// On place tous les pions sur la case 0 du joueur.
				tabCaseJoueur[(i - 1) % 12][0].addPions(joueurs[(i - 1) % 12].getPions());
			}
		}

		// On place les cases spéciales "avant les sorties".
		for(int i = 9; i <= 48; i += 13)
			tabCase[i].setSpe(true);

		return;
	}

	/*
	 * Méthode permettant de déplacer un pion d'une case à une autre.
	 * @param p : le pion à déplacer.
	 * @param depart : la case de départ.
	 * @param arrive : la case d'arrivée.
	 */
	public void movePion(Pion p, int depart, int arrive){
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

	/*
	 * Méthode permettant d'avancer un pion de plusNb case(s).
	 * @param p : le pion à avancer.
	 * @param plusNb : le nombre de cases que le pion doit traverser.
	 */
	public void movePion(Pion p, int plusNb){
		if(p.getPos() < 0){
			tabCaseJoueur[p.getNumJoueur()][-(p.getPos()+1)].delPion(p);
			tabCaseJoueur[p.getNumJoueur()][-(p.getPos()+1+plusNb)].addPion(p);
			p.setPos(p.getPos()+plusNb);
		}
		else if(p.getPos() <= joueurs[p.getNumJoueur()].getPosCaseDeFin() &&
				p.getPos() + plusNb > joueurs[p.getNumJoueur()].getPosCaseDeFin()){
			tabCase[p.getPos()].delPion(p);
			tabCaseJoueur[p.getNumJoueur()]
					[- (-7 + (p.getPos() + plusNb - joueurs[p.getNumJoueur()].getPosCaseDeFin()))]
					.addPion(p);
			p.setPos(-8 + (p.getPos() + plusNb - joueurs[p.getNumJoueur()].getPosCaseDeFin()));
		}
		else{
			tabCase[p.getPos()].delPion(p);
			tabCase[(p.getPos()+plusNb)%52].addPion(p);
			p.setPos((p.getPos()+plusNb)%52);
		}

	}

	public Joueur[] getJoueurs() {
		return joueurs;
	}

	public Joueur getJoueur(int pos) {
		return joueurs[pos];
	}

	public Case[] getTabCase() {
		return tabCase;
	}

	public Case[][] getTabCaseJoueur() {
		return tabCaseJoueur;
	}

	public Case getCase(Pion p){
		if(p.getPos() < 0){
			return tabCaseJoueur[p.getNumJoueur()][-(p.getPos()+1)];
		}
		else{
			return tabCase[p.getPos()];
		}
	}

	public Case getCase(int pos){
		return tabCase[pos];
	}
}
