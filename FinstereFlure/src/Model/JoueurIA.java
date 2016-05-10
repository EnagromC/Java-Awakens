/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import IA.Etat;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Corentin
 */
public class JoueurIA extends Joueur {

    ////////////////////////////////////////////////////////////////////////////
    // Attributs
    ////////////////////////////////////////////////////////////////////////////
    private HashMap<Etat, Float> dejaCalcules; //Les utilités des noeuds déjà calculées, afin de ne pas refaire les calculs

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public JoueurIA() {
        super(baptiser());
    }

    public JoueurIA(Plateau p) {
        super(baptiser(), p);
        dejaCalcules = new HashMap<>();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Algorithme expectiminimax avec élagage alpha-bêta.
     *
     * Cet algorithme a pour but de déterminer, pour chaque état, s'il conduit
     * plutôt à la victoire ou à la défaite. Pour cela, on génère tous les coups
     * possible jusqu'à la fin de la partie, on regarde ke résultat de la partie
     * et on le propage en remontant, en sachant qu'on choisit toujours le coup
     * le meilleur et que l'humain choisit toujours le coup le plus défavorable.
     * Dans le cas de noeuds "Chance", on prend la moyenne des valeurs de chaque
     * fils, pondérée par leur probabilité respective.
     *
     * On effectue un élagage alpha-bêta dans les noeuds Humain et Ordi, afin
     * d'éliminer les branches inutiles. On suppose que la méthode
     * Etat.successeurs() renvoit les successeurs triés selon une heuristique
     * afin de maximiser le nombre de coupes alpha ou bêta.
     *
     * Une fois qu'on a calculé la valeur d'un noeud, on l'ajoute à
     * dejaCalcules. Si on le rencontre a nouveau, il n'y a donc pas besoin de
     * dérouler l'algorithme mais seulement de récupéré sa valeur dans cette
     * Map.
     *
     * @param sinit L'état initial dont on cherche à connaître la valeur
     * @param alpha valeur alpha du noeud
     * @param beta valeur bêta du noeud
     * @return la valeur du noeud
     */
    public float expectiminimax(Etat sinit, float alpha, float beta) {

        //Si on a déjà calculé la valeur avant , on la retourne simplement
        if (this.dejaCalcules.containsKey(sinit)) {
            return dejaCalcules.get(sinit);
        }

        //Si on est dans un état final, on sait qui a gagné
        if (sinit.fini()) {
            return sinit.kikawon();
        }

        //Sinon il faut propager
        float v = 0;
        if (sinit.kikijoue() == Etat.ORDI) {
            v = Float.NEGATIVE_INFINITY;
            //On choisit la plus grande valeur des successeurs
            for (Etat fils : sinit.successeurs()) {
                v = Math.max(v, expectiminimax(fils, alpha, beta));
                if (v >= beta) { //Coupure bêta
                    dejaCalcules.put(sinit, v);
                    return v;
                } else {
                    alpha = Math.max(alpha, v);
                }

            }
        } else if (sinit.kikijoue() == Etat.HUMAIN) {
            v = Float.POSITIVE_INFINITY;
            //On choisit la plus petite valeur des successeurs
            for (Etat fils : sinit.successeurs()) {
                v = Math.min(v, expectiminimax(fils, alpha, beta));
                if (alpha >= v) { //coupure alpha
                    dejaCalcules.put(sinit, v);
                    return v;
                } else {
                    beta = Math.min(beta, v);
                }
            }
        } else if (sinit.kikijoue() == Etat.CHANCE) {
            v = 0;
            //Ici on prend la valeur moyenne
            for (Etat fils : sinit.successeurs()) {
                v += fils.proba() * expectiminimax(fils, alpha, beta);
            }
        }

        dejaCalcules.put(sinit, v);
        return v;
    }

    /**
     * Permet au joueurIA de trouver le meilleur coup et de le jouer
     */
    @Override
    public void jouer() {

        //Choix du meilleur coup possible
        Etat sinit = new Etat(super.p);
        float max = Integer.MIN_VALUE;
        Etat meilleur = new Etat();
        for (Etat s : sinit.successeurs()) {
            float valeur = expectiminimax(s, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
            if (valeur > max) {
                max = valeur;
                meilleur = s;
            }
        }
        
        mettreAJour(p, meilleur);
        p.getVue().updatePlateau();

    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes privées
    ////////////////////////////////////////////////////////////////////////////    
    /**
     * Génère un nom rigolo pour l'ordinateur
     *
     * @return un nom rigolo
     */
    private static String baptiser() {
        return "Ordinateur";
    }

    /**
     * Permet de mettre à jour la Partie en fonction d'un Etat, c'est-à-dire de
     * modifier la partie pour qu'elle soit identique à l'état.
     *
     * On considère que les seuls changements possibles sont liés au déplacement
     * d'un jeton de l'ordi, donc seuls les cailloux ou les jetons de l'ordi
     * peuvent être différents.
     *
     * @param p la partie à modifier
     * @param s l'état modèle
     */
    private void mettreAJour(Partie p, Etat s) {
        Plateau partie = p.getPlateau();
        Plateau etat = s.getPlateau();

        //On calcule la différence symétrique entre l'ensemble des coordonnées des cases occupées du plateau de la Partie et de l'Etat
        //c'est-à-dire toutes les cases qui sont différentes entre ces 2 ensembles
        HashSet<Coordonnees> intersection = new HashSet<>(partie.getPlateau().keySet());
        intersection.retainAll(etat.getPlateau().keySet());
        HashSet<Coordonnees> differences = new HashSet<>(partie.getPlateau().keySet());
        differences.addAll(etat.getPlateau().keySet());
        differences.removeAll(intersection);

        //Pour chacune de ces cases, on la vide dans le plateau de la Partie, et on y ajoute un caillou s'il y en a un dans l'Etat
        for (Coordonnees c : differences) {
            partie.removePion(partie.getCase(c), c);
            if (etat.getCase(c) instanceof Caillou) {
                new Caillou(c, partie);
            }
        }

        //Puis on regarde pour chaque pion de l'ordi s'il a les mêmes coordonnées dans l'Etat et la Partie. Si non, on le déplace dans la Partie.
        for (int i = 0; i < 4; i++) {
            if (s.getOrdi()[i].getPosition() != p.getJoueur2().getPions()[i].getPosition()) {
                p.getJoueur2().getPions()[i].seDeplacer(s.getOrdi()[i].getPosition());
            }

        }

    }
}
