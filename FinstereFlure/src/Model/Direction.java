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
public enum Direction {
    HAUT(-1, 0),
    BAS(1, 0),
    GAUCHE(0, -1),
    DROITE(0, 1);

    private Coordonnees vector;

    Direction(int dx, int dy) {
        this.vector = new Coordonnees(dx,dy);
    }

    public int getDx() {
        return this.vector.getX();
    }

    public int getDy() {
        return this.vector.getY();
    }
    
    public Coordonnees getVector(){
        return this.vector;
    }
}
