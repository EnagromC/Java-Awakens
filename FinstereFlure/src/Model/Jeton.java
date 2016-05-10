/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.HashSet;

/**
 * Représente les jetons possédés par le joueur, et qu'il doit amener à la
 * sortie.
 *
 * @author Morgane
 */
public class Jeton extends Pion implements Traversable {

    ////////////////////////////////////////////////////////////////////////////
    // Attributs
    ////////////////////////////////////////////////////////////////////////////
    private boolean surPlateau; //Indique si un pion est sur le plateau, ou dans la réserve du joueur / éliminé
    private int[] faces = new int[2]; //valeur des 2 faces du pion. Par convention, la première est la blanche et la deuxième la noire.
    private boolean faceBlanche; //indique si le pion est retourné sur la face blanche (false = face noire)
    private boolean enJeu; //Indique si un pion est encore en jeu ou s'il a été éliminé/est sorti
    private boolean vivant; //indique si un pion est encore vivant. Si un pion est vivant mais pas en jeu, il est sorti.
    private int deplacementsRestants; //nombre de points de déplacement restants pour le tour

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur du jeton. Le nombre de déplacements correspondant à chaque
     * face est passé en paramètre. Par défaut, la face visible est la blanche,
     * et le jeton n'est pas sur le plateau, mais il est en jeu (il est vivant).
     * De plus, un jeton est traversable (on peut passer sur une case dans
     * laquelle il se trouve).
     *
     * @param blanche : le nombre de déplacements sur la face blanche.
     * @param noire : le nombre de déplacements sur la face noire.
     * @param p : le plateau auquel est rattaché le Jeton
     */
    public Jeton(int blanche, int noire, Plateau p) {
        super(p);
        this.surPlateau = false;
        this.faces[0] = blanche;
        this.faces[1] = noire;
        this.enJeu = true;
        this.vivant = true;
        this.deplacementsRestants = blanche;

        this.faceBlanche = true;

    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
    public boolean getEnJeu() {
        return this.enJeu;
    }

    public boolean getSurPlateau() {
        return this.surPlateau;
    }

    public boolean getVivant() {
        return this.vivant;
    }

    public int getFaceBlanche() {
        return this.faces[0];
    }

    public int getFaceNoire() {
        return this.faces[1];
    }

    public int getDeplacementsRestants() {
        return this.deplacementsRestants;
    }

    public boolean estSurFaceBlanche() {
        return this.faceBlanche;
    }

    public void setPlateau(Plateau p) {
        this.plateau = p;
    }

    public void setVivant(boolean b) {
        this.vivant = b;
    }

    public void setSurPlateau(boolean b) {
        this.surPlateau = b;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Permet de retourner le pion, c'est-à-dire de le mettre sur la face
     * blanche s'il était sur la face noire, ou vice-versa.
     */
    public void retourner() {
        this.faceBlanche = !this.faceBlanche;
        this.deplacementsRestants = this.faces[this.faceBlanche ? 0 : 1];
    }

    /**
     * Permet de faire se déplacer un jeton d'une case dans une direction, en
     * vérifiant que le mouvement est possible. Si la case est libre, le pion y
     * est simplement déplacée. Si c'est une flaque de sang, il glisse. Si la
     * case contient un autre jeton, il ne peut y rentrer que s'il la quitte
     * immédiatement en glissant, ou s'il lui reste assez de points de
     * déplacements pour la quitter ensuite. Si c'est un caillou, il le pousse
     * si possible et prend sa place.
     *
     * On suppose que quand cette méthode est appelée, le joueur a un nombre de
     * points de déplacements non nul.
     *
     * @param d la direction du déplacement
     * @return true si le déplacement a été effectué, false s'il était invalide.
     */
    public boolean seDeplacer(Direction d) {
        Coordonnees newCoord = this.position.plus(d.getVector()); //coordonnées de la case d'arrivée

        if (plateau.valide(newCoord)) {//On vérifie que cette case est bien sur le plateau
            if (plateau.caseLibre(newCoord)) { //Si elle est libre, on déplace le pion grâce à la méthode mère (qui gère les glissades...)
                super.seDeplacer(d);
                this.deplacementsRestants--;
                System.out.println("cas 1");
                return true;

            } else if (plateau.getCase(newCoord) instanceof Traversable) { //Si la case contient un joueur
                if (this.deplacementsRestants > 1) { //Dans le cas où on a au moins 2 points de déplacement on peut y rentrer.
                    this.position = newCoord; //On ne met ici pas à jour la position dans le plateau afin de ne pas effacer l'autre joueur se trouvant dans cette case. La mise à jour du plateau aura lieu au rochain mouvement de ce jeton, qui doit de toute façon quitter cette case pour finir son tour (on suppose que le bouton de fin de tour n'est pas disponible alors)
                    this.deplacementsRestants--;
                    if (plateau.estUneFlaque(this.position)) {//On gère la glissade éventuelle
                        this.seDeplacer(d);
                    }
                    return true;

                } else if (plateau.estUneFlaque(newCoord)) {//Dans le cas ou le joueur n'a plus qu'un seul point de déplacement, il ne peut éventuellement entrer sur la case que si elle lui permet de la quitter immédiatement en glissant
                    this.position = newCoord;
                    if (this.seDeplacer(d)) { //On vérifie alors si le joueur peut glisser
                        this.deplacementsRestants--;
                        return true;
                    } else { //Si non, on lui remet ses anciennes coordonnées pour annuler le déplacement
                        this.position.plus(d.getVector().fois(-1));
                        return false;
                    }
                }

            } else if (plateau.getCase(newCoord) instanceof Caillou) { //Si la case contient un caillou
                Caillou caillou = (Caillou) plateau.getCase(newCoord);
                if (caillou.seDeplacer(d)) { //On pousse le caillou d'une case
                    this.seDeplacer(d); //On prend sa place
                    return true;
                }
            }
        }

        return false; //Si le mouvement n'est pas possible
    }

    /**
     * Indique si le joueur peut finir son tour. Plus exactement, indique s'il
     * est bien sur une case libre, ou s'il est sur une case occupée par un
     * autre joueur (auquel cas il est obligé de quitter la case avant de finir
     * son tour)
     *
     * @return true si le jeton est seul sur sa case, false s'il est sur la case
     * d'un autre jeton.
     */
    public boolean peutFinirTour() {
        return plateau.getCase(this.position) == this;
    }

    /**
     * Indique si le pion est dans la salle d'attente
     *
     * @return
     */
    public boolean dansSalleDAttente() {
        return this.vivant && this.enJeu && !this.surPlateau;
    }

    /**
     * Fait entrer un pion de la salle d'attente dans la case en bas à droite du
     * plateau.
     */
    public void entrerPlateau() {
        if (this.dansSalleDAttente()) {
            this.position = new Coordonnees(10, 15);
            this.surPlateau = true;
            this.deplacementsRestants--;
            plateau.addPion(this, position);
        }
    }

    public Object clone() {
        Jeton j = new Jeton(this.faces[0], this.faces[1], new Plateau());
        j.surPlateau = this.surPlateau;
        j.deplacementsRestants = this.deplacementsRestants;
        j.enJeu = this.enJeu;
        j.vivant = this.vivant;
        j.faceBlanche = this.faceBlanche;

        return j;

    }
}
