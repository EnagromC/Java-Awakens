/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import InterfaceGraphique.Vue;
import java.util.HashSet;

/**
 * Représente l'état et le déroulement d'une partie de Finstere Flure.
 *
 * @author Corentin
 */
public class Partie {

    ////////////////////////////////////////////////////////////////////////////
    // Attributs
    ////////////////////////////////////////////////////////////////////////////
    private Vue vue;
    private Joueur joueur1;
    private Joueur joueur2;
    private Plateau plateau;
    private boolean faceBlanche;
    private int manche;
    private int tour;
    private Paquet paquet;

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public Partie(Vue v, Joueur j1, Joueur j2) {
        this.vue = v;
        joueur1 = j1;
        joueur2 = j2;
        plateau = new Plateau();
        joueur1.setPartie(this);
        joueur2.setPartie(this);
        joueur1.setPlateau(plateau);
        joueur2.setPlateau(plateau);
        this.joueur1.setNumero(1);
        this.joueur2.setNumero(2);

        this.manche = 0;
        this.tour = 0;
        this.paquet = new Paquet();
        faceBlanche = true;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs 
    ////////////////////////////////////////////////////////////////////////////
    public Joueur getJoueur1() {
        return this.joueur1;
    }

    public Joueur getJoueur2() {
        return this.joueur2;
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

    public Vue getVue() {
        return this.vue;
    }
}
