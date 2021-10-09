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
        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            Statement statement = conn.createStatement();
            String tableName = "PLAYERINFO";

            if (!exists(tableName)) {
                statement.executeUpdate("CREATE TABLE " + tableName + " (username VARCHAR(12), password VARCHAR(12), balance DOUBLE, wins INT, loss INT)");
            }
            //statement.executeUpdate("INSERT INTO " + tableName + " VALUES('Fiction',0),('Non-fiction',10),('Textbook',20)");
            statement.close();

        } catch (Throwable e) {
            System.out.println("error");

        }
    }

    //checkName method
    public Data checkName (String username, String password){
        
        Data data = new Data();
      try{ 
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT username, password, balance, wins, loss FROM PLAYERINFO WHERE username = '" + username + "'");
        
        if (rs.next()){
            String confirmed = rs.getString("password");  
            
            if (password.equals(confirmed)) //if passworad is correct, load values from database to Data object
            {
                data.balance = rs.getDouble("balance");
                data.wins = rs.getInt("wins");
                data.loss = rs.getInt("loss");
                data.loginFlag = true; 
            }
            else
                data.loginFlag = false;
        }
        else
        {
            // if username does not exist, create a new user in database with default values
            System.out.println("No such user");
            statement.executeUpdate("INSERT INTO PLAYERINFO VALUES ('" + username + "', '" + password + "' , 0 , 0 ,0");
            data.balance = 0;
            data.wins = 0;
            data.loss = 0;
            data.loginFlag = true;
        }
      }catch(SQLException ex)
      {
          Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
      }
      return data; 
    }
    
    
    
    
    
    //quitGame method
    public void quitGame(int score, String username) {
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.executeUpdate("UPDATE UserInfo SET score=" + score + " WHERE userid='" + username + "'");

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    
    public static void main(String[] args) {
        Database db = new Database();
        db.dbsetup();
    }
}
