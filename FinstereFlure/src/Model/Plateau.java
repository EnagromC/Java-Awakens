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
    
    Map<int[],Pion> plateau = new HashMap<>(); //Contient les cases occupées du plateau.
    ArrayList<Integer[]> flaques = new ArrayList<>(); //Contient les cases sur lesquelles il y a une flaque de sang.
    
    /**
     * Plateau est le constructeur. Il place le zombie à sa case de départ, en [0;0]
     */
    public Plateau(){
        int[] cle = new int[2];
        cle[0] = 0;
        cle[1] = 1;
        this.plateau.put(cle, value) // value est à remplacer par Zombie
    }
    
     /**
      * estUneFlaque est une méthode qui regarde si la case en paramètre est une flaque ou pas.
      * @param position case que l'on veut vérifier
      * @return true si la case est une flaque
      */
    public boolean estUneFlaque(int[] position){
        boolean flaque = this.flaques.contains(position);
        return flaque;
    }
}
