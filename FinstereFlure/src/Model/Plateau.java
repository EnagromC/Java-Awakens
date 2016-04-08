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
 * Représente le plateau de jeu dans lequel évoluent les pions. Le plateau est
 * représenté sous la forme d'une Map, dont les clés sont les coordonnées des
 * cases et les valeurs, les pions positionnés sur cette case. Ainsi toutes les
 * cases ne figurant pas dans la Map sont des cases du plateau inoccupées. Les
 * cases où se trouvent une tâche de sang sont référencées dans la liste
 * "flaques".
 *
 * @author Corentin
 */
public class Plateau {

    ////////////////////////////////////////////////////////////////////////////
    // Attributs
    ////////////////////////////////////////////////////////////////////////////
    private Map<Coordonnees, Pion> plateau; //Contient les cases occupées du plateau.
    private ArrayList<Coordonnees> flaques; //Contient les coordonnées des cases sur lesquelles il y a une flaque de sang.

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Plateau est le constructeur. Il place le zombie à sa case de départ, en
     * [0;0]
     */
    public Plateau() {
        this.plateau = new HashMap<>();
        this.flaques = new ArrayList<>();
        //Ajouter les flaques dans la liste.
        Coordonnees cle = new Coordonnees(0, 0);
        this.plateau.put(cle, new Monstre(this));
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    /**
     * estUneFlaque est une méthode qui regarde si la case en paramètre est une
     * flaque ou pas.
     *
     * @param position case que l'on veut vérifier
     * @return true si la case est une flaque
     */
    public boolean estUneFlaque(Coordonnees position) {
        return this.flaques.contains(position);
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

                    if (res.add(new CoordonneesDist(c2, d))) { //On ajoute la case dans l'ensemble des accessibles et on vérifie si elle n'y était pas déjà
                        newAcc.add(c2); //Si elle n'y était pas déjà, on l'ajoute à la liste des nouveaux accessibles de ce niveau
                    }
                }
            }

            for (Coordonnees c2 : newAcc) { //Pour chaque nouvel accessible, on génére les nouveaux états accessibles. Puisqu'on parcourt en largeur, ils sont forcément plus éloignés de l'état de départ que les précédents.
                res.addAll(accessibles(c2, n - 1, d + 1));
            }

        }
        return res;
    }

    /**
     * Permet d'ajouter un pion sur le plateau.
     *
     * @param p le pion à ajouter
     * @param c les coordonnées de ce pion
     * @return true si le pion a bien été ajouté, false s'il ne l'a pas été car
     * un pion occupait déjà la case.
     */
    public boolean addPion(Pion p, Coordonnees c) {
        Pion ancien = this.plateau.putIfAbsent(c, p);
        return ancien == null;
    }

    /**
     * Supprime du plateau le pion situé aux coordonnées donénes
     *
     * @param c coordonnées du pion à supprimer
     * @return true si le pion a bien été supprimé, false s'il n'y avait pas de
     * pion à ces coordonées.
     */
    public boolean removePion(Coordonnees c) {
        Pion ancien = this.plateau.remove(c);
        return ancien != null;
    }

    /**
     * Déplace un pion sur le plateau, si il existe et que la case d'arrivée est
     * vide, des coordonnées depart aux coordonnées arrivee.
     *
     * @param depart coordonnées du pion à déplacer
     * @param arrivee coordonnées de la case où doit arriver le pion
     * @return true si tout c'est bien passé, false si le pion n'existait pas ou
     * la case d'arrivée est déjà occupée.
     */
    public boolean movePion(Coordonnees depart, Coordonnees arrivee) {
        Pion p = this.plateau.get(depart);
        if (p != null) { //S'il y a bien un pion sur la case départ
            if (addPion(p, arrivee)) { //Si on a bien pu déplacer le pion sur la case d'arrivée
                removePion(depart);
                return true;
            }
        }
        return false;
    }
}
