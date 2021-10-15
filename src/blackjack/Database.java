/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.awt.List;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Ivan
 */
public class Database {

    Connection conn = null;
    String url = "jdbc:derby:PlayerDB;create=true";  //url of the DB host
    //jdbc:derby://localhost:1527/BookStoreDB
    String dbusername = "pdc";  //your DB username
    String dbpassword = "pdc";   //your DB password

    public void dbsetup() {
        System.out.println("Db setting up...");

        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            Statement statement = conn.createStatement();
            String playerTable = "PLAYERINFO";
            String infoTable = "GAMEINFO";
            String leaderTable = "LEADERBOARD";

            
            if (!exists(playerTable)) {

                statement.executeUpdate("CREATE TABLE " + playerTable + " (username VARCHAR(12), password VARCHAR(12), balance DOUBLE, wins INT, loss INT)");
                System.out.println("playertable created!");
            }
            if (!exists(infoTable)) {

                statement.executeUpdate("CREATE TABLE " + infoTable + " (rules VARCHAR(150), credits VARCHAR(50))");
                System.out.println("infotable created!");

            }
            if (!exists(leaderTable)) {
                System.out.println("Leader table created");
                statement.executeUpdate("CREATE TABLE " + leaderTable + " (username VARCHAR(20), wins VARCHAR(10))");
                System.out.println("leadertable created!");
                

            }
//            populateInfoTable();
//            populateLeaderTable();
            statement.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String populateInfoTable() {
        String rules = "";
        try {
//            this.conn = DriverManager.getConnection(url, dbusername, dbpassword);
            Statement statement = conn.createStatement();
            System.out.println("POPULATING CREDITS");
            rules = "Rules: The goal of blackjack is to beat the dealer's hand without going over 21.\n"
                + "Face cards are worth 10. Aces are worth 1 or 11, whichever makes a better hand.\n"
                + "Each player starts with two cards, one of the dealer's cards is hidden until the end.\n"
                + "To 'Hit' is to ask for another card. To 'Stand' is to hold your total and end your turn.\n"
                + "If you go over 21 you bust, and the dealer wins regardless of the dealer's hand.\n"
                + "If you are dealt 21 from the start (Ace & 10), you got a blackjack.\n"
                + "Blackjack means you win 2x the amount of your bet. Winning without blackjack is 1.5x.\n"
                + "Dealer will hit until their cards total 17 or higher.";
            
            String credits = "Ivan Czar - AUT UNIVERSITY - 2021";
            statement.executeUpdate("INSERT INTO GAMEINFO VALUES('" + rules + "', '" + credits + "')");
            
            statement.close();

        } catch (Throwable e) {
            System.out.println("populate info failed");

        }
        return rules;
    }

    public String populateLeaderTable() {
        System.out.println("populating leader table");

        
        String leaderBoard = "";
        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            Statement statement = conn.createStatement();
           ResultSet rs = statement.executeQuery("SELECT username, wins, loss FROM PLAYERINFO ORDER BY wins DESC");

            while (rs.next()) {

                String username = rs.getString("username");
                String wins = String.valueOf(rs.getInt("wins"));
                if (Integer.parseInt(wins) != 0) {
                    
//                    statement.executeUpdate("INSERT INTO LEADERBOARD VALUES('" + username + "', '" + wins + "')");

                    leaderBoard += "USER: " + username + " WINS: " + wins + "\n";
                }

            }

            System.out.println(leaderBoard);
            
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return leaderBoard;
    }

    //checkName method
    public Player checkName(String username, String password) {

        System.out.println("Checking name in db");
        Player player = new Player();
        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT password, balance, wins, loss FROM PLAYERINFO WHERE username = '" + username + "'");

            if (rs.next()) {
                String confirmed = rs.getString("password");

                if (password.compareTo(confirmed) == 0) //if passworad is correct, load values from database to Data object
                {
                    System.out.println("Match Found!");
                    player.setPlayerName(username);
                    player.setPlayerBalance(rs.getDouble("balance"));
                    player.setPlayerWins(rs.getInt("wins"));
                    player.setPlayerLoss(rs.getInt("loss"));
                    player.setLoginFlag(true);

                } else {
                    System.out.println("mismAtch password");
                    player.setLoginFlag(false);

                }
            } else {
                // if username does not exist, create a new user in database with default values
                System.out.println("No such user");
                statement.executeUpdate("INSERT INTO PLAYERINFO VALUES('" + username + "', '" + password + "', 1000 , 0 ,0)");
                player.setPlayerName(username);
                player.setPlayerBalance(1000);
                player.setPlayerWins(0);
                player.setPlayerLoss(0);
                player.setLoginFlag(true);
            }
            statement.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return player;
    }

    public boolean exists(String tableName) {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, tableName, null);

            if (!rs.next()) {
                return false;
            }

           
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public void quitGame(String username, double balance, int wins, int loss) {
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.executeUpdate("UPDATE PLAYERINFO SET balance=" + balance + ", wins=" + wins + ", loss=" + loss + " WHERE username='" + username + "'");

            statement.close();
//            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
