import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Permet de gérer le jeu.
 *
 * @author Alexandre l'Heritier
 * @version 2.0
 */
public class Jeu {

	/**
	 * Plateau de jeu.
	 */
	private Grille grille;

	/**
	 * Liste de joueur.
	 */
	private Joueur[] joueurs;

	/**
	 * Le dessin du jeu.
	 */
	private Dessin dessin;

	private Scanner sc = new Scanner(System.in);
	private Random random = new Random();

	/**
	 * Constructeur
	 */
	public Jeu(){
		this.grille = new Grille(demandeJoueur());
		this.joueurs = grille.getJoueurs();
		this.dessin = new Dessin(grille, joueurs);
	}

	/**
	 * Methode permettant de demander le nombre de joueur et leurs noms.
	 *
	 * @return
	 * 		Liste qui contient les noms des joueurs.
	 */
	public String[] demandeJoueur(){
		int nbJoueur;
		boolean pass;
		String[] nomJoueur;

		// On demande le nombre de joueurs qui vont jouer, en verifiant que le nombre est valide.
		do {
			System.out.println("\nCombien de joueur vont jouer ? (1-4)");
			try{
				nbJoueur = sc.nextInt();
			}
			catch(java.util.InputMismatchException e){
				nbJoueur = 0;
				sc.nextLine();
			}
			sc.nextLine();

			// Si le nombre de joueur est non valide, on redemande.
			if (nbJoueur < 1 || nbJoueur > 4)
				pass = false;

			else {
				pass = true;
			}
		} while (!pass);

		nomJoueur = new String[nbJoueur];

		// On demande les noms des joueurs.
		for(int i = 0; i < nbJoueur; i++){
			System.out.println("Quel est le nom du joueur " + Integer.toString(i+1));
			nomJoueur[i] = sc.nextLine();
		}

		return nomJoueur;
	}

	/**
	 * Méthode permettant de lancer le jeu.
	 */
	public void play(){
		int i = 0;
		boolean a;

		// On initialise la fenètre dessin.
		dessin.init();

		// Boucle qui tourne tant que la partie continue.
		do{
			i++;
			a = tour(i);
		}while(!a);

		System.out.println("Partie terminée");
		return;
	}

	/**
	 * Méthode effectuant un tour de jeu.
	 *
	 * @param nbTour
	 * 		Le nombre de tour qui a été effectué.
	 *
	 * @return
	 * 		True si la partie est terminée, false sinon.
	 */
	private boolean tour(int nbTour){
		int tirage;

		// Permet de mettre à jour le nombre de tour dans dessin.
		dessin.updateTour(nbTour);

		// On fait jouer tous les joueurs.
		for(Joueur j : joueurs){

			// Si le joueur a terminé la partie, on le passe.
			if(j.getWin() != 0)
				continue;

			// On met à jour le nom du joueur dans dessin.
			dessin.updateJoueurActuel(j);
			System.out.println("\n" + j.getNom());

			// Tant que le joueur fait des 6 et qu'il n'a toujours pas gagné.
			do {

				// On fait une pause pour que le joueur lance le dé.
				System.out.print("Appuyer sur Entrer pour lancer le dé.");
				sc.nextLine();

				// On fait un tirage avec le dé.
				tirage = random.nextInt(6) + 1;
				dessin.updateDe(tirage);

				System.out.print("Vous avez tiré le numéro ");
				System.out.println(tirage);

				// Pour tous les pions, on determine s'il peut se déplacer.
				for (Pion p : j.getPions()) {

					// Si le pion est dans la réserve et que le dé n'est pas à 6.
					if (p.inReserve() && tirage != 6)
						p.setSelected(false);

					// Si le pion est à la fin du parcours et que le dé produit un dépassement.
					else if (p.inEndParcours() && p.getPos() + tirage >= -1)
						p.setSelected(false);

					else
						p.setSelected(true);
				}

				// On met à jour le dessin pour prendre en compte les ronds autour des pions.
				dessin.update();

				// Si un des pions est sélectionné, on entre.
				if (j.pionIsSelected()) {
					Pion aJouer;
					boolean pass;

					// Tant qu'un pion n'a pas été déplacé.
					do {
						pass = true;

						// On demande un pion.
						System.out.println("\nQuel pion voulez-vous déplacer ?");
						try{
							aJouer = j.getPion(sc.nextInt());
						}
						catch(java.util.InputMismatchException e){
							aJouer = null;
							sc.nextLine();
						}
						sc.nextLine();


						// Si la pos est pas bonne.
						if (aJouer == null)
							pass = false;

						// Si le pion n'est pas selectionné (donc pas jouable).
						else if (!aJouer.isSelected())
							pass = false;

						// Si le pion est dans la réserve, on le déplace dans le parcours.
						else if (aJouer.inReserve())
							grille.movePion(aJouer, -1, j.getPosCaseDeDepart());

						// Sinon, on le déplace normalement.
						else {
							grille.movePion(aJouer, tirage);
							miam(aJouer);
						}

						// On update pour déplacer les pions.
						dessin.update();
					} while (!pass);
				}
				else{}
					//return allVictoire();

				// On update et on verifie si le joueur a terminé.
				dessin.update();
				victoire(j);

				// On désélectionne tous les pions.
				for (Pion p : j.getPions())
					p.setSelected(false);

			}while(tirage == 6 && j.getWin() == 0);
		}
		return allVictoire();
	}

	/**
	 * Méthode permettant de mettre la position du joueur sur le podium s'il a gagné.
	 *
	 * @param j
	 * 		Le joueur à analiser.
	 */
	public void victoire(Joueur j){

		// Si le joueur a terminé.
		if(j.isVictoire()){
			int sup = 0;
			// On met la prochaine position du podium.
			for(Joueur j1 : joueurs){
				if(j1.getWin() > sup){
					sup = j1.getWin();
				}
			}
			j.setWin(sup+1);
		}
	}

	/**
	 * Méthode permettant de savoir si tous les joueurs ont fini la partie.
	 *
	 * @return
	 * 		True si la partie est terminée, false sinon.
	 */
	public boolean allVictoire(){
		for(Joueur j : joueurs){
			if(j.getWin() == 0){
				return false;
			}
		}
		return true;
	}

	/**
	 * Méthode permettant de déterminer si un pion mange les autres pions de la case.
	 *
	 * @param pion
	 * 		Le pion qui arrive sur la case.
	 */
	private void miam(Pion pion){
		Case c = grille.getCase(pion);
		ArrayList<Pion> pions = c.getPions();

		// S'il y a plusieurs pions sur la case et que ce n'est pas une case spéciale.
		if(pions.size() != 1 && !c.isSpe()){
			// On vire tous les pions qui ne sont pas au joueur.
			for(Pion p : pions){
				if(p.getNumJoueur() != pion.getNumJoueur())
					grille.movePion(p, p.getPos(), -1);
			}
		}
	}
}

