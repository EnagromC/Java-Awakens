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
    
    
    public static final int TAILLE_CASE = 36 ;
    String[] images;
    int imageActuelle;
    
    /**
     * Constructeur
     * @param adresses : les adresses des images
     */
    public JPion(String[] adresses) { 
        this.images = adresses;
        imageActuelle = 0;
        this.setSize(TAILLE_CASE, TAILLE_CASE);
        
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g) ;
        try {
            BufferedImage img = ImageIO.read(new File("img/"+images[imageActuelle]));
            g.drawImage(img,0,0,TAILLE_CASE, TAILLE_CASE, null) ;
        } catch (IOException ex) {
            Logger.getLogger(JPion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
