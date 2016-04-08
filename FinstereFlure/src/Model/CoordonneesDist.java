/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Les coordonnées d'une case, associées à la distance de cette case par rapport
 * à une case de départ. Cette classe est utilisée comme type de retour pour les
 * fonctions cherchant à déterminer quelles sont les classes accessibes d'un
 * certain point.
 *
 * @author Corentin
 */
public class CoordonneesDist {

    ////////////////////////////////////////////////////////////////////////////
    // Attributs
    ////////////////////////////////////////////////////////////////////////////
    private Coordonnees c;
    private int d;

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public CoordonneesDist(Coordonnees c, int d) {
        this.c = c;
        this.d = d;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
    public Coordonnees getCoordonnees() {
        return this.c;
    }

    public int getDistance() {
        return this.d;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean equals(Object o) {
        CoordonneesDist cd = (CoordonneesDist) o;
        return this.c == cd.c;
    }

    @Override
    public int hashCode() {
        return this.c.hashCode();
    }
}
