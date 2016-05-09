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
import Model.Direction;
import Model.Monstre;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

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
    public Etat() {

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
        plateau.getPlateau().clear();

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
        for (int i = 0; i < 4; i++) {
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

        /*
        Si on est sur un noeud chance
         */
        if (this.kikijoue == CHANCE) {

            for (Carte c : paquet.getComposition().keySet()) {
                Etat s = (Etat) this.clone();
                s.tour++;
                //La proba d'être dans ce cas est le nombre de carte de cette sorte / le nombre de carte restant
                s.proba = paquet.getComposition().get(c) / paquet.taille();
                Monstre m = s.plateau.getMonstre();

                //On chasse selon la carte, puis on tue ou non les victimes selon le tour.
                ArrayList<Jeton> victimes = m.chasser(c);
                for (Jeton j : victimes) {
                    if (manche == 2) {
                        j.setVivant(false);
                    }
                }

            }

            /*
            Si on est sur un noeud ORDI ou HUMAIN
             */
        } else {
            //On suppose que l'ordre de déplacement des pions n'a pas réellement d'importance. On déplace donc le premier jeton déplaçable.
            Jeton[] joueur = this.kikijoue == HUMAIN ? this.humain : this.ordi;

            boolean trouve = false;
            int i = 0;
            int numJeton = -1;
            while (!trouve && i < 4) {
                //Si le pion est encore en jeu et qu'il est sur la bonne face (blanche si congru tour congru à 1, noire sinon)
                if (joueur[i].getEnJeu() && ((tour % 2 == 1) == joueur[i].estSurFaceBlanche())) {
                    trouve = true;
                    numJeton = i;
                    successeurs = deplacementsJoueur(numJeton);
                }
                i++;
            }

            //Si aucun mouvement n'est possible, on change quand même kikijoue
            if (successeurs.isEmpty()) {
                Etat s = (Etat) this.clone();
                successeurs.add(s);
            }

            for (Etat s : successeurs) {
                if (numJeton != -1) { //Si on a trouvé un jeton
                    Jeton j = this.kikijoue == HUMAIN ? s.humain[numJeton] : s.ordi[numJeton];
                    j.retourner();
                }

                if (kikijoue == ORDI && finTour()) {
                    s.kikijoue = CHANCE;
                } else if (kikijoue == ORDI) {
                    s.kikijoue = HUMAIN;
                } else if (kikijoue == HUMAIN) {
                    s.kikijoue = ORDI;
                }
            }

        }

        return successeurs;
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
        for (int i = 0; i < 4; i++) {
            s.humain[i] = (Jeton) this.humain[i].clone();
            s.humain[i].setPlateau(plat);

            s.ordi[i] = (Jeton) this.ordi[i].clone();
            s.ordi[i].setPlateau(plat);
        }

        s.kikijoue = this.kikijoue;
        s.proba = this.proba;

        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Etat) {
            Etat s = (Etat) o;
            return Arrays.equals(this.humain, s.humain) && Arrays.equals(this.ordi, s.ordi) && s.kikijoue == this.kikijoue && this.manche == s.manche && this.paquet.equals(s.paquet) && this.plateau.equals(s.plateau) && this.proba == s.proba && this.tour == s.tour;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Arrays.deepHashCode(this.humain);
        hash = 11 * hash + Arrays.deepHashCode(this.ordi);
        hash = 11 * hash + Objects.hashCode(this.plateau);
        hash = 11 * hash + this.manche;
        hash = 11 * hash + this.tour;
        hash = 11 * hash + Objects.hashCode(this.paquet);
        hash = 11 * hash + this.kikijoue;
        hash = 11 * hash + Float.floatToIntBits(this.proba);
        return hash;
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

    /**
     * Permet de générer tous les déplacements possibles d'un jeton en
     * retournant la liste des nouveaux états ainsi créés. Cette liste est triée
     * par ordre croissant de la distance à la sortie du jeton déplacé.
     *
     * A partir d'un état initial, on génère tous les états possibles en
     * déplaçant le jeton d'une case, puis on recommence à partir de chacun de
     * ces nouveaux états, jusqu'à ce que le jeton ne puisse plus se déplacer.
     *
     * @param numJeton l'indice du jeton à déplacer
     *
     */
    private ArrayList<Etat> deplacementsJoueur(int numJeton) {

        ArrayList<Etat> ouvert = new ArrayList<>(); //tous les états dont on doit générer les "fils".
        ArrayList<Etat> ferme = new ArrayList<>(); //La liste résultat

        ouvert.add(this);

        while (!ouvert.isEmpty()) { //tant qu'il reste des mouvements à faire
            Etat s = ouvert.get(0);
            ouvert.remove(0);

            Jeton j = this.kikijoue == HUMAIN ? s.humain[numJeton] : s.ordi[numJeton];

            if (j.getDeplacementsRestants() > 0) {//Si le jeton peut encore se déplacer
                for (Direction d : Direction.values()) {
                    Etat s2 = (Etat) s.clone();
                    Jeton j2 = this.kikijoue == HUMAIN ? s2.humain[numJeton] : s2.ordi[numJeton];
                    if (j2.seDeplacer(d)) {//si le déplacement est valide

                        if (!ferme.contains(s2)) {//Si on a créé un nouvel état

                            /*
                            On ajoute ce nouvel état dans la liste des successeurs, en la triant en fonction de la distance du pion déplacé à la sortie.
                             */
                            boolean superieur = true;
                            int i = 0;
                            while (i < ferme.size() && superieur) {
                                Jeton j3 = this.kikijoue == HUMAIN ? ferme.get(i).humain[numJeton] : ferme.get(i).ordi[numJeton];
                                if (j3.getPosition().distance(new Coordonnees(0, 0)) > j2.getPosition().distance(new Coordonnees(0, 0))) {
                                    superieur = false;
                                    ferme.add(i, s2);
                                }

                            }
                            ouvert.add(s2); //On ajoute ce nouvel état pour tester des déplacements à partir de celui-ci
                        }
                    }
                }
            }
        }

        return ferme;

    }

    /**
     * Indique si un état est la fin d'un tour et qu'il faut passer au tour
     * suivant, c'est-à-dire si tous les jetons ont été retournés.
     *
     * @return true si c'est le dernier état du tour, false sinon
     */
    private boolean finTour() {
        boolean fini = true;
        boolean faceTour = (this.tour % 2 == 1); //face blanche si congru à 1, noire sinon
        int i = 0;
        while (i < 4 && !fini) {
            fini = humain[i].estSurFaceBlanche() != faceTour && ordi[i].estSurFaceBlanche() != faceTour;
            i++;
        }
        return fini;
    }
}
