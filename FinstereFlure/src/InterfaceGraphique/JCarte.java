/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceGraphique;

import Model.Carte;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *Cette classe permet d'afficher l'image des cartes sur l'interface.
 * @author Morgane
 */
public class JCarte extends JPanel {
    private JLabel nombre ;
    private JLabel typeCarte ;
    
    
    public JCarte () {
        super() ;
        nombre = new JLabel("");
        typeCarte = new JLabel("");
        this.setSize(100, 100) ;
        this.add(nombre) ;
        this.add(typeCarte) ;
        this.setBorder(BorderFactory.createLineBorder(Color.PINK, 3, true));
        
        
    }
    /**
     * Crée une JCarte à  partir d'une Carte passée en paramètre.
     * @param c : la carte à partir de laquelle on veut créer la JCarte.
     */
    public void createCarte(Carte c) {
        this.nombre.setText(String.valueOf(c.getNombre()));
        if (c.getProie()){
            this.typeCarte.setText("Proies");
        }
        else {
            this.typeCarte.setText("Pas") ;
        }
        
        
    }
    
}
