/*
 * Cette classe contient toutes les requêtes SQL pour communiquer avec la base de données
 */
package Model;

import java.sql.*;
import java.util.Scanner;
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
            
            /* A partir d'ici, ce sont toutes les requêtes, sans soucis de connexion avec l'interface graphique */
            
            /* Dans cette requête, on crée un tuple avec le pseudo et le mdp*/
            Scanner sc1 = new Scanner(System.in);
            System.out.println("Veuillez saisir un pseudonyme");
            String pseudo = sc1.nextLine();
            Scanner sc2 = new Scanner(System.in);
            System.out.println("Veuillez saisir un mot de passe");
            String mdp = sc2.nextLine();
            
            Statement req1 = con.createStatement();
            int nb = req1.executeUpdate("INSERT INTO CompteJoueur (Pseudonyme, MotDePasse) " + "VALUES (pseudo, mdp) ");
            
            /* 
             * A partir d'ici, il faut (dans l'idéal) que l'utilisateur clique sur la case pour que les instructions s'exécutent.
             */
            
            /* Requête pour entrer le nom */
            Scanner sc3 = new Scanner(System.in);
            String nom = sc3.nextLine();
            Statement req2 = con.createStatement();
            nb = req2.executeUpdate("UPDATE CompteJoueur" + "SET Nom = nom");
            
            /* Requête pour entrer le prénom */
            Scanner sc4 = new Scanner(System.in);
            String prenom = sc4.nextLine();
            Statement req3 = con.createStatement();
            nb = req3.executeUpdate("UPDATE CompteJoueur" + "SET Prenom = prenom");
            
            /* Requête pour donner un avatar */
            Scanner sc5 = new Scanner(System.in);
            String avatar = sc5.nextLine();
            Statement req4 = con.createStatement();
            nb = req4.executeUpdate("UPDATE CompteJoueur" + "SET Avatar = url "); // Voir si on héberge l'avatar sur un site ou si on peut le récupérer de l'ordi de l'utilisateur
            
            /* 
             * Attention requête pour le nombre de parties gagnées :o !!!
             * Ici on récupère le nombre de parties gagnées dans le premier morceau 
             */
            
            Statement stmt = con.createStatement();
            String query = "SELECT distinct Pseudonyme FROM Partie" + "WHERE Pseudonyme = pseudo";
            ResultSet rs = stmt.executeQuery(query);
            int nbPartiesGagnees = 0;
            while (rs.next()) {
                nbPartiesGagnees += 1;
            }
            Statement req5 = con.createStatement(); // Deuxième morceau : On enregistre le nombre de parties gagnées
            nb = req5.executeUpdate("UPDATE CompteJoueur" + "SET NbPartiesGagnées = nbPartiesGagnees");
            
            /* 
             * Requête pour la connection
             */
            try {
                Scanner sc6 = new Scanner(System.in);
                String pseudoCo = sc6.nextLine();
                Scanner sc7 = new Scanner(System.in);
                String mdpCo = sc7.nextLine();
                Statement req6 = con.createStatement();
                String query2 = "SELECT Pseudonyme, MotDePasse FROM CompteJoueur" + "WHERE Pseudonyme LIKE pseudoCo AND MotDePasse LIKE mdpCo";
                ResultSet res = stmt.executeQuery(query2);
            }
            catch (Exception e){
                System.out.println("Identifiants incorrects, ou je me suis fail, mais ça c'est pas possible.");
            }
            
            
            
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
}
