/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Représente le monstre qui essaye de manger les jetons des joueurs.
 *
 * @author Corentin
 */
public class Monstre extends Pion implements NonTraversable {

    Direction direction;
    /**
     * C'est le constructeur ! Il prend en paramètre le plateau sur lequel on va positionner le monstre.
     * @param p 
     */
    public Monstre(Plateau p) {
        super(new Coordonnees(0, 0), p);
        this.direction = Direction.DROITE;
    }
    
    
}
