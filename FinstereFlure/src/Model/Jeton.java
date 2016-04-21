/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.HashSet;

/**
 * Représente les jetons possédés par le joueur, et qu'il doit amener à la
 * sortie.
 *
 * @author Morgane
 */
public class Jeton extends Pion implements Traversable {

    ////////////////////////////////////////////////////////////////////////////
    // Attributs
    ////////////////////////////////////////////////////////////////////////////
    private boolean surPlateau;
    private int[] faces = new int[2];
    private boolean faceBlanche;
    private boolean enJeu;

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur du jeton. Le nombre de déplacements correspondant à chaque
     * face est passé en paramètre. Par défaut, la face visible est la blanche,
     * et le jeton n'est pas sur le plateau, mais il est en jeu (il est vivant).
     * De plus, un jeton est traversable (on peut passer sur une case dans
     * laquelle il se trouve).
     *
     * @param blanche : le nombre de déplacements sur la face blanche.
     * @param noire : le nombre de déplacements sur la face noire.
     */
    public Jeton(int blanche, int noire) {
        super();
        this.surPlateau = false;
        this.faces[0] = blanche;
        this.faces[1] = noire;
        this.enJeu = true;

        this.faceBlanche = true;

    }

    /* 
   Il faut faire les accesseurs, et éventuellement d'autres constructeurs.
     */
    /**
     * Retourne l'ensemble des coordonnées des cases accessibles depuis la case
     * de coordonnées c, en au maximum n déplacements, associée à leur distance
     * (nombre de déplacements nécessaires) depuis la case de départ. On
     * initialisera avec d = 0;
     *
     * @param c coordonnées de la case de départ
     * @param n nombre de déplacements maximum
     * @return l'ensemble des cases accessibles
     */
    public HashSet<Chemin> accessibles(Chemin c, int n) {
        HashSet<Chemin> res = new HashSet<>();
        if (n != 0) {
            HashSet<Chemin> newAcc = new HashSet<>(); //nouvelles cases accessibles
            for (Direction dir : Direction.values()) { //On essaie de se déplacer dans les 4 directions (haut, bas, gauche, droite)
                Coordonnees c2 = dir.getVector().plus(c.destination());
                if (plateau.valide(c2) && (plateau.caseLibre(c2) || (n != 1 && plateau.getCase(c2) instanceof Traversable))) {//Pour chaque nouvelle coordonnée générée on vérifie si elle est valide. S'il ne reste qu'un déplacement, la case d'arrivée doit aussi être vide. Si ce n'est pas le dernier mouvement, si elle est occupée ça doit être par une entité traversable.
                    Chemin ch = new Chemin(c);
                    ch.incCout();
                    ch.add(c2);
                    while(plateau.estUneFlaque(ch.destination())&& positionValide(ch.destination().plus(dir.getVector()))){
                        ch.add(ch.destination().plus(dir.getVector()));
                    }
                    
                    if (res.add(ch)) { //On ajoute la case dans l'ensemble des accessibles et on vérifie si elle n'y était pas déjà
                        newAcc.add(ch); //Si elle n'y était pas déjà, on l'ajoute à la liste des nouveaux accessibles de ce niveau
                    }
                    
                }
            }

            for (Chemin ch : newAcc) { //Pour chaque nouvel accessible, on génére les nouveaux états accessibles. Puisqu'on parcourt en largeur, ils sont forcément plus éloignés de l'état de départ que les précédents.
                res.addAll(accessibles(ch, n - 1));
            }

        }
        return res;
    }

    private boolean positionValide(Coordonnees plus) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
