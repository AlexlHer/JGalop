// -----------------------------
// JGalop
// Par : Alexandre l'Heritier
// -----------------------------
// Classe Main
// -----------------------------

public class Main {
	public static void main(String[] args){
		Jeu test = new Jeu();
		test.play();
		return;
	}
}

//region
/*
Changelog :

v1.0 (build 180630.2) :
Version Stable
- Agrandissement du pion s'il est seul sur une case.
- Correction de la position des numéros des pions.
- Ajout d'une condition pour supporter moins de 4 joueurs.
- Ajout de l'affichage des positions podium.
- Message de fin.
- Correction des méthodes victoire().

Versions Beta :
v0.6 (build 180630.1) :
- Correction d'un bug dans movePion de la partie joueur : nouvelle pos.
- Correction du positionnement des pions dans la réserve.

v0.5 (build 180629.1) :
- Ajout des chiffres sur les pions.
- Ajout de la méthode miam() pour manger les autres pions quand c'est necessaire.
- Correction du movePion() négatif.
- Correction d'un bug dans le déplacement du pion dans la partie du joueur.

v0.4 (build 180628.1) :
- Correction dans Grille : movePion : modification des pos des pions.
- Ajout des couleurs des pions dans placePion.
- Correction de tour dans Jeu.
- Ajout de la boucle du terrain.

v0.3 (build 180622.1) :
- Ajouts de quelques méthodes dans joueur.

v0.2 (build 180621.1) :
- Ajout des couleurs.
- Ajout des pions.
- Ajout du ratio d'agrandissement dans l'affichage des pions.

v0.1 (build 180620.1) :
- Version initiale.
- Création du scelette avec méthodes du projet POGL.
- Ajout de carrés dans dessin pour créer le plateau.

*/
//endregion