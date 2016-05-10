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

    ////////////////////////////////////////////////////////////////////////////
    // Attributs
    ////////////////////////////////////////////////////////////////////////////
    public static final int TAILLE_CASE = 36;
    protected String[] images; //toi t'as besoin de garder l'adresse que de l'image actuelle
    protected int imageActuelle; //donc ça ça sert à rien
    private int numJoueur;//les 2 int là aussi
    private int numPion;

    ////////////////////////////////////////////////////////////////////////////
    // Constructeurs
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur
     *
     * @param adresses : les adresses des images
     *
     */
    public JPion(String[] adresses) {
        this.images = adresses;
        imageActuelle = 0;
        this.setSize(TAILLE_CASE, TAILLE_CASE);

        this.numJoueur = 0; //Ici le pion n'est pas lié à un joueur (c'est genre un caillou)

    }

    /**
     * Constructeur
     *
     * @param adresses : les adresses des images
     * @param numJoueur : le joueur à qui appartient le pion
     * @param numPion : numéro du pion dans le tableau
     */
    public JPion(String[] adresses, int numJoueur, int numPion) {
        this.images = adresses;
        imageActuelle = 0;
        this.setSize(TAILLE_CASE, TAILLE_CASE);

        this.numJoueur = numJoueur;
        this.numPion = numPion;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Accesseurs
    ////////////////////////////////////////////////////////////////////////////
    public Coordonnees getCoordonnees() {
        int x = (this.getY() - 20) / 40;
        int y = (this.getX() - 27) / 40;
        return new Coordonnees(x, y);
    }

    public int getNumJoueur() {
        return this.numJoueur;
    }

    public int getNumPion() {
        return this.numPion;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Méthodes publiques
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Permet de passer à l'image suivante dans la liste de simages
     */
    public void nextImagte() {
        this.imageActuelle = (this.imageActuelle + 1) % images.length;
        this.repaint();
    }

    /**
     * Permet de passer à l'image n dans la liste des images. Si ça dépasse la
     * taille, on revient au début
     *
     * @param n le numéro de l'image
     */
    public void setImage(int n) {
        this.imageActuelle = n % images.length;
        this.repaint(); //Il faut repaint() pour rappeler paintComponent() et mettre effectivement à jour l'image.
    }

    /**
     * On redéfinit cette méthode pour choisir l'image à afficher
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            BufferedImage img = ImageIO.read(new File("img/" + images[imageActuelle]));
            g.drawImage(img, 0, 0, TAILLE_CASE, TAILLE_CASE, null);
        } catch (IOException ex) {
            Logger.getLogger(JPion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Permet de changer l'affichage du pion pour montrer qu'il est sélectionner
     */
    public void select() {
        this.setBorder(BorderFactory.createLineBorder(Color.yellow, 5)); //Queque chose pour montrer que le pion est sélectionné
    }

    /**
     * Annule l'effet de select()
     */
    public void unselect() {
        this.setBorder(null);
    }
}
