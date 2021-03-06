/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Objects;

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
        this.position = new Coordonnees(-1,-1);
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
        this.position = new Coordonnees(-1,-1);
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
    public void seDeplacer(Coordonnees c) {
        this.plateau.movePion(this, this.position, c);
        this.position = c;
    }

    /**
     * Déplace le pion de 1 case dans une direction, et glisse si on arrive sur
     * une tâche de sang. Redéfinir la méthode dans les classes filles en
     * faisant appel à celle-là pour pouvoir ajouter des conditions de
     * déplacements.
     *
     * @param d la direction du déplacement
     * @return true si on a réussi à se déplacer, false sinon
     */
    public boolean seDeplacer(Direction d) {
        Coordonnees destination = this.position.plus(d.getVector());
        plateau.movePion(this, this.position, destination);
        this.position = destination;

        //On glisse si on est sur une flaque de sang
        while (this.plateau.estUneFlaque(this.position)) {
            seDeplacer(d); //C'est ici la méthode redéfinie dans les classes filles qui est appelée, elle prend donc en compte les contraintes.
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pion) {
            Pion p = (Pion) o;
            return this.position.equals(p.position);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.position);
        return hash;
    }

}
