/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import IA.Etat;
import java.util.HashMap;

/**
 *
 * @author Corentin
 */
public class JoueurIA extends Joueur{
    private HashMap<Etat,Integer> dejaCalcules; //Les utilités des noeuds déjà calculées.
    
    public JoueurIA(Plateau p){
        super(baptiser(),p);
        dejaCalcules = new HashMap<>();
    }
    
    public int expectiminimax(Etat sinit, int prof, int alpha, int beta){
        if(this.dejaCalcules.containsKey(sinit)){
            return dejaCalcules.get(sinit);
        }
        
        if(prof == 0 || sinit.fini()){
            return sinit.kikawon();
        }
        int v = 0;
        if(sinit.kikijoue() == Etat.ORDI){
            v = Integer.MIN_VALUE;
            for(Etat fils : sinit.successeurs()){
                v = Math.max(v, expectiminimax(fils,prof-1,alpha,beta));
                if(v >= beta){ //Coupure bêta
                    dejaCalcules.put(sinit, v);
                    return v; 
                }else{
                    alpha = Math.max(alpha, v);
                }
                    
            }
        }else if(sinit.kikijoue() == Etat.HUMAIN){
            v = Integer.MAX_VALUE;
            for(Etat fils:sinit.successeurs()){
                v = Math.min(v, expectiminimax(fils,prof-1,alpha,beta));
                if(alpha >= v){ //coupure alpha
                    dejaCalcules.put(sinit, v);
                    return v;
                }else{
                    beta = Math.min(beta,v);
                }
            }
        }else if(sinit.kikijoue() == Etat.CHANCE){
            v = 0;
            for(Etat fils : sinit.successeurs()){
                v+= fils.proba()*expectiminimax(fils,prof-1,alpha,beta);
            }
        }
        
        dejaCalcules.put(sinit, v);
        return v;
    }
    
    
    
    
    /**
     * Génère un nom rigolo pour l'ordinateur
     * @return un nom rigolo
     */
    private static String baptiser(){
        return "Barnabé";
    }
}
