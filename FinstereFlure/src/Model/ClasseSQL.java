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
    
    /**
     * Méthode pour connecter le programme à la base de données
     */
    public void connexionSQL(){
        String connectUrl = "jdbc:mysql://localhost/finstereFlure";  
        String username = "finstereFlure", password =""; //utilisés pour getConnexion
        Connection con = null;
        try{
            Class.forName(" com.mysql.jdbc.Driver ").newInstance(); // on enregistre le driver JDBC
            con = DriverManager.getConnection( connectUrl, username, password);
            System.out.println ("Database connection established.");
        }
        catch (Exception e){
            System.out.println("Erreur de connexion");
        }
    }
    
    /**
     * Méthode pour deconnecter le serveur MySQL
     */
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
     * Cette méthode crée un compte utilisateur avec comme entrées le pseudonyme et le mot de passe
     * @param pseudo
     * @param mdp
     * @return vrai si le compte a été crée, faux si un pseudo avec cette chaîne de caractère existe déjà, ou si il y a une erreur et qu'il ne rentre pas dans le try.
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
     * Cette méthode permet de renseigner le prénom de l'utilisateur
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
     * Méthode pour insérer une image dans la base de données, en tant qu'avatar
     */
    public void insererAvatar(){
        Scanner imLoc = new Scanner(System.in);//on récupère la localisation de l'image
        String location = imLoc.nextLine();
        Scanner nomIm = new Scanner(System.in);//on récupère le nom que l'on veut donner à l'image
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
            tableImage();
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
            String query3 = "SELECT "+nomPourIm+" FROM image";
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
     * Méthode pour créer une table "image" dans la bdd
     * @throws Exception vu qu'on l'appelle dans insererAvatar, c'est cette méthode qui gèrera l'erreur
     */
    public void tableImage () throws Exception{
        Statement creation = con.createStatement();
        int nb = creation.executeUpdate("CREATE TABLE image name varchar(20) NOT NULL img mediumblob  PRIMARY KEY (name)");
    }
    
    /**
     * Méthode pour enregistrer une partie
     * nbTour correspond au nombre de tour au bout desquels la partie est gagnée. Rappel : au bout de 14, la partie est finie.
     */
    public void enregistrerPartie(int nbTour){
        try {
            Statement req7 = con.createStatement();
            int nb = req7.executeUpdate("INSERT INTO Partie Values (DEFAULT," +nbTour+")");
        } //DEFAULT est utilisé pour les données avec "NumAuto" pour remplir automatiquement. On peut aussi ne rien mettre.
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
     * Enregistre le pseudo du gagnant dans la partie correspondante
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
    public String[][] historique(){
        String[][] hist = new String[4][10]; //stock le résultat de res, pour ensuite le retourner
        try {
            Statement req11 = con.createStatement();
            String queryHist1 = "SELECT NumPartie FROM Partie LIMIT 10 ";
            ResultSet res1 = req11.executeQuery(queryHist1);
            if (res1.first()){
                int i;
                for (i=0;i<10;i++){
                    hist[1][i]=res1.getString(i);
                }
            }
            Statement req12 = con.createStatement();
            String queryHist2 = "SELECT NbTour FROM Partie LIMIT 10";
            ResultSet res2 = req12.executeQuery(queryHist2);
            if (res2.first()){
                int i;
                for (i=0;i<10;i++){
                    hist[2][i]=res2.getString(i);
                }
            }
            Statement req13 = con.createStatement();
            String queryHist3 = "SELECT Pseudonyme FROM Partie LIMIT 10";
            ResultSet res3 = req13.executeQuery(queryHist3);
            if (res3.first()){
                int i;
                for (i=0;i<10;i++){
                    hist[3][i]=res3.getString(i);
                }
            }
            Statement req14 = con.createStatement();
            String queryHist4 = "SELECT DatePartie FROM Partie LIMIT 10";
            ResultSet res4 = req14.executeQuery(queryHist4);
            if (res4.first()){
                int i;
                for (i=0;i<10;i++){
                    hist[4][i]=res4.getString(i);
                }
            }
        }
        catch (SQLException ex){
            Logger.getLogger(ClasseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hist;
    }
}