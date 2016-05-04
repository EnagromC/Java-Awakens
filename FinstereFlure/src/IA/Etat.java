/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IA;

import Model.Jeton;
import Model.Paquet;
import Model.Plateau;

/**
 *
 * @author Morgane
 */
public class Etat {
    private Jeton[] humain; //On s'en fiche de son nom, on veut juste ses jetons.
    private Jeton[] ordi;
    private Plateau plateau;
    private int manche;
    private int tour;
    private Paquet paquet ;
    public static final int HUMAIN = 0 ;
    public static final int ORDI = 1 ;
    public static final int CHANCE = 2 ;
    

    
    public Jeton[] getHumain() {
        return this.humain ;
    }
    
    public Jeton[] getOrdi(){
        return this.ordi ;
    }
    
    public Plateau getPlateau() {
        return this.plateau ;
    }
    
    public int getManche() {
        return this.manche ;
    }
    
    public int getTour() {
        return this.tour ;
    }
    
    public Paquet getPaquet() {
        return this.paquet ;
    }
    
    public boolean fini() {
        
    }
    
    public int kikijoue() {
        
    }
    
    
    public int kikawon() { //Indique kikagagné : -1 si c'est l'humain, 1 l'IA, 0 si match nul.        
    }
    
    
}
