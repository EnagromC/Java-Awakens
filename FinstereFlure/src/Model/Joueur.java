/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Représente un joueur lors d'une partie, avec ses pions et son score.
 *
 * @author Corentin
 */
public class Joueur {

    ////////////////////////////////////////////////////////////////////////////
    // Attributs
    ////////////////////////////////////////////////////////////////////////////
    Jeton[] pions = new Jeton[4];
    String pseudo;
    Partie p;
    int numero;

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public Joueur(String pseudo) {
        this.pseudo = pseudo;
    }

    public Joueur(String pseudo, Plateau p) {
        pions[0] = new Jeton(1, 6, p);
        pions[1] = new Jeton(3, 4, p);
        pions[2] = new Jeton(4, 3, p);
        pions[3] = new Jeton(5, 2, p);
        this.pseudo = pseudo;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
    public Jeton[] getPions() {
        return this.pions;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public void setPartie(Partie p) {
        this.p = p;
    }

    public void setPlateau(Plateau p) {
        pions[0] = new Jeton(1, 6, p);
        pions[1] = new Jeton(3, 4, p);
        pions[2] = new Jeton(4, 3, p);
        pions[3] = new Jeton(5, 2, p);
    }

    public void setNumero(int n) {
        this.numero = n;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Permet de faire jouer le joueur. Si c'est un joueur humain, appelle la
     * fonction de l'interface qui lui demande de saisir son déplacement. Si
     * c'est l'ordi, ça joue le coup automatiquement (méthode redéfinie)
     */
    public void jouer() {
        p.getVue().tourJoueur(numero);
    }
}
