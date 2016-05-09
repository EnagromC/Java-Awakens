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
 * Permet d'afficher une tache de sang
 *
 * @author Corentin
 */
public class JFlaque extends JPanel {

    private String image;


    public JFlaque(Forme forme) {
        super();
        switch (forme) {
            case CARRE:
               this.setSize(76, 76);
               this.image = "tachesang_carree.png";
               break;
            case HORIZ:
                this.setSize(152,38);
                this.image = "tachesang_lineaireH.png";
                break;
            case VERT:
                this.setSize(38,152);
                this.image = "tachesang_lineaireV.png";
                break;
        }

    }
    
        public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            BufferedImage img = ImageIO.read(new File("img/" +this.image));
            g.drawImage(img, 0, 0,this.getSize().width , this.getSize().height, null);
        } catch (IOException ex) {
            Logger.getLogger(JPion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        enum Forme {
    CARRE,
    HORIZ,
    VERT;
}
}


