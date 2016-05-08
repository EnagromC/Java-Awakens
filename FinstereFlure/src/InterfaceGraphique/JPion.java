/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceGraphique;

import Model.Coordonnees;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Morgane
 */
public class JPion extends JPanel {

    public static final int TAILLE_CASE = 36;
    protected String[] sprites;
    protected int imageActuelle;
    private int numJoueur;

    /**
     * Constructeur
     *
     * @param adresses : les adresses des images
     *
     */
    public JPion(String[] adresses) {
        this.sprites = adresses;
        imageActuelle = 0;
        this.setSize(TAILLE_CASE, TAILLE_CASE);
        
        this.numJoueur = 0;

    }

    /**
     * Constructeur
     *
     * @param adresses : les adresses des images
     * @param numJoueur : le joueur à qui appartient le pion
     */
    public JPion(String[] adresses, int numJoueur) {
        this.sprites = adresses;
        imageActuelle = 0;
        this.setSize(TAILLE_CASE, TAILLE_CASE);

        this.numJoueur = numJoueur;
    }

    public Coordonnees getCoordonnees() {
        int x = (this.getY() - 20) / 40;
        int y = (this.getX() - 27) / 40;
        return new Coordonnees(x, y);
    }

    public int getNumJoueur() {
        return this.numJoueur;
    }

    public void nextSprite() {
        this.imageActuelle = (this.imageActuelle + 1) % sprites.length;
        this.repaint();
    }

    public void setSprite(int n) {
        this.imageActuelle = n % sprites.length;
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            BufferedImage img = ImageIO.read(new File("img/" + sprites[imageActuelle]));
            g.drawImage(img, 0, 0, TAILLE_CASE, TAILLE_CASE, null);
        } catch (IOException ex) {
            Logger.getLogger(JPion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void select() {
        this.setBorder(BorderFactory.createLineBorder(Color.yellow, 5)); //Queque chose pour montrer que le pion est sélectionné
    }

    public void unselect() {
        this.setBorder(null);
    }
}
