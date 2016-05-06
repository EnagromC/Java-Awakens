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

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public Joueur(String pseudo) {
        pions[0] = new Jeton(1, 6);
        pions[1] = new Jeton(3, 4);
        pions[2] = new Jeton(4, 3);
        pions[3] = new Jeton(5, 2);
        this.pseudo = pseudo;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
    public Jeton[] getPions() {
        return this.pions;
    }
    
    public String getPseudo(){
        return this.pseudo;
    }
}
