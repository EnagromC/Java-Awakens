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
    ////////////////////////////////////////////////////////////////////////////
    // Attributs
    ////////////////////////////////////////////////////////////////////////////
    private HashMap<Etat,Integer> dejaCalcules; //Les utilités des noeuds déjà calculées, afin de ne pas refaire les calculs
    
    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    public JoueurIA(){
        super(baptiser());
    }
    
    public JoueurIA(Plateau p){
        super(baptiser(),p);
        dejaCalcules = new HashMap<>();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Algorithme expectiminimax avec élagage alpha-bêta.
     * @param sinit
     * @param alpha
     * @param beta
     * @return 
     */
    public int expectiminimax(Etat sinit, int alpha, int beta){
        System.out.println("a");
        if(this.dejaCalcules.containsKey(sinit)){
            return dejaCalcules.get(sinit);
        }
        
        if(sinit.fini()){
            return sinit.kikawon();
        }
        int v = 0;
        if(sinit.kikijoue() == Etat.ORDI){
            v = Integer.MIN_VALUE;
            for(Etat fils : sinit.successeurs()){
                v = Math.max(v, expectiminimax(fils,alpha,beta));
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
                v = Math.min(v, expectiminimax(fils,alpha,beta));
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
                v+= fils.proba()*expectiminimax(fils,alpha,beta);
            }
        }
        
        dejaCalcules.put(sinit, v);
        return v;
    }
    
    
    @Override
    public void jouer(){
        
        //Choix du meilleur coup possible
        Etat sinit = new Etat(super.p);
        int max = Integer.MIN_VALUE;
        Etat meilleur;
        for(Etat s : sinit.successeurs()){
            int valeur = expectiminimax(s, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if(valeur>max){
                max = valeur;
                meilleur = s;
            }
        }
        
        
        
        

    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Méthodes privées
    ////////////////////////////////////////////////////////////////////////////    
    
    /**
     * Génère un nom rigolo pour l'ordinateur
     * @return un nom rigolo
     */
    private static String baptiser(){
        return "Barnabé";
    }
    
    private void mettreAJour(Partie p, Etat s){
        
    }
}
