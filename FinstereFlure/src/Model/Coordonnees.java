/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Point;

/**
 * Représente les coordonnées d'une case du plateau de jeu, en fonction de son
 * numéro de ligne et de colonne
 *
 * @author Corentin
 */
public class Coordonnees extends Point{

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public Coordonnees(int x, int y) {
       super(x,y);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
    /**
     *
     * @return les coordonnées sous la forme d'un tableau de 2 cases.
     */
    public int[] getCoordonnees() {
        int[] coord = {this.x, this.y};
        return coord;
    }

    public int getXint() {
        return this.x;
    }

    public int getYint() {
        return this.y;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Permet de calculer la somme de 2 vecteurs de coordonnées.
     *
     * @param c les coordonnées à ajouter
     * @return la somme de 2 Coordonnées
     */
    public Coordonnees plus(Coordonnees c) {
        return new Coordonnees(this.x + c.x, this.y + c.y);
    }

    /**
     * Permet de multiplier un vecteur de coordonnées par un entier. On peut
     * ainsi faire la différence de 2 Coordonnées en multipliant par -1
     *
     * @param lambda le coefficient de multiplication
     * @return le produit des coordonnées par lambda
     */
    public Coordonnees fois(int lambda) {
        return new Coordonnees(this.x * lambda, this.y * lambda);
    }

    
}
