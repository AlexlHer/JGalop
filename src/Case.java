// -----------------------------
// JGalop
// Par : Alexandre l'Heritier
// -----------------------------
// Classe Case : Représente une case sur le plateau.
// -----------------------------

import java.util.ArrayList;

public class Case {
	private boolean isSpe;
	private ArrayList<Pion> pions;
	private int dX, dY;

	/*
	 * Constructeur
	 * @param isSpe : Si la case est spécial ou non.
	 * @param x et y : la position de la case.
	 */
	public Case(boolean isSpe, int x, int y){
		this.isSpe = isSpe;
		this.pions = new ArrayList<>();
		this.dX = x;
		this.dY = y;
		return;
	}

	/*
	 * Méthode permettant de retirer un pion de la liste.
	 * @param p : le pion à retirer.
	 */
	public void delPion(Pion p){
		pions.remove(p);
		return;
	}


	/*
	 * Méthode permettant de rejouter un pion dans la liste.
	 * @param p : le pion à ajouter.
	 */
	public void addPion(Pion p){
		pions.add(p);
		return;
	}

	/*
	 * Méthode permettant de rajouter plusieurs pions dans la liste.
	 * @param p : le tableau de pion qui contient tous les pions à ajouter.
	 */
	public void addPions(Pion[] p){
		for(Pion p1 : p)
			pions.add(p1);
		return;
	}

	public ArrayList<Pion> getPions() {
		return pions;
	}

	public Pion getPion(int pos){
		return pions.get(pos);
	}

	public void setSpe(boolean spe) {
		isSpe = spe;
		return;
	}

	public int getdX() {
		return dX;
	}

	public int getdY() {
		return dY;
	}

	public boolean isSpe() {
		return isSpe;
	}
}
