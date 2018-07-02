// -----------------------------
// JGalop
// Par : Alexandre l'Heritier
// -----------------------------
// Classe Joueur : Représente un joueur avec ses pions et son numéro unique.
// -----------------------------

public class Joueur {
	private Case deDepart;
	private int posCaseDeDepart;
	private int posCaseDeFin;
	private String nom;
	private Pion[] pions;
	private int numJoueur;
	private int win;

	public Joueur(String nom, Case casse, int posDepart, int numJoueur){
		this.nom = nom;
		this.deDepart = casse;
		this.posCaseDeDepart = posDepart;
		this.numJoueur = numJoueur;
		this.win = 0;

		this.posCaseDeFin = posCaseDeDepart - 2;
		if(this.posCaseDeFin < 0)
			this.posCaseDeFin = 52 + this.posCaseDeFin;

		pions = new	Pion[4];
		for(int i = 1; i <= 4; i++){
			pions[i-1] = new Pion(-1, i, numJoueur);
		}
	}

	public boolean isVictoire(){
		for(Pion p : pions){
			if(p.getPos() != -2)
				return false;
		}
		return true;
	}

	public String getNom() {
		return nom;
	}

	public Pion[] getPions() {
		return pions;
	}

	public Pion getPion(int pion) {
		if(pion > 4 || pion < 1)
			return null;
		else
			return this.pions[pion-1];

	}

	public boolean pionIsSelected(){
		for(Pion p : pions){
			if(p.isSelected())
				return true;
		}
		return false;
	}

	public int getPosCaseDeDepart() {
		return posCaseDeDepart;
	}

	public int getPosCaseDeFin() {
		return posCaseDeFin;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}
}
