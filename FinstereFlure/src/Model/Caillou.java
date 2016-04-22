/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Représente un caillou bloquant une case du labyrinthe
 *
 * @author Corentin
 */
public class Caillou extends Pion implements NonTraversable {

    public Caillou(Coordonnees c, Plateau p) {
        super(c, p);
    }

    /**
     * Un caillou est poussable dans une certaine direction si la case derrière
     * lui est vide.
     *
     * @param d direction dans laquelle on veut pousser le caillou
     * @return true si le caillou peut être poussé dans cette direction, false
     * sinon
     */
    public boolean poussable(Direction d) {
        return plateau.caseLibre(position.plus(d.getVector())) && plateau.valide((position.plus(d.getVector())));
    }

    /**
     * Permet de déplacer un caillou d'une case dans une direction. Le
     * déplacement n'est effectué que s'il est valide, c'est-à-dire si la case
     * est vide et appartient au plateau.
     *
     * @param d la direction où pousser le caillou
     * @return true si le déplacement a été effectué, false s'il était invalide.
     */
    public boolean seDeplacer(Direction d) {
        Coordonnees newCoord = this.position.plus(d.getVector());
        if (plateau.valide(newCoord) && plateau.caseLibre(newCoord)) {
            super.seDeplacer(d);
            return true;
        }
        return false;
    }
}
