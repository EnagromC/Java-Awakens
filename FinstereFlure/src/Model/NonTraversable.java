/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Morgane
 */
public abstract class NonTraversable extends Pion {

    protected boolean traversable;

    public NonTraversable() {
        super();
    }

    public NonTraversable(Coordonnees c, Plateau p) {
        super(c, p);
    }

}
