/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author Morgane
 */
public class Paquet {
    ArrayList<Carte> paquet = new ArrayList<>() ;
    
    /**
     * C'est le constructeur ! On ajoute chacune des cartes dans le paquet, puis on le mélange.
     */
    public Paquet() {
        this.paquet.add(new Carte(5, false)) ;
        this.paquet.add(new Carte(7, false)) ;
        this.paquet.add(new Carte(7, false)) ;
        this.paquet.add(new Carte(8, false)) ;
        this.paquet.add(new Carte(8, false)) ;
        this.paquet.add(new Carte(10, false)) ;
        this.paquet.add(new Carte(1, true)) ;
        this.paquet.add(new Carte(2, true)) ;
        Collections.shuffle(this.paquet) ; //On mélange le paquet;
        while (!this.paquet.get(0).getProie()) { //Tant que la première carte est une carte "proie", on remélange.
            Collections.shuffle(this.paquet) ;
        }
    }
    
    /**
     * On pioche une carte, et on la supprime du paquet.
     * @return la carte piochée.
     */
    public Carte piocherCarte() {
        Carte c = this.paquet.get(0) ;
        this.paquet.remove(0) ;
        return c ;
    }
    
    public int taille(){
        return this.paquet.size();
    }
    
    public boolean vide(){
        return this.paquet.isEmpty();
    }
    
    
    public HashMap<Carte,Integer> getComposition(){
        HashMap<Carte,Integer> hm = new HashMap<>();
        for (Carte c : this.paquet){
            if(hm.containsKey(c)){
                hm.replace(c, hm.get(c)+1);
            }else{
                hm.put(c, 1);
            }
        }
        return hm;
    }
    
    
    @Override
    public Object clone(){
        Paquet p = new Paquet();
        ArrayList<Carte> cartes = new ArrayList<>();
        for(Carte c : this.paquet){
            cartes.add((Carte) c.clone());
        }
        
        return p;
    }
    
}
