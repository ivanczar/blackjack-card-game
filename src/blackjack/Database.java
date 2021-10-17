/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;

/**
 *
 * @author Ivan
 */
public class Database {

    Connection conn = null;
    String url = "jdbc:derby:PlayerDB;create=true";  //url of the DB host
    String dbusername = "pdc";  //your DB username
    String dbpassword = "pdc";   //your DB password

    /**
     * Creates PLAYERINFO, GAMEINFO and LEADERBOARD tables
     */
    public void dbsetup() {
        System.out.println("Db setting up...");

        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            Statement statement = conn.createStatement();
            String playerTable = "PLAYERINFO";
            String infoTable = "GAMEINFO";
            String leaderTable = "LEADERBOARD";

//            statement.executeUpdate("DROP TABLE PLAYERINFO");
            if (!exists(playerTable)) {

                statement.executeUpdate("CREATE TABLE " + playerTable + " (username VARCHAR(12), password VARCHAR(12), balance DOUBLE, wins INT, loss INT)");
                System.out.println("PLAYERINFO created!");
            }
            if (!exists(infoTable)) {

                statement.executeUpdate("CREATE TABLE " + infoTable + " (rules VARCHAR(1000), credits VARCHAR(50))");
                System.out.println("GAMEINFO created!");

            }
            if (!exists(leaderTable)) {
                System.out.println("Leader table created");
                statement.executeUpdate("CREATE TABLE " + leaderTable + " (username VARCHAR(20), wins VARCHAR(10))");
                System.out.println("LEADERBOARD created!");

            }
            populateInfoTable();
            populateLeaderTable();
            statement.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * PopuLates info table with GAmE rules and credits
     *
     * @return
     */
    public void populateInfoTable() {
        String rules = "";
        try {
//            this.conn = DriverManager.getConnection(url, dbusername, dbpassword);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO GAMEINFO (rules, credits) VALUES (?,?)");
            System.out.println("POPULATING CREDITS");
            rules = "Rules: The goal of blackjack is to beat the dealer's hand without going over 21.\n"
                    + "Face cards are worth 10. Aces are worth 1 or 11, whichever makes a better hand.\n"
                    + "Each player starts with two cards, one of the dealer's cards is hidden until the end.\n"
                    + "To 'Hit' is to ask for another card. To 'Stand' is to hold your total and end your turn.\n"
                    + "If you go over 21 you bust, and the dealer wins regardless of the dealer's hand.\n"
                    + "If you are dealt 21 from the start (Ace & 10), you got a blackjack.\n"
                    + "Blackjack means you win 2x the amount of your bet. Winning without blackjack is 1.5x.\n"
                    + "Dealer will hit until their cards total 17 or higher.";

            String credits = "Ivan Czar 19088501 - AUT UNIVERSITY - 2021";
            pstmt.setString(1, rules);
            pstmt.setString(2, credits);
//          
            pstmt.executeUpdate();
            pstmt.close();

        } catch (Throwable e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);

        }

    }

    /**
     * returns querry of rules from GAMEINFO table
     *
     * @return
     */
    public String getRules() {
        String rules = "";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT rules FROM GAMEINFO");

            if (rs.next()) {
                rules = rs.getString("rules");
            }

        } catch (SQLException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }

        return rules;
    }

    /**
     * returns querry of credits from GAMEINFO table
     *
     * @return
     */
    public String getCredits() {
        String credits = "";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT credits FROM GAMEINFO");

            if (rs.next()) {
                credits = rs.getString("credits");
            }

        } catch (SQLException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }

        return credits;
    }

    /**
     * Populates LEADERBOARD table and retrieves string containing leaderboard
     * values and credits
     *
     * @return
     */
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
                    
//                    statement.executeUpdate("INSERT INTO LEADERBOARD VALUES('" + username + "', '" + wins + "')"); CANT FIGURE OUT THIS ERROR (AUTOCOMMIT=OFF???)
                    leaderBoard += "USER: " + username + "\t WINS: " + wins + "\n";
                }

            }

            System.out.println(leaderBoard);

            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return leaderBoard;
    }


    /**
     * Checks in PLAYERINFO databaSe if username exists, if not creates a new
     * user.
     *
     * @param username
     * @param password
     * @return
     */
    public Player checkName(String username, String password) {

        System.out.println("Checking name in db");
        Player player = new Player();
        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT password, balance, wins, loss FROM PLAYERINFO WHERE username = '" + username + "'");

            if (rs.next()) {
                String pass = rs.getString("password");

                if (password.compareTo(pass) == 0) //if passworad is correct, load values from database to Data object
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

    /**
     * Checks if tableName has already been created
     *
     * @param tableName
     * @return
     */
    public boolean exists(String tableName) {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, tableName, null);

            if (!rs.next()) {
                System.out.println("Tablename does not exist");
                return false;

            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    /**
     * Updates PLAYERINFO table
     *
     * @param username
     * @param balance
     * @param wins
     * @param loss
     */
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
