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
public abstract class Pion {
    protected int[] position = new int [2] ;
    protected Plateau plateau ;
    
    public Pion(){
        this.plateau = new Plateau();
    }
    
    public Pion(int[] coordonnees){
        this.position = coordonnees;
        this.plateau = new Plateau();
    }
    
    public Pion(Plateau plateau){
        this.plateau = plateau;
    }
    
    public Pion(int[] coordonnees, Plateau plateau){
        this.position = coordonnees;
        this.plateau = plateau;
    }
    
}
