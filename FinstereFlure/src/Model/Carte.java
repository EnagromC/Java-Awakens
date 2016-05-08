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
    
  
     @Override
    public boolean equals(Object o){
        if(o instanceof Carte){
            Carte c = (Carte) o;
            return c.nombre == this.nombre && c.proie == this.proie;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.nombre;
        hash = 41 * hash + (this.proie ? 1 : 0);
        return hash;
    }
    
     @Override
    public Object clone(){
        return new Carte(this.nombre, this.proie);
    }
    
    
}
