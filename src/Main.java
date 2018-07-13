/**
 * Main
 *
 * @author Alexandre l'Heritier
 * @version 2.0
 */
public class Main {
	public static void main(String[] args){
		System.out.println("-------------------------\nJGalop\nPar Alexandre l'Heritier\n-------------------------");
		Jeu test = new Jeu();
		test.play();
		return;
	}
}

//region
/*
Changelog :

v2.0 (build 180713.1) :
- Amélioration des inscriptions console.
- Ajout de la demande du nombre de joueur.
- Ajout de la demande des noms des joueurs.
- Ajout d'inscriptions dans le dessin du plateau (méthode drawInfos() qui
  est une adaptation et une amélioration de la méthode drawLigneInfo() du
  projet Ile Interdite).


v1.1 (build 180302.1) :
- Ajout de l'affichage de la sélection du pion.
- Correction définitive de la position des chiffres sur les pions.

(build 180303.1) :
- Correction du positionnement les positions podium.
- Conversion des descriptions en convention javadoc.

(build 180718.1) :
- Ajout des protections dans les entrées de l'utilisateur.
- Correction au niveau les scanner int.


v1.0 (build 180630.2) :
Version Stable
- Agrandissement du pion s'il est seul sur une case.
- Correction de la position des numéros des pions.
- Ajout d'une condition pour supporter moins de 4 joueurs.
- Ajout de l'affichage des positions podium.
- Message de fin.
- Correction des méthodes victoire().
- Projet renommé en JGalop

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