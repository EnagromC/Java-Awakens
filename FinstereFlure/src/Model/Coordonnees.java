/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Corentin
 */
public class Coordonnees {

    private int x;
    private int y;

    public Coordonnees(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] getCoordonnees() {
        int[] coord = {this.x, this.y};
        return coord;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    /**
     * Permet de calculer la somme de 2 vecteurs de coordonnées.
     * @param c les coordonnées à ajouter
     * @return la somme de 2 Coordonnées
     */
    public Coordonnees plus(Coordonnees c) {
        return new Coordonnees(this.x + c.x, this.y + c.y);
    }

    /**
     * Permet de multiplier un vecteur de coordonnées par un entier. On peut ainsi faire la différence de 2 Coordonnées en multipliant par -1
     * @param lambda le coefficient de multiplication
     * @return le produit des coordonnées par lambda
     */
    public Coordonnees fois(int lambda) {
        return new Coordonnees(this.x * lambda, this.y * lambda);
    }

    /**
     * Calcule la distance euclidienne entre 2 coordonnées
     * @param c 
     * @return 
     */
    public double distanceEuclidienne(Coordonnees c) {
        return Math.sqrt(Math.pow(this.x - c.x, 2) + Math.pow(this.y - c.y, 2));
    }

    /**
     * Calcule la distance de Manhattan entre 2 coordonnées
     * @param c
     * @return 
     */
    public int distanceManhattan(Coordonnees c) {
        return Math.abs(this.x - c.x) + Math.abs(this.y - c.y);
    }

    
    @Override
    public boolean equals(Object o) {
        Coordonnees c = (Coordonnees) o;
        return this.x == c.x && this.y == c.y;
    }

    @Override
    public int hashCode() {
        return this.x * 3 + this.y * 7;
    }
}
