/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import InterfaceGraphique.Vue;

/**
 * Représente l'état et le déroulement d'une partie de Finstere Flure.
 *
 * @author Corentin
 */
public class Partie {
    private Vue vue;
    private Joueur joueur1;
    Joueur joueur2;
    private Plateau plateau;
    private int manche;
    private int tour;
    
    public Partie(Vue v,Joueur j1, Joueur j2){
        this.vue = v;
        joueur1 = j1;
        joueur2 = j2;
        plateau = new Plateau();
        this.manche = 0;
        this.tour = 0;
    }
    
    public Joueur getJoueur1() {
        return this.joueur1 ;
    }
    
    public Joueur getJoueur2(){
        return this.joueur2 ;
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
    
}
