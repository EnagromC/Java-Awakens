/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Corentin
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Monstre m = new Monstre(new Plateau());
        m.seDeplacer(new Coordonnees(10,12));
        m.direction = Direction.BAS;
        Coordonnees c = m.destinationTeleportation();
        System.out.println("x :" +c.getX()+" y:"+c.getY() );
    }
    
}
