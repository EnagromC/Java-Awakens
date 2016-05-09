/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
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
    private ArrayList<Rectangle> flaques; //Contient les coordonnées des tâches de sang, chacune étant représentée par un rectangle.
    private Monstre monstre;

    ////////////////////////////////////////////////////////////////////////////
    // Constructeursa
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Plateau est le constructeur. Il place le zombie à sa case de départ, en
     * [0;0]
     */
    public Plateau() {
        this.plateau = new HashMap<>();
        this.flaques = new ArrayList<>();

        //Ajout des tâches de sang
        this.flaques.add(new Rectangle(8, 4, 3, 0));
        this.flaques.add(new Rectangle(2, 8, 1, 1));

        //Ajout du monstre
        this.monstre = new Monstre(this);
        Coordonnees cle = new Coordonnees(0, 0);
        this.plateau.put(cle, monstre);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
    public Pion getCase(Coordonnees c) {
        return this.plateau.get(c);
    }

    public Map<Coordonnees, Pion> getPlateau() {
        return this.plateau;
    }
    
    public Monstre getMonstre(){
        return this.monstre;
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
        for (Rectangle flaque : flaques) { //Pour chaque rectangle représentant une flaque, on reegarde s'il contient le point demandé.
            if (flaque.contains(position)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Détermine si des coordonnées sont valides, c'est-à-dire si elles sont
     * bien à l'intérieur du plateau.
     *
     * @param c Coordonnées à tester
     * @return true si les coordonnées sont dans le plateau, false sinon
     */
    public boolean valide(Coordonnees c) {
        int x = c.getXint();
        int y = c.getYint();
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
     * @param p le pion à supprimer
     * @param c coordonnées du pion à supprimer
     * @return true si le pion a bien été supprimé, false s'il n'y avait pas de
     * pion à ces coordonées.
     */
    public boolean removePion(Pion p, Coordonnees c) {
        return this.plateau.remove(c, p);
    }

    /**
     * Déplace un pion sur le plateau, si la case d'arrivée est vide, des
     * coordonnées depart aux coordonnées arrivee.
     *
     * @param p le pion à déplacer
     * @param depart coordonnées du pion à déplacer
     * @param arrivee coordonnées de la case où doit arriver le pion
     * @return true si tout c'est bien passé, false si le pion n'existait pas ou
     * la case d'arrivée est déjà occupée.
     */
    public boolean movePion(Pion p, Coordonnees depart, Coordonnees arrivee) {

        if (addPion(p, arrivee)) { //Si on a bien pu déplacer le pion sur la case d'arrivée
            removePion(p, depart);
            return true;
        }

        return false;
    }
    
    
}
