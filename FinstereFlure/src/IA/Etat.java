/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IA;

import Model.Jeton;
import Model.Paquet;
import Model.Plateau;
import java.util.ArrayList;

/**
 *
 * @author Morgane
 */
public class Etat {

    private Jeton[] humain; //On s'en fiche de son nom, on veut juste ses jetons.
    private Jeton[] ordi;
    private Plateau plateau;
    private int manche;
    private int tour;
    private Paquet paquet;
    public static final int HUMAIN = 0;
    public static final int ORDI = 1;
    public static final int CHANCE = 2;

    public Jeton[] getHumain() {
        return this.humain;
    }

    public Jeton[] getOrdi() {
        return this.ordi;
    }

    public Plateau getPlateau() {
        return this.plateau;
    }

    public int getManche() {
        return this.manche;
    }

    public int getTour() {
        return this.tour;
    }

    public Paquet getPaquet() {
        return this.paquet;
    }

    /**
     * Regarde si le jeu est fini, donc si un joueur a trois pions sortis, ou si
     * on en est au tour 15 (donc plus loin que le 14).
     *
     * @return
     */
    public boolean fini() {
        int comptHumain = nbPionsSortis(humain);
        int comptOrdi = nbPionsSortis(ordi);

        return this.tour == 15 || comptHumain == 3 || comptOrdi == 3;

    }

    public int kikijoue() {
        return 0;
    }

    public ArrayList<Etat> successeurs() {
        return new ArrayList<>();
    }

    public float proba() {
        return 1;
    }

    /**
     * Indique kikagagné : pour le savoir, on regarde qui a le plus de jetons
     * sortis.
     *
     * @return un entier selon le gagnant : -1 si c'est l'humain, 1 l'IA, 0 si
     * match nul.
     */
    public int kikawon() {
        int comptHumain = nbPionsSortis(humain);
        int comptOrdi = nbPionsSortis(ordi);

        if (comptHumain < comptOrdi) { //L'humain a gagné.
            return 1;
        }
        if (comptHumain > comptOrdi) { // L'ordi a gagné.
            return -1;
        }
        return 0; //Match nul.

    }

    private int nbPionsSortis(Jeton[] joueur) {
        int compteur = 0;
        for (Jeton j : joueur) {
            if (!j.getSurPlateau() && !j.getEnJeu() && j.getVivant()) {
                compteur++;
            }
        }
        return compteur;
    }

}
