/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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
    }

    /* 
   Il faut faire les accesseurs, et éventuellement d'autres constructeurs.
     */
}
