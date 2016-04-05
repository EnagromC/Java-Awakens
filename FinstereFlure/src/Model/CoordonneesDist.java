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
public class CoordonneesDist {
    private Coordonnees c;
    private int d;
    
    public CoordonneesDist(Coordonnees c, int d){
        this.c = c;
        this.d = d;
    }
    
    public Coordonnees getCoordonnees(){
        return this.c;
    }
    
    public int getDistance(){
        return this.d;
    }
    
    public boolean equals(Object o){
        CoordonneesDist cd = (CoordonneesDist) o;
        return this.c == cd.c;
    }
    
    public int hashCode(){
        return this.c.hashCode();
    }
}
