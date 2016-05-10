/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import InterfaceGraphique.Vue;
import java.util.ArrayList;

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

        this.manche = 1;
        this.tour = 1;
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

    public boolean getFace() {
        return this.faceBlanche;
    }

    public Vue getVue() {
        return this.vue;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Indique si le tour est fini ou pas, c'est-à-dire si tous les joueurs ont
     * joué tous leurs pions et que c'est au monstre d'avancer.
     *
     * @return true si le tour est fini, false sinon.
     */
    public boolean tourFini() {
        for (int i = 0; i < 4; i++) {
            //Si un des pions est encore sur la face pour bouger
            if (joueur1.getPions()[i].estSurFaceBlanche() == this.faceBlanche && joueur2.getPions()[i].estSurFaceBlanche() == this.faceBlanche) {
                return false;
            }
        }
        return true;
    }

    /**
     * Effectue les actions de fin de tour, c'est-à-dire faire avancer le
     * monstre, incrémenter le compteur de tour, et changer le côté des pions
     * qu'on considère.
     */
    public void actionFinTour() {
        this.tour++;
        this.faceBlanche = !this.faceBlanche;
        Carte c = paquet.piocherCarte();
        vue.updatePaquet(c);
        ArrayList<Jeton> victimes = this.plateau.getMonstre().chasser(c);
        if (this.manche == 2) {
            for (Jeton j : victimes) {
                j.setVivant(false);
            }
        }
        vue.updatePlateau();

        if (this.paquet.taille() == 1) {
            if (this.manche == 1) {
                this.paquet = new Paquet();
                this.manche = 2;
                joueur1.jouer();
            } else {
                this.gameOver();
            }
        }
        vue.updatePaquet(c);
        vue.updateInfosPartie();
    }

    /**
     * Actions à effectuer quand la partie est terminée
     */
    public void gameOver() {
        /*
        Ajouter la partie à la BDD
         */

        vue.gameOver();
    }

    /**
     * Retourne le joueur vainqueur de la partie. On suppose que cette méthode
     * est appelée quand on sait que la partie ets terminée, sinon elle renvoie
     * n'importe quoi.
     *
     * Le vainqueur est celui qui a sorti le plus de pions du labyrinthe
     * (forcément si un joueur gagne en en sortant 3, il en a sorti plus)
     *
     * En cas d'égalité, retourne null
     *
     * @return le joueur vainqueur.
     */
    public Joueur vainqueur() {
        int score1 = 0;
        int score2 = 0;
        for (int i = 0; i < 4; i++) {
            if (joueur1.getPions()[i].getVivant() && !joueur1.getPions()[i].getEnJeu()) {
                score1++;
            }
            if (joueur2.getPions()[i].getVivant() && !joueur2.getPions()[i].getEnJeu()) {
                score2++;
            }
        }
        if (score1 == score2) {
            return null;
        } else if (score1 > score2) {
            return joueur1;
        } else {
            return joueur2;
        }
    }
}
