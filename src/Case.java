import java.util.ArrayList;

/**
 * Représente une case sur le plateau.
 * Une case spéciale est une case sur laquelle les joueurs sont invincibles.
 *
 * @author Alexandre l'Heritier
 * @version 1.1
 */
public class Case {

	/**
	 * Case spéciale ou non.
	 */
	private boolean isSpe;

	/**
	 * Liste des pions sur la case.
	 */
	private ArrayList<Pion> pions;

	/**
	 * Position de la case dans la grille.
	 */
	private int dX, dY;


	/**
	 * Constructeur
	 *
	 * @param isSpe
	 * 		Si la case est spécial ou non.
	 * @param x
	 * 		Position x de la case.
	 * @param y
	 * 		Position y de la case.
	 */
	public Case(boolean isSpe, int x, int y){
		this.isSpe = isSpe;
		this.pions = new ArrayList<>();
		this.dX = x;
		this.dY = y;
		return;
	}

	/**
	 * Méthode permettant de retirer un pion de la liste.
	 *
	 * @param p
	 * 		Le pion à retirer.
	 */
	public void delPion(Pion p){
		pions.remove(p);
		return;
	}

	/**
	 * Méthode permettant de rejouter un pion dans la liste.
	 *
	 * @param p
	 * 		Le pion à ajouter.
	 */
	public void addPion(Pion p){
		pions.add(p);
		return;
	}

	/**
	 * Méthode permettant de rajouter plusieurs pions dans la liste.
	 *
	 * @param p
	 * 		Le tableau de pion qui contient tous les pions à ajouter.
	 */
	public void addPions(Pion[] p){
		for(Pion p1 : p)
			pions.add(p1);
		return;
	}

	/**
	 * Accesseur permettant de renvoyer la liste des pions.
	 *
	 * @return La liste des pions.
	 */
	public ArrayList<Pion> getPions() {
		return pions;
	}

	/**
	 * Accesseur permettant de renvoyer un pion selon sa position dans la liste.
	 *
	 * @param pos
	 * 		La position du pion dans la liste.
	 *
	 * @return
	 * 		Le pion à la position demandée.
	 */
	public Pion getPion(int pos){
		return pions.get(pos);
	}

	/**
	 * Mutateur permettant de modifier la spécificité de la case.
	 *
	 * @param spe
	 * 		La nouvelle valeur de isSpe.
	 */
	public void setSpe(boolean spe) {
		isSpe = spe;
		return;
	}

	/**
	 * Accesseur permettant de renvoyer la position x de la case dans la grille.
	 *
	 * @return
	 * 		La position x de la case dans la grille.
	 */
	public int getdX() {
		return dX;
	}

	/**
	 * Accesseur permettant de renvoyer la position y de la case dans la grille.
	 *
	 * @return
	 * 		La position y de la case dans la grille.
	 */
	public int getdY() {
		return dY;
	}

	/**
	 * Accesseur permettant de renvoyer la valeur de isSpe.
	 *
	 * @return
	 * 		La valeur de isSpe.
	 */
	public boolean isSpe() {
		return isSpe;
	}
}
