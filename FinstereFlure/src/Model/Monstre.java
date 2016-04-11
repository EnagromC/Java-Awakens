/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static java.util.Arrays.asList;
import java.util.List;

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
    
    
    public List directionsVisibles(){
        List l = asList(Direction.values());
        l.remove(this.direction.directionOpposee());
        return l;
    }
}
