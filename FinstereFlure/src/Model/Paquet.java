/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author Morgane
 */
public class Paquet {

    ////////////////////////////////////////////////////////////////////////////
    // Attributs
    ////////////////////////////////////////////////////////////////////////////
    LinkedList<Carte> paquet = new LinkedList<>();

    ////////////////////////////////////////////////////////////////////////////
    // Attributs
    ////////////////////////////////////////////////////////////////////////////
    /**
     * C'est le constructeur ! On ajoute chacune des cartes dans le paquet, puis
     * on le mélange.
     */
    public Paquet() {
        this.paquet.add(new Carte(5, false));
        this.paquet.add(new Carte(7, false));
        this.paquet.add(new Carte(7, false));
        this.paquet.add(new Carte(8, false));
        this.paquet.add(new Carte(8, false));
        this.paquet.add(new Carte(10, false));
        this.paquet.add(new Carte(1, true));
        this.paquet.add(new Carte(2, true));
        Collections.shuffle(this.paquet); //On mélange le paquet;
        while (!this.paquet.get(0).getProie()) { //Tant que la première carte est une carte "proie", on remélange.
            Collections.shuffle(this.paquet);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    /**
     * On pioche une carte, et on la supprime du paquet. Si le paquet est vide,
     * renvoie null
     *
     * @return la carte piochée.
     */
    public Carte piocherCarte() {
        if (!this.vide()) {
            Carte c = this.paquet.get(0);
            this.paquet.remove(0);
            return c;
        } else {
            return null;
        }
    }

    /**
     * Renvoie la taille du paquet, c'est-à-dire le nombre de carte qu'il
     * contient encore
     *
     * @return la taille du paquet
     */
    public int taille() {
        return this.paquet.size();
    }

    /**
     * Permet de retirer une carte du paquet
     *
     * @param c la carte à retirer
     */
    public void remove(Carte c) {
        this.paquet.remove(c);
    }

    /**
     * Indique si le paquet est vide, c'est-à-dire qu'il ne contient plus de
     * carte.
     *
     * @return true si le paquet est vide, false sinon
     */
    public boolean vide() {
        return this.paquet.isEmpty();
    }

    /**
     * Indique la composition du paquet, c'est-à-dire le nombre de fois
     * qu'apparait chaque carte dans le paquet. Le résultat est renvoyé sous la
     * forme d'une Map avec la Carte en clé et son nombre d'occurrences en
     * valeur. Les à n'apparaissent pas.
     *
     * @return une HashMap contenant toutes les cartes du paquet assocée à leur
     * nombre d'occurrence.
     */
    public HashMap<Carte, Integer> getComposition() {
        HashMap<Carte, Integer> hm = new HashMap<>();
        for (Carte c : this.paquet) {
            if (hm.containsKey(c)) {
                hm.replace(c, hm.get(c) + 1);
            } else {
                hm.put(c, 1);
            }
        }
        return hm;
    }

    /**
     * Permet de cloner un paquet. On clone chaque Carte une par une et on
     * l'ajoute dans une nouveau Paquet.
     *
     * @return un clone de ce paquet. Les cartes contenues ont également des
     * références différentes.
     */
    @Override
    public Object clone() {
        Paquet p = new Paquet();
        LinkedList<Carte> cartes = new LinkedList<>();
        for (Carte c : this.paquet) {
            cartes.add((Carte) c.clone());
        }

        return p;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Paquet) {
            Paquet p = (Paquet) o;
            return this.paquet.equals(p.paquet);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.paquet);
        return hash;
    }

}
