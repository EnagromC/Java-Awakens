/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceGraphique;

import Model.Carte;

/**
 *
 * @author Corentin
 */
public interface Vue {
    public void updatePlateau();
    public void tourJoueur(int numJoueur);
    public void updatePaquet(Carte c);
    public void updateInfosPartie();
    public void gameOver();
    
}
