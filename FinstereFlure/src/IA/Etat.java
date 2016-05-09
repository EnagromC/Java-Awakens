/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IA;

import Model.Caillou;
import Model.Carte;
import Model.Coordonnees;
import Model.Jeton;
import Model.Paquet;
import Model.Partie;
import Model.Pion;
import Model.Plateau;
import java.util.ArrayList;

/**
 *
 * @author Morgane
 */
public class Etat {

    ////////////////////////////////////////////////////////////////////////////
    // Attributs
    ////////////////////////////////////////////////////////////////////////////
    private Jeton[] humain; //On s'en fiche de son nom, on veut juste ses jetons.
    private Jeton[] ordi;
    private Plateau plateau;
    private short manche; //On essaye de gratter un peu de place en mémoire comme on peut
    private short tour;
    private Paquet paquet;
    private short kikijoue;
    private float proba;
    public static final int HUMAIN = 0;
    public static final int ORDI = 1;
    public static final int CHANCE = 2;

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////  
    public Etat(){
        
    }
    
    public Etat(Jeton[] humain, Jeton[] ordi, Plateau plateau, int manche, int tour, Paquet paquet, int kikijoue) {
        this.humain = humain;
        this.ordi = ordi;
        this.plateau = plateau;
        this.manche = (short) manche;
        this.tour = (short) tour;
        this.paquet = paquet;
        this.kikijoue = (short) kikijoue;
    }

    public Etat(Partie p) {
        this.manche = (short) p.getManche();
        this.tour = (short) p.getTour();

        this.paquet = (Paquet) p.getPaquet().clone();

        Plateau plat = new Plateau();

        //Copie du monstre
        Coordonnees coordMonstre = p.getPlateau().getMonstre().getPosition();
        plat.getMonstre().seDeplacer(coordMonstre);

        this.plateau = plat;
        //Copie des cailloux
        for (Pion pion : plat.getPlateau().values()) {
            if (pion instanceof Caillou) {
                Caillou caillou = new Caillou(pion.getPosition(), plat); //Le caillou est ajouté au plateau
            }
        }
        
        //Copie des jetons
        for(int i = 0;i<4;i++){
            this.humain[i] = (Jeton) p.getJoueur1().getPions()[i].clone();
            this.humain[i].setPlateau(plat);
            
            this.ordi[i] = (Jeton) p.getJoueur2().getPions()[i].clone();
            this.ordi[i].setPlateau(plat);
            
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
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

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
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

    /**
     * Indique à qui c'est le tour de jouer
     *
     * @return le nombre associé au joueur dont c'est le tour
     */
    public int kikijoue() {
        return kikijoue;
    }

    /**
     * Renvoie la liste des successeurs d'un état, c'est-à-dire tous les états
     * du jeu atteignables après cet état en appliquant les règles.
     *
     * La liste est triée selon une heuristique basique (distance à la sortie)
     * afin de maximiser le nombre de coupes alpha/bêta.
     *
     * @return la liste des états successeurs de l'état actuel
     */
    public ArrayList<Etat> successeurs() {
        ArrayList<Etat> successeurs = new ArrayList<>();

        if (this.kikijoue == CHANCE) {
            for (Carte c : paquet.getComposition().keySet()) {

            }
        }

        return new ArrayList<>();
    }

    /**
     * Indique la probabilité d'arriver dans cet état sachant l'état précédent.
     *
     * @return la probabilité
     */
    public float proba() {
        return this.proba;
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

    @Override
    public Object clone() {
        Etat s = new Etat();
         s.manche = this.manche;
        s.tour = this.tour;

        s.paquet = (Paquet) this.paquet.clone();

        Plateau plat = new Plateau();

        //Copie du monstre
        Coordonnees coordMonstre = this.getPlateau().getMonstre().getPosition();
        plat.getMonstre().seDeplacer(coordMonstre);

        //Copie des cailloux
        for (Pion pion : plat.getPlateau().values()) {
            if (pion instanceof Caillou) {
                Caillou caillou = new Caillou(pion.getPosition(), plat); //Le caillou est ajouté au plateau
            }
        }
        this.plateau = plat;
        
        //Copie des jetons
        for(int i = 0;i<4;i++){
            s.humain[i] = (Jeton) this.humain[i].clone();
            s.humain[i].setPlateau(plat);
            
            s.ordi[i] = (Jeton) this.ordi[i].clone();
            s.ordi[i].setPlateau(plat); 
        }
        
        
        s.kikijoue = this.kikijoue;
        s.proba = this.proba;
        
        return s;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes privées
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Indique combien de pions sont sortis (c'est-à-dire qui sont arrivés à la
     * sortie du labbyrinthe) dans un tableau de Jetons
     *
     * @param joueur le tableau à tester
     * @return le nombre qui sont sortis
     */
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
