/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Corentin
 */
public class Joueur {

    Jeton[] pions = new Jeton[4];

    public Joueur() {
        pions[0] = new Jeton(6, 1);
        pions[1] = new Jeton(4, 3);
        pions[2] = new Jeton(3, 4);
        pions[3] = new Jeton(2, 5);
    }
}
