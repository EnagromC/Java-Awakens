/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceGraphique;

import java.awt.Component;
import javax.swing.JPanel;

/**
 * Cette classe permet d'afficher plusieurs JPions à la queuleuleu
 * @author Corentin
 */
public class JGroupePions extends JPanel{
    boolean horizontal; //Sens du groupe : vertical ou horizontal
    int nombrePions; //nombre de pions dans le groupe
    public static final int MARGE = 2;
    
    /**
     * On créé le groupe en définissant son sens
     * @param horizontal 
     */
    public JGroupePions(boolean horizontal){
        this.horizontal = horizontal;
    }
    
    /**
     * Permet d'ajouter un pion à la fin du groupe
     * @param c le pion à ajouter
     * @return le pion ajouté
     */
    @Override
    public Component add(Component c){
        if(horizontal){
            c.setLocation(nombrePions*(JPion.TAILLE_CASE+1)+MARGE, MARGE);
        }else{
            c.setLocation(MARGE,nombrePions*(JPion.TAILLE_CASE+1)+MARGE);
        }
        
        super.add(c);
        nombrePions++;
        
        return c;

    }
    
    
    /**
     * Permet de supprimer un pion du groupe
     * @param c le pion à supprimer
     */
    @Override
    public void remove(Component c){
        nombrePions--;
        super.remove(c);
    }
    
}
