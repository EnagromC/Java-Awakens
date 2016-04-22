/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceGraphique;

import Model.Coordonnees;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;

/**
 *
 * @author Morgane
 */
public class JPlateau extends JLayeredPane {

    public final String bgImage = "img/fns_plateau.jpg";

    public JPlateau() {

    }

    /**
     * Indique la position en pixels d'une case en fonction de ses coordonnées
     * dans la grille.
     *
     * @param c les coordonnées dans la grille
     * @return un point
     */
    public Point position(Coordonnees c) {
        int x = c.getXint() * 40 + 27;
        int y = c.getYint() * 40 + 20;
        return new Point(x, y);

    }

    /**
     * On redéfinit la méthode paintComponent pour afficher une image sur le
     * pion.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            BufferedImage img = ImageIO.read(new File(bgImage));
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
        } catch (IOException ex) {
            Logger.getLogger(JPion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
