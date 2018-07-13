/**
 * Représente un joueur avec ses pions et son numéro unique.
 *
 * @author Alexandre l'Heritier
 * @version 1.1
 */

public class Joueur {
	/**
	 * La position de la case de départ du joueur sur le plateau.
	 */
	private int posCaseDeDepart;

	/**
	 * La position de la case de fin du joueur.
	 */
	private int posCaseDeFin;

	/**
	 * Le nom du joueur.
	 */
	private String nom;

	/**
	 * Les pions du joueur.
	 */
	private Pion[] pions;

	/**
	 * Le numéro unique du joueur.
	 */
	private int numJoueur;

	/**
	 * Variable qui contient la place du joueur sur le podium, ou 0 s'il n'a pas encore terminé sa partie.
	 */
	private int win;


	/**
	 * Constructeur
	 *
	 * @param nom
	 * 		Le nom du joueur.
	 * @param posDepart
	 * 		La case de départ du joueur.
	 * @param numJoueur
	 * 		Le numéro unique du joueur.
	 */
	public Joueur(String nom, int posDepart, int numJoueur){
		this.nom = nom;
		this.posCaseDeDepart = posDepart;
		this.numJoueur = numJoueur;
		this.win = 0;

		// On calcul la case de fin à partir de la case de début.
		this.posCaseDeFin = posCaseDeDepart - 2;
		if(this.posCaseDeFin < 0)
			this.posCaseDeFin = 52 + this.posCaseDeFin;

		// On crée les pions.
		pions = new	Pion[4];
		for(int i = 1; i <= 4; i++){
			pions[i-1] = new Pion(-1, i, numJoueur);
		}
	}

	/**
	 * Méthode qui détermine si le joueur a terminé la partie.
	 *
	 * @return
	 * 		True si le joueur a terminé, false sinon.
	 */
	public boolean isVictoire(){
		for(Pion p : pions){
			if(p.getPos() != -2)
				return false;
		}
		return true;
	}

	/**
	 * Accesseur permettant de recupérer le nom du joueur.
	 *
	 * @return
	 * 		Le nom du joueur.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Accesseur permettant de récupérer la liste des pions du joueur.
	 *
	 * @return
	 * 		La liste des pions du joueur.
	 */
	public Pion[] getPions() {
		return pions;
	}

	/**
	 * Accesseur permettant de récupérer un pion de la liste selon la position donnée.
	 *
	 * @param pion
	 * 		La position du pion dans la liste.
	 *
	 * @return
	 * 		Le pion à la position demandé.
	 */
	public Pion getPion(int pion) {
		if(pion > 4 || pion < 1)
			return null;
		else
			return this.pions[pion-1];

	}

	/**
	 * Accesseur permettant de savoir si un des pions du joueur est séléctionné.
	 *
	 * @return
	 * 		True si un des pions du joueur est séléctionné, false sinon.
	 */
	public boolean pionIsSelected(){
		for(Pion p : pions){
			if(p.isSelected())
				return true;
		}
		return false;
	}

	/**
	 * Accesseur permettant de retourner la position de la case de départ du joueur.
	 *
	 * @return
	 * 		La position de la case de départ du joueur.
	 */
	public int getPosCaseDeDepart() {
		return posCaseDeDepart;
	}

	/**
	 * Accesseur permettant de retourner la positrion de la case d'arrivée du joueur.
	 *
	 * @return
	 * 		La position de la case de fin.
	 */
	public int getPosCaseDeFin() {
		return posCaseDeFin;
	}

	/**
	 * Accesseur permettant de retourner la position du joueur dans le classement.
	 *
	 * @return
	 * 		La position du joueur da,s le classement.
	 */
	public int getWin() {
		return win;
	}

	/**
	 * Mutateur permettant de modifier la position du joueur dans le classement de la partie.
	 *
	 * @param win
	 * 		La nouvelle position du joueur dans le classement.
	 */
	public void setWin(int win) {
		this.win = win;
	}
}
