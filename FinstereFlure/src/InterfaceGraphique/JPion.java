/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceGraphique;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Morgane
 */
public class JPion extends JPanel {
    
    
    public static final int tailleCase = 36 ;
    String image ;
    
    /**
     * Constructeur
     * @param chaine : l'adresse de l'image
     */
    public JPion(String chaine) {
        this.image = chaine ;
        this.setSize(tailleCase, tailleCase);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g) ;
        try {
            BufferedImage img = ImageIO.read(new File("//img"+image));
            g.drawImage(img,0,0,tailleCase, tailleCase, null) ;
        } catch (IOException ex) {
            Logger.getLogger(JPion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
