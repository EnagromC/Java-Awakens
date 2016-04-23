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

    private Direction direction;
    private Jeton dernierMange; //Le dernier jeton qui a été dévoré par le monstre

    /**
     * C'est le constructeur ! Il prend en paramètre le plateau sur lequel on va
     * positionner le monstre.
     *
     * @param p
     */
    public Monstre(Plateau p) {
        super(new Coordonnees(0, 0), p);
        this.direction = Direction.DROITE;
        this.dernierMange = null;
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

    /**
     * Indique si le monstre va sortir du plateau en faisant encore un pas dans
     * la même direction
     *
     * @return true s'il va sortir, false sinon
     */
    public boolean vaSortir() {
        return !(this.plateau.valide(this.position.plus(this.direction.getVector())));
    }

    /**
     * Détermine les coordonnées de la case dans laquelle doit se téléporter le
     * monstre. Attention, il faut auparavant s'assurer que le monstre est bien
     * sur une case en bordure du plateau.
     *
     * @return les coordonnées de la case où le monstre doit se téléporter.
     */
    public Coordonnees destinationTeleportation() {
        //Toutes les coordonnées des cases en bord de plateau.
        int[][] coordTeleporteurs = {{0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}, {0, 8}, {0, 9}, {0, 10}, {0, 11}, {1, 12}, {2, 13}, {3, 14}, {4, 15}, {5, 15}, {6, 15}, {7, 15}, {8, 15}, {9, 15}, {10, 15}, {10, 14}, {10, 13}, {10, 12}, {10, 11}, {10, 10}, {10, 9}, {10, 8}, {10, 7}, {10, 6}, {10, 5}, {10, 4}, {9, 3}, {8, 2}, {7, 1}, {6, 0}, {5, 0}, {4, 0}, {3, 0}, {2, 0}, {1, 0}};
        ArrayList<Coordonnees> teleporteurs = new ArrayList<>();
        for (int[] c : coordTeleporteurs) {
            teleporteurs.add(new Coordonnees(c[0], c[1]));
        }

        int i = teleporteurs.indexOf(this.position);

        //Dans la liste, les cases sont dans l'ordre. On remarque que la case d'arrivée de la téléportation est toujours 21 cases de bord plus loin que celle de départ : il suffit donc d'accéder aux coordonnées stockés 21 cases plus loin, modulo la taille de la liste.
        i += 21;
        i %= teleporteurs.size();

        return teleporteurs.get(i);
    }

    public void pousser(Pion p) {
        Coordonnees destination = p.getPosition().plus(this.direction.getVector());
        if (!plateau.valide(destination)) {
            //le truc sort du plateau
        } else if (!p.seDeplacer(direction)) { //On essaie de déplacer le pion. S'il y arrive on est OK, sinon c'est qu'il y a quelque chose derrière.
            Pion p2 = plateau.getCase(destination); //Le pion qui bloque le déplacement
            pousser(p2); //On pousse à son tour le pion qui bloquait
            p.seDeplacer(direction); //On peut alors déplacer ce pion
        }
    }

    public boolean seDeplacer(Direction d) {
        Coordonnees newCoord = this.position.plus(d.getVector()); //coordonnées de la case d'arrivée

        if (plateau.valide(newCoord)) {//On vérifie que cette case est bien sur le plateau
            if (plateau.caseLibre(newCoord)) { //Si elle est libre, on déplace le pion grâce à la méthode mère (qui gère les glissades...)
                super.seDeplacer(d);
                return true;

            } else if (plateau.getCase(newCoord) instanceof Jeton) { //Si la case contient un joueur
                //On se débrouille pour le manger
                this.seDeplacer(d);
                return true;

            } else if (plateau.getCase(newCoord) instanceof Caillou) { //Si la case contient un caillou
                pousser(plateau.getCase(newCoord));
                this.seDeplacer(d);
            }
        }

        return false; //Si le mouvement n'est pas possible
    }

    public void manger(Pion p) {
        plateau.removePion(p, p.getPosition());
        if (p instanceof Jeton) {
            this.dernierMange = (Jeton) p;
        }
    }

    public Jeton chasser() {
        regarder();
        if (this.vaSortir()) {
            /*
            Téléportation
            */
            plateau.removePion(this, this.position);
            //On positionne virtuellement le monstre une case avant la case d'arrivée, pour pouvoir utiliser la méthode seDeplacer qui prend en compte l'intéraction avec une éventuelle tâche de sang, ou un caillou ou joueur.
            this.position = this.destinationTeleportation().plus(this.direction.getVector().fois(-1));
        }
        
        /*
        Déplacement et éventuel repas
        */
        this.dernierMange = null;
        this.seDeplacer(this.direction);
        return this.dernierMange;

    }

}
