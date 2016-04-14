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
     * @return true si le caillou peut être poussé dans cette direction, false
     * sinon
     */
    public boolean poussable(Direction d) {
        return plateau.caseLibre(position.plus(d.getVector())) && plateau.valide((position.plus(d.getVector())));
    }
}
