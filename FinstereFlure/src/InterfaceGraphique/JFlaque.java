/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceGraphique;

import Model.Coordonnees;
import java.awt.Graphics;
import java.awt.Rectangle;
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

    public JFlaque(Rectangle r) {
        super();
        if (r.getHeight() == r.getWidth()) {//tache carrée
            this.setSize(76, 76);
            this.image = "tachesang_carree.png";

        } else if (r.getHeight() > r.getWidth()) {//tache verticale
            this.setSize(38, 152);
            this.image = "tachesang_lineaireV.png";
        } else {//tache horizontale
            this.setSize(152, 38);
            this.image = "tachesang_lineaireH.png";
        }

        this.setLocation(JPlateau.position(new Coordonnees(r.x, r.y)));
this.setOpaque(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            BufferedImage img = ImageIO.read(new File("img/" + this.image));
            g.drawImage(img, 0, 0, this.getSize().width, this.getSize().height, null);
        } catch (IOException ex) {
            Logger.getLogger(JPion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
