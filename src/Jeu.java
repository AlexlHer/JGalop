// -----------------------------
// JGalop
// Par : Alexandre l'Heritier
// -----------------------------
// Classe Jeu : Permet de gérer le jeu.
// -----------------------------

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Jeu {
	private Grille grille;
	private Joueur[] joueurs;
	private Dessin dessin;
	private Scanner sc = new Scanner(System.in);
	private Random random = new Random();

	public Jeu(){
		this.grille = new Grille();
		this.joueurs = grille.getJoueurs();
		this.dessin = new Dessin(grille, joueurs);
	}

	public void play(){
		int i = 0;
		boolean a;
		dessin.init();

		do{
			i++;
			a = tour(i);
		}while(!a);
		System.out.println("Partie terminée");
		return;
	}

	private boolean tour(int nbTour){
		int tirage;

		dessin.updateTour(nbTour);
		for(Joueur j : joueurs){

			if(j.getWin() != 0)
				continue;

			dessin.updateJoueurActuel(j.getNom());
			System.out.println("\n" + j.getNom());

			do {
				System.out.print("lancer de");
				sc.nextLine();

				tirage = random.nextInt(6) + 1;

				System.out.print("de : ");
				System.out.println(tirage);

				for (Pion p : j.getPions()) {
					if (p.inReserve() && tirage != 6)
						p.setSelected(false);

					else if (p.inEndParcours() && p.getPos() + tirage >= -1)
						p.setSelected(false);

					else
						p.setSelected(true);
				}
				dessin.update();
				if (j.pionIsSelected()) {
					Pion aJouer;
					boolean pass;
					do {
						pass = true;
						System.out.println("pion ?");
						aJouer = j.getPion(sc.nextInt());

						// Si la pos est pas bonne.
						if (aJouer == null)
							pass = false;

						// Si le pion n'est pas selectionné (donc pas jouable).
						else if (!aJouer.isSelected())
							pass = false;

						else if (aJouer.inReserve())
							grille.movePion(aJouer, -1, j.getPosCaseDeDepart());

						else {
							grille.movePion(aJouer, tirage);
							miam(aJouer);
						}

						dessin.update();
					} while (!pass);
				}
				else
					return allVictoire();

				dessin.update();
				victoire(j);

				for (Pion p : j.getPions())
					p.setSelected(false);

			}while(tirage == 6 && j.getWin() == 0);
		}
		return allVictoire();
	}

	public void victoire(Joueur j){
		if(j.isVictoire()){
			int sup = 0;
			for(Joueur j1 : joueurs){
				if(j1.getWin() > sup){
					sup = j1.getWin();
				}
			}
			j.setWin(sup+1);
		}
	}

	public boolean allVictoire(){
		for(Joueur j : joueurs){
			if(j.getWin() == 0){
				return false;
			}
		}
		return true;
	}

	private void miam(Pion pion){
		Case c = grille.getCase(pion);
		ArrayList<Pion> pions = c.getPions();
		if(pions.size() != 1 && !c.isSpe()){
			for(Pion p : pions){
				if(p.getNumJoueur() != pion.getNumJoueur())
					grille.movePion(p, p.getPos(), -1);
			}
		}
	}
}

