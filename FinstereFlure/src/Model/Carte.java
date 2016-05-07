/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Morgane
 */
public class Carte {
     private int nombre ;
     private boolean proie ; 
    
    /**
     * C'est le constructeur !
     * @param nombre : le nombre de proies ou de pas à manger/effectuer.
     * @param proie : true si c'est une carte "proies", false si c'est une carte "pas".
     */
    public Carte(int nombre, boolean proie) {
        this.nombre = nombre ;
        this.proie = proie ;
    }
    
    public int getNombre(){
        return this.nombre ;
    }
    
    public boolean getProie(){
        return this.proie ;
    }
    
}
