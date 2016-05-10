
import IA.Etat;
import Model.Joueur;
import Model.JoueurIA;
import Model.Paquet;
import Model.Plateau;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Corentin
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Plateau p = new Plateau();
        Joueur j = new Joueur("dfr",p);
        JoueurIA j2 = new JoueurIA(p);
        Etat s = new Etat(j.getPions(), j2.getPions(), p, 0, 0, new Paquet(), Etat.HUMAIN);
        System.out.println(j2.expectiminimax(s, 100, Integer.MIN_VALUE, Integer.MAX_VALUE));
    }
    
}
