/**
 * Représente un pion avec un son numéro unique composé du num du joueur et	du num du pion.
 *
 * @author Alexandre l'Heritier
 * @version 1.1
 */

public class Pion {
	/**
	 * La position du pion sur le plateau (les positions négatives correspondent
	 * aux cases du joueur, -1 la réserve, -2 l'arrivée, -3 la case juste avant
	 * l'arrivée, etc).
	 */
	private int pos;

	/**
	 * Le numéro du pion, non unique sur le plateau, dépend du joueur.
	 */
	private int numPion;

	/**
	 * Le numéro du joueur à qui appartient le pion.
	 */
	private int numJoueur;

	/**
	 * Permet de savoir si le pion est séléctionné ou non.
	 */
	private boolean isSelected;


	/**
	 * Constructeur
	 *
	 * @param pos
	 * 		Position du pion sur le plateau.
	 * @param numPion
	 * 		Numéro du pion.
	 * @param numJoueur
	 * 		Numéro du joueur liée au pion.
	 */
	public Pion(int pos, int numPion, int numJoueur){
		this.pos = pos;
		this.numPion = numPion;
		this.numJoueur = numJoueur;
		return;
	}

	/**
	 * Accesseur permettant de savoir si le pion est sélectionnée.
	 *
	 * @return
	 * 		True si le pion est sélectionné, false sinon.
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * Mutateur permettant de modifier la séléction du pion.
	 *
	 * @param selected
	 * 		True si le pion dopit être sélectionné, false sinon.
	 */
	public void setSelected(boolean selected) {
		isSelected = selected;
		return;
	}

	/**
	 * Accesseur permettant de renvoyer true si le pion est dans la réserve, false sinon.
	 *
	 * @return
	 * 		True si le pion est dans la réserve, false sinon.
	 */
	public boolean inReserve(){
		return pos == -1;
	}

	/**
	 * Accesseur permettant de savoir si le pion est dans la fin du parcours.
	 *
	 * @return
	 * 		True si le pion est dans la fin du parcours, false sinon.
	 */
	public boolean inEndParcours(){
		return pos < 0 && pos != -1;
	}

	/**
	 * Accesseur permettant de renvoyer la position du pion.
	 *
	 * @return
	 * 		La position du pion.
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * Mutateur permettant de modifier la position du joueur.
	 *
	 * @param pos
	 * 		La nouvelle position du joueur.
	 */
	public void setPos(int pos){
		this.pos = pos;
	}

	/**
	 * Accesseur permettant de retourner le numéro du joueur liée au pion.
	 *
	 * @return
	 * 		Le numéro du joueur.
	 */
	public int getNumJoueur() {
		return numJoueur;
	}

	/**
	 * Accesseur permettant de renvoyer le numéro du pion.
	 *
	 * @return
	 * 		Le numéro du pion.
	 */
	public int getNumPion() {
		return numPion;
	}
}
