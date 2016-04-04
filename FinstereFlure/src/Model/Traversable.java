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
public abstract class Traversable extends Pion {

    protected boolean traversable;

    public Traversable() {
        super();
    }

    public Traversable(Coordonnees c, Plateau p) {
        super(c, p);
    }
}
