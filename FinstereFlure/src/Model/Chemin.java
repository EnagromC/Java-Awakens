/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Corentin
 */
public class Chemin extends ArrayList<Coordonnees> {

    private int cout;

    public Chemin() {
        super();
        this.cout = 0;
    }
    
    public Chemin(Chemin ch){
        super();
        for (Coordonnees c :ch){
            this.add(c);
        }
        this.cout = ch.cout;
    }

    public int getCout() {
        return this.cout;
    }

    public void incCout() {
        this.cout++;
    }

    public Coordonnees destination() {
        return this.get(this.size() - 1);
    }

    /**
     * Détermine la direction du personnage à la fin du chemin, c'est-à-dire la
     * direction qu'il a du prendre pour passer de l'avant dernière case à la
     * dernière.
     *
     * On suppose que le chemin a toujours une longueur d'au moins 2 quand on
     * appelle cette méthode.
     *
     * @return un vecteur de coordonnées correspondant à la direction du jeton
     */
    public Coordonnees directionVector() {
        return this.destination().plus(this.get(this.size() - 2).fois(-1));
    }

    //On considère que 2 chemins sont égaux s'ils ont le même départ et la même arrivée.
    @Override
    public boolean equals(Object o) {
        Chemin ch = (Chemin) o;
        return this.get(0) == ch.get(0) && this.destination() == ch.destination();
    }

    @Override
    public int hashCode() {
        return this.get(0).hashCode() * 17 + this.destination().hashCode() * 29;
    }

}
