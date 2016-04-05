/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author Corentin
 */
public class Plateau {

    Map<Coordonnees, Pion> plateau = new HashMap<>(); //Contient les cases occupées du plateau.
    ArrayList<Coordonnees> flaques = new ArrayList<>(); //Contient les cases sur lesquelles il y a une flaque de sang.

    /**
     * Plateau est le constructeur. Il place le zombie à sa case de départ, en
     * [0;0]
     */
    public Plateau() {
        Coordonnees cle = new Coordonnees(0, 0);
        this.plateau.put(cle, new Monstre(this));
    }

    /**
     * estUneFlaque est une méthode qui regarde si la case en paramètre est une
     * flaque ou pas.
     *
     * @param position case que l'on veut vérifier
     * @return true si la case est une flaque
     */
    public boolean estUneFlaque(Coordonnees position) {
        boolean flaque = this.flaques.contains(position);
        return flaque;
    }

    /**
     * Détermine si des coordonnées sont valides, c'est-à-dire si elles sont
     * bien à l'intérieur du plateau.
     *
     * @param c Coordonnées à tester
     * @return true si les coordonnées sont dans le plateau, false sinon
     */
    public boolean valide(Coordonnees c) {
        int x = c.getX();
        int y = c.getY();
        boolean coinHD = (x == 0 && y > 11) || (x == 1 && y > 12) || (x == 2 && y > 13) || (x == 3 && y > 14);
        boolean coinBG = (y == 0 && x > 6) || (y == 1 && x > 7) || (y == 2 && x > 8) || (y == 3 && x > 9);

        return x >= 0 && y >= 0 && x < 11 && y < 17 && !coinHD && !coinBG;
    }

    /**
     * Indique si la case de coordonnées c est libre ou si un pion y séjourne.
     *
     * @param c coordonnées à tester
     * @return true si la case est libre, false si quelqu'un y est.
     */
    public boolean caseLibre(Coordonnees c) {
        return !this.plateau.containsKey(c);
    }

    /**
     * Retourne l'ensemble des coordonnées des cases accessibles depuis la case
     * de coordonnées c, en au maximum n déplacements, associée à leur distance
     * (nombre de déplacements nécessaires) depuis la case de départ. On
     * initialisera avec d = 0;
     *
     * @param c coordonnées de la case de départ
     * @param n nombre de déplacements maximum
     * @param d la distance d'une case accessible à la case de départ
     * @return l'ensemble des cases accessibles
     */
    public HashSet<CoordonneesDist> accessibles(Coordonnees c, int n, int d) {
        HashSet<CoordonneesDist> res = new HashSet<>();
        if (n != 0) {
            HashSet<Coordonnees> newAcc = new HashSet<>(); //nouvelles cases accessibles
            for (Direction dir : Direction.values()) { //On essaie de se déplacer dans les 4 directions (haut, bas, gauche, droite)
                Coordonnees c2 = dir.getVector().plus(c);
                if (valide(c2) && (caseLibre(c2) || (n != 1 && plateau.get(c2) instanceof Traversable))) {//Pour chaque nouvelle coordonnée générée on vérifie si elle est valide. S'il ne reste qu'un déplacement, la case d'arrivée doit aussi être vide. Si ce n'est pas le dernier mouvement, si elle est occupée ça doit être par une entité traversable.
                    
                    if(res.add(new CoordonneesDist(c2,d))){ //On ajoute la case dans l'ensemble des accessibles et on vérifie si elle n'y était pas déjà
                        newAcc.add(c2); //Si elle n'y était pas déjà, on l'ajoute à la liste des nouveaux accessibles de ce niveau
                    }
                }
            }
            
            for(Coordonnees c2 : newAcc){ //Pour chaque nouvel accessible, on génére les nouveaux états accessibles. Puisqu'on parcourt en largeur, ils sont forcément plus éloignés de l'état de départ que les précédents.
                res.addAll(accessibles(c2,n-1,d+1));
            }

        }
        return res;
    }
}

