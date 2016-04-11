/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
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
     * C'est le constructeur ! Il prend en paramètre le plateau sur lequel on va
     * positionner le monstre.
     *
     * @param p
     */
    public Monstre(Plateau p) {
        super(new Coordonnees(0, 0), p);
        this.direction = Direction.DROITE;
    }

    /**
     * Renvoie la liste des directions visibles par le zombie, c'est-à-dire
     * celles dans lesquelles il peut se déplacer.
     *
     * @return
     */
    public List directionsVisibles() {
        List l = asList(Direction.values()); //Transforme un tableau en liste.
        l.remove(this.direction.directionOpposee());
        return l;
    }

    /**
     * En prenant une direction, donne vers cette dernière la distance de la
     * nourriture la plus proche.
     *
     * @param d : la direction dans laquelle on doit chercher à manger.
     * @return la distance du repas. S'il n'y en a pas dans cette direction,
     * retourne -1.
     */
    public int trouverAManger(Direction d) {
        Coordonnees caseDep = this.position;
        int distance = 1;
        caseDep.plus(d.getVector());
        while (super.plateau.valide(caseDep) && super.plateau.caseLibre(caseDep)) { //On avance d'une case dans la direction, tant qu'elle est valide et ne contient rien.
            caseDep.plus(d.getVector());
            distance += 1;
        }
        if (super.plateau.getCase(caseDep) instanceof Jeton) { //Si la première case occupée est un pion (donc qui se mange, on renvoie sa distance)
            return distance;
        } else {
            return -1;
        }

    }

    /**
     * Le zombie regarde où est le jeton le plus proche, et met à jour sa
     * direction en fonction de ce dernier.
     */
    public void regarder() {
        List<Direction> l = this.directionsVisibles();
        int distance = this.trouverAManger(l.get(0)); //On initialise la distance avec la première direction.
        Direction dir = this.direction;
        for (int i = 1; i < 3; i++) {
            if ((this.trouverAManger(l.get(i)) <= distance) && this.trouverAManger(l.get(i)) > 0) { //Puis on regarde si les autres directions proposent de la nourriture plus près.
                distance = this.trouverAManger(l.get(i));
                if (this.trouverAManger(l.get(i)) == distance) { //Si on a déjà trouvé de la chair fraiche à cette distance, alors on gardela direction de départ
                    dir = this.direction;
                } else { //Sinon on prend la nouvelle direction.
                    dir = l.get(i);
                }

            }

        }
        this.direction = dir;//Une fois qu'on a trouvé la direction la plus intéressante, on MAJ la direction du zombie.
        //Si on n'a pas trouvé à manger, la direction du zombie ne bouge pas.

    }

}
