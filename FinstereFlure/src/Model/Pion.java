/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Représente un "pion", c'est-à-dire une entité pouvant être posée sur le
 * plateau et déplacée.
 *
 * @author Corentin
 */
public abstract class Pion {

    ////////////////////////////////////////////////////////////////////////////
    // Attributs
    ////////////////////////////////////////////////////////////////////////////
    protected Coordonnees position; //la position du pion sur le plateau
    protected Plateau plateau; //le plateau sur lequel est placé le pion

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur par défaut
     */
    public Pion() {
        this.plateau = new Plateau();
    }

    /**
     * Initialise un pion à partir d'un tableau de coordonnées
     *
     * @param coordonnees
     */
    public Pion(Coordonnees c) {
        this.position = c;
        this.plateau = new Plateau();
    }

    /**
     * Initialise un pion à partir d'un plateau
     *
     * @param plateau
     */
    public Pion(Plateau plateau) {
        this.plateau = plateau;
    }

    /**
     * Initialise un pion à partir d'un tableau de coordonnées et d'un plateau
     *
     * @param coordonnees
     * @param plateau
     */
    public Pion(Coordonnees coordonnees, Plateau plateau) {
        this.position = coordonnees;
        this.plateau = plateau;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
    public Coordonnees getPosition() {
        return this.position;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    public void seDeplacer(Direction d) {
        this.position = this.position.plus(d.getVector());

        if (plateau.valide(this.position)) {
            //Changer les coordonnées dans le plateau
        } else {
            this.position = this.position.plus(d.getVector().fois(-1)); //On annule le déplacement
            System.out.println("Déplacement invalide");
        }
        //Attention, il faut aussi vérifier que les nouvelles coordonnées sont valides et bouger sur le plateau.
    }
}
