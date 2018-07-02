// -----------------------------
// JGalop
// Par : Alexandre l'Heritier
// -----------------------------
// Classe Pion : Représente un pion avec un son numéro unique composé du num du joueur et
// 				 du num du pion.
// -----------------------------

public class Pion {
	private int pos;
	private int numPion;
	private int numJoueur;
	private boolean isSelected;

	public Pion(int pos, int numPion, int numJoueur){
		this.pos = pos;
		this.numPion = numPion;
		this.numJoueur = numJoueur;
		return;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
		return;
	}

	public boolean inReserve(){
		return pos == -1;
	}

	public boolean inEndParcours(){
		return pos < 0 && pos != -1;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos){
		this.pos = pos;
	}

	public int getNumJoueur() {
		return numJoueur;
	}

	public int getNumPion() {
		return numPion;
	}
}
