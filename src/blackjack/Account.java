package blackjack;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author ivanc
 */
public class Account {

    private HashMap<String, Player> players; // allows to store/retrieve Player object(value) via the player name(key)
    private String file; //stores path to file

    /*
    Constructor intitializes member variables and popualtes
    hashmap with txt file data
    */
    public Account() {
        this.file = "./resources/blackjackData.txt";
        this.players = new HashMap();
        this.getPlayers(file); 
    }

    /*
    reads from txt file all info stored for each player and stores in
    hashmap member variable
    */ 
    public void getPlayers(String file) {
        FileInputStream fin;
        try {
            fin = new FileInputStream(file);
            Scanner fileScan = new Scanner(fin);

            while (fileScan.hasNextLine()) {
                String line = fileScan.nextLine(); //stores line in String variable
                StringTokenizer st = new StringTokenizer(line); // breaks String into tokens
                Player p = new Player(st.nextToken(), Double.parseDouble(st.nextToken()) // creates player object
                , Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                this.players.put(p.getPlayerName(), p); //stores created player in hashmap

            }
            fin.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    checks if playerName already exists in txt file, if yes, it returns its 
    corresponding object. If no, it creates a new player with default values 
    and a starting balance
    */
    public Player checkPlayer(String playerName) {
        Player p;

        if (this.players.containsKey(playerName)) {
            p = this.players.get(playerName);

        } else {
            p = new Player(playerName, 50, 0, 0);
            this.players.put(playerName, p);
        }
        return p;
    }

    /*
    updates inputted player's balance by writing to text file
    */
    public void updateBalance(Player p) {
        this.players.put(p.getPlayerName(), p);
        try {
            FileOutputStream fout = new FileOutputStream(this.file);
            PrintWriter pw = new PrintWriter(fout);
            for (Player player : this.players.values()) {
                pw.println(player.getPlayerName() + " " + player.getPlayerBalance()
                     + " " + player.getPlayerWins() + " " + player.getPlayerLoss());

            }
            pw.close();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
