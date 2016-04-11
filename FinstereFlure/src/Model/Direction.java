/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Représente une direction sur la carte : vers le haut, vers le bas, vers la
 * gauche, ou vers la droite. Chaque direction est associée au vecteur unitaire
 * de cette direction, c'est-à-dire un vecteur représentant un déplacement d'une
 * case dans cette direction. Le vecteur est de type Coordonnees.
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
        this.vector = new Coordonnees(dx, dy);
    }

    public int getDx() {
        return this.vector.getX();
    }

    public int getDy() {
        return this.vector.getY();
    }

    public Coordonnees getVector() {
        return this.vector;
    }
    
    public Direction directionOpposee(){
        switch(this){
            case HAUT :
                return BAS;
            case BAS :
                return HAUT;
            case GAUCHE:
                return DROITE;
            case DROITE:
                return GAUCHE;     
        }
        return null;
    }

}
