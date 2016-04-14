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
     * Initialise un pion à partir de ses coordonnées
     *
     * @param c coordonnées du pion sur le plateau
     */
    public Pion(Coordonnees c) {
        this.position = c;
        this.plateau = new Plateau();
        this.plateau.addPion(this, c);
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
        this.plateau.addPion(this, coordonnees);
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
    /**
     * Déplace un pion vers de nouvelles coordonnées si elles sont valides
     *
     * @param c les coordonnées de la case d'arrivée
     */
    public void seDeplacer(Coordonnees c) {

        if (plateau.valide(c) && plateau.caseLibre(c)) {
            plateau.movePion(this.position, c);
            this.position = c;
            //Il faut voir pour prendre en comtpe les taches de sang.
        } else {
            System.out.println("Déplacement invalide");
        }

    }

    /**
     * Déplace le pion de 1 case dans une direction, si le mouvement est valide
     *
     * @param d la direction du déplacement
     */
    public void seDeplacer(Direction d) {
        Coordonnees newCoord = this.position.plus(d.getVector());

        if (plateau.valide(newCoord) && plateau.caseLibre(newCoord)) {
            
            
            
            plateau.movePion(this.position, newCoord);
            this.position = newCoord;
            while (this.plateau.estUneFlaque(this.position)) {
                this.seDeplacer(d);
            }
        } else {
            System.out.println("Déplacement invalide");
        }

    }

}
