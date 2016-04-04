/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Corentin
 */
public class Plateau {
    
    Map<Coordonnees,Pion> plateau = new HashMap<>(); //Contient les cases occupées du plateau.
    ArrayList<Coordonnees> flaques = new ArrayList<>(); //Contient les cases sur lesquelles il y a une flaque de sang.
    
    /**
     * Plateau est le constructeur. Il place le zombie à sa case de départ, en [0;0]
     */
    public Plateau(){
        Coordonnees cle = new Coordonnees(0,0);
        this.plateau.put(cle, new Monstre(this));
    }
    
     /**
      * estUneFlaque est une méthode qui regarde si la case en paramètre est une flaque ou pas.
      * @param position case que l'on veut vérifier
      * @return true si la case est une flaque
      */
    public boolean estUneFlaque(Coordonnees position){
        boolean flaque = this.flaques.contains(position);
        return flaque;
    }
    
    public boolean valide(Coordonnees c){
        return true;
    }
}
