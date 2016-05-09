/*
 * Cette classe contient toutes les requêtes SQL pour communiquer avec la base de données
 */
package Model;

import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Laurine
 */
public class ClasseSQL { 
    public static void main (String[]args){
        String connectUrl = "jdbc:mysql://localhost/nomdelabase"; //à changer avec l'url de la bdd 
        String username = "nom", password ="mdp"; //utilisé pour getConnexion
        Connection con = null;
        try{
            Class.forName(" com.mysql.jdbc.Driver ").newInstance(); // on enregistre le driver JDBC
            con = DriverManager.getConnection( connectUrl, username, password);
            System.out.println ("Database connection established.");
        }
        catch (Exception e){
            System.out.println("Erreur");
        }
        finally {
                // à la fin, on ferme la connection avec la BdD
            if (con != null) {
                try {
                    con.close();
                    System.out.println ("Database connection terminated.");
                } 
                catch (Exception e) { /* ignore close errors */ }
            }
        }
    }
    
    /**
     * Cette méthode crée un compte utilisateur avec comme entrées le pseudonyme et le mot de passe
     * @param con connexion à la base de donnée
     */
    public void creationCompte(Connection con){
        try {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Veuillez saisir un pseudonyme");
        String pseudo = sc1.nextLine();
        Scanner sc2 = new Scanner(System.in);
        System.out.println("Veuillez saisir un mot de passe");
        String mdp = sc2.nextLine();

        Statement req1 = con.createStatement();
        int nb = req1.executeUpdate("INSERT INTO CompteJoueur (Pseudonyme, MotDePasse) " + "VALUES (pseudo, mdp) ");
        }
        catch (SQLException ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Cette méthode permet de renseigner le nom de l'utilisateur 
     * @param con connexion à la bdd
     */
    public void entrerNom(Connection con){
        try {
            Scanner sc3 = new Scanner(System.in);
            String nom = sc3.nextLine();
            Statement req2 = con.createStatement();
            int nb = req2.executeUpdate("UPDATE CompteJoueur" + "SET Nom = nom");
        }
        catch (SQLException ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Cette méthode permet de renseigner le nom de l'utilisateur
     * @param con connexion à la bdd
     */
    public void entrerPrenom(Connection con){
        try{
            Scanner sc4 = new Scanner(System.in);
            String prenom = sc4.nextLine();
            Statement req3 = con.createStatement();
            int nb = req3.executeUpdate("UPDATE CompteJoueur" + "SET Prenom = prenom");
        }
        catch (SQLException ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Méthode pour insérer une image dans l'avatar
     * @param con 
     */
    public void insererAvatar(Connection con){
        Scanner imLoc = new Scanner(System.in);
        String location = imLoc.nextLine();
        Scanner nomIm = new Scanner(System.in);
        String nomPourIm = nomIm.nextLine();
        File avatar = new File(location);
        FileInputStream istreamImage = null;
        try {
            istreamImage = new FileInputStream(avatar);
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        try { //on crée la table image
            tableImage(con);
        }
        catch (Exception ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            // on insère l'image dans la base de données
            PreparedStatement ps = con.prepareStatement("INSERT INTO image (name, img) values (?,?)");
            try {
                ps.setString(1, nomPourIm);
                ps.setBinaryStream(2, istreamImage, (int) avatar.length());
                ps.executeUpdate();
            }
            finally {
                ps.close();
            }
            //on récupère l'image pour l'insérer dans l'attribut "avatar"
            Statement recupImage = con.createStatement();
            String query3 = "SELECT nomPourIm FROM image";
            ResultSet res = recupImage.executeQuery(query3);
            Statement reqIm = con.createStatement();
            int nb = reqIm.executeUpdate("UPDATE CompteJoueur" + "SET Avatar = res");
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                istreamImage.close();
            } catch (IOException ex) {
                Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Méthode pour créer une table "image" dans la bdd
     * @param con
     * @throws Exception vu qu'on l'appelle dans insererAvatar, c'est cette méthode qui gêrera l'erreur
     */
    public void tableImage (Connection con) throws Exception{
        Statement creation = con.createStatement();
        int nb = creation.executeUpdate("CREATE TABLE image " + "name varchar(20) NOT NULL" + "img mediumblob" + "  PRIMARY KEY  (name)");
    }
    
    /**
     * Méthode pour enregistrer une partie
     * @param con 
     * nbTour correspond au nombre de tour au bout desquels la partie est gagnée. Rappel : au bout de 14, la partie est finie.
     */
    public void enregistrerPartie(Connection con){
        try {
        Statement req7 = con.createStatement();
        int nb = req7.executeUpdate("INSERT INTO Partie" + "Values (DEFAULT, nbTour)");
        } //DEFAULT est utilisé pour les données avec "NumAuto" pour remplir automatiquement. On peut aussi ne rien mettre.
        catch (SQLException ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Méthode pour connecter un compte joueur. Elle demande le pseudonyme et le mdp.
     * @param con 
     */
    public void connexion(Connection con){
        try {
                Scanner sc6 = new Scanner(System.in);
                System.out.println("Veuillez entrer votre pseudonyme");
                String pseudoCo = sc6.nextLine();
                Scanner sc7 = new Scanner(System.in);
                System.out.println("Veuillez entrer votre mot de passe");
                String mdpCo = sc7.nextLine();
                Statement req6 = con.createStatement();
                String query2 = "SELECT Pseudonyme, MotDePasse FROM CompteJoueur" + "WHERE Pseudonyme LIKE pseudoCo AND MotDePasse LIKE mdpCo";
                ResultSet res = req6.executeQuery(query2);
            }
            catch (Exception e){
                System.out.println("Identifiants incorrects, ou je me suis fail, mais ça c'est pas possible.");
            }
    }
    
    /**
     * Crée une occurrence de "participer"
     * @param con 
     */
    public void participer(Connection con){
        try {
            Statement req8 = con.createStatement();
            int nb = req8.executeUpdate("INSERT INTO Participer" + "Values (pseudo1, DEFAULT");
        }
        catch (SQLException ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Crée une occurrence de "gagner" table de la bdd
     * @param con 
     */
    public void gagner(Connection con){
        try {
            Statement req8 = con.createStatement();
            int nb = req8.executeUpdate("INSERT INTO Gagner" + "Values ( , DEFAULT");
        } //revoir le modèle E/A et le modèle relationnel.
        catch (SQLException ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}