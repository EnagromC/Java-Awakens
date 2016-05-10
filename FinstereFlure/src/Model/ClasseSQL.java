/*
 * Cette classe contient toutes les requ�tes SQL pour communiquer avec la base de donn�es
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
    
    private Connection con ;
    
    public void connexionSQL(){
        String connectUrl = "jdbc:mysql://localhost/java";  
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
    }
    
    public void deconnexionSQL(){
        if (con != null) {
            try {
                con.close();
                System.out.println ("Database connection terminated.");
            } 
            catch (Exception e) { /* ignore close errors */ }
        }
    }
    /**
     * Cette m�thode cr�e un compte utilisateur avec comme entrées le pseudonyme et le mot de passe
     * @param pseudo
     * @param mdp
     * @return vrai si le compte a été crée, faux si un pseudo avec cette chaîne de caractère existe déjà, ou si il y a une erreur et qu'il ne rentre pas dans le catch.
     */
    public boolean creationCompte(String pseudo, String mdp){
        try {
            boolean compteExistant = verifCompte(pseudo) ;
            if (compteExistant){
                Statement req1 = con.createStatement();
                int nb = req1.executeUpdate("INSERT INTO CompteJoueur (Pseudonyme, MotDePasse) VALUES ("+pseudo+","+mdp+") ");
                return true;
            }
            else{
                System.out.println("Un compte avec ce pseudonyme existe déjà.");
                return false;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Cette méthode permet de renseigner le nom de l'utilisateur
     * @param nom
     */
    public void entrerNom(String nom){
        try {
            Statement req2 = con.createStatement();
            int nb = req2.executeUpdate("UPDATE CompteJoueur SET Nom =" +nom);
        }
        catch (SQLException ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Cette m�thode permet de renseigner le nom de l'utilisateur
     * @param prenom
     */
    public void entrerPrenom(String prenom){
        try{
            Statement req3 = con.createStatement();
            int nb = req3.executeUpdate("UPDATE CompteJoueur SET Prenom =" +prenom);
        }
        catch (SQLException ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * M�thode pour ins�rer une image dans l'avatar
     */
    public void insererAvatar(){
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
        try { //on cr�e la table image
            tableImage();
        }
        catch (Exception ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            // on ins�re l'image dans la base de donn�es
            PreparedStatement ps = con.prepareStatement("INSERT INTO image (name, img) values (?,?)");
            try {
                ps.setString(1, nomPourIm);
                ps.setBinaryStream(2, istreamImage, (int) avatar.length());
                ps.executeUpdate();
            }
            finally {
                ps.close();
            }
            //on r�cup�re l'image pour l'ins�rer dans l'attribut "avatar"
            Statement recupImage = con.createStatement();
            String query3 = "SELECT nomPourIm FROM image";
            ResultSet res = recupImage.executeQuery(query3);
            Statement reqIm = con.createStatement();
            int nb = reqIm.executeUpdate("UPDATE CompteJoueur SET Avatar =" +res);
            
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
     * M�thode pour cr�er une table "image" dans la bdd
     * @throws Exception vu qu'on l'appelle dans insererAvatar, c'est cette m�thode qui g�rera l'erreur
     */
    public void tableImage () throws Exception{
        Statement creation = con.createStatement();
        int nb = creation.executeUpdate("CREATE TABLE image name varchar(20) NOT NULL img mediumblob  PRIMARY KEY (name)");
    }
    
    /**
     * M�thode pour enregistrer une partie
     * nbTour correspond au nombre de tour au bout desquels la partie est gagn�e. Rappel : au bout de 14, la partie est finie.
     */
    public void enregistrerPartie(int nbTour){
        try {
            Statement req7 = con.createStatement();
            int nb = req7.executeUpdate("INSERT INTO Partie Values (DEFAULT," +nbTour+")");
        } //DEFAULT est utilis� pour les donn�es avec "NumAuto" pour remplir automatiquement. On peut aussi ne rien mettre.
        catch (SQLException ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Méthode pour connecter un compte joueur.
     * @param pseudoCo le pseudonyme du joueur
     * @param mdpCo le mot de passe utilisé pour se connecter
     * @return 
     */
    public boolean connexionIdent(String pseudoCo, String mdpCo){
        try {
            Statement req6 = con.createStatement();
            String query2 = "SELECT Pseudonyme, MotDePasse FROM CompteJoueur WHERE Pseudonyme ="+pseudoCo+" AND MotDePasse =" +mdpCo;
            ResultSet res = req6.executeQuery(query2);
            if (res.first()){
                String p = res.getString("Pseudonyme");//je me permet de rester à la première rangée, car si le programme fonctionne bien il n'y aura qu'un cas dans le res
                String m = res.getString("MotDePasse");
                if (p == pseudoCo && m == mdpCo){
                    return true;
                }
                else{
                    System.out.println("Login failed");
                    return false;
                }
            }
        }
        catch (Exception e){
        }
        System.out.println("Erreur");
        return false;
    }
    
    /**
     * Crée une occurrence de "participer"
     * @param pseudo 
     */
    public void participer(String pseudo){
        try {
            Statement req8 = con.createStatement();
            int nb = req8.executeUpdate("INSERT INTO Participer Values ("+pseudo+", DEFAULT)");
        }
        catch (SQLException ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Crée une occurrence de "gagner" table de la bdd
     */
    public void gagner(String pseudo){
        try {
            Statement req8 = con.createStatement();
            int nb = req8.executeUpdate("INSERT INTO Partie (Pseudonyme) Values ("+pseudo+")");
        } 
        catch (SQLException ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Cette méthode vérifie si un compte avec ce pseudo n'est pas déjà créé
     * @param pseudo prend en paramètre le pseudo que le joueur veut utiliser
     * @return vrai si le pseudonyme est libre, faux si il n'y est pas ou si il y a une erreur.
     */
    public boolean verifCompte(String pseudo){
        try {
            Statement req9 = con.createStatement();
            String queryCo = "SELECT Pseudonyme FROM CompteJoueur WHERE Pseudonyme = "+pseudo;
            ResultSet res = req9.executeQuery(queryCo);
            if (res.first()){ //regarde si il existe une occurrence dans le résultat. Si il n'en existe pas, c'est que le pseudo n'est pas pris.
                return false;
            }
            else{
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Erreur.");
        return false;
    }
    
    /**
     * Méthode qui affiche l'historique de nbPartie.
     * @param nbPartie nombre de partie que l'on souhaite afficher
     * @return un tableau de chaîne de caractère que l'on récupère pour afficher
     */
    public String[] historique(int nbPartie){
        String[] hist = new String[nbPartie];
        try {
            Statement req10 = con.createStatement();
            String queryHist = "SELECT ALL FROM Partie LIMIT "+nbPartie;
            ResultSet res = req10.executeQuery(queryHist);
            if (res.first()){
                int i;
                for (i=0;i<nbPartie;i++){
                    hist[i]=res.getString(i);
                }
            }
        }
        catch (SQLException ex){
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hist;
    }
}