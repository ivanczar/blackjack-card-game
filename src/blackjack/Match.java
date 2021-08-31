package blackjack;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author ivanc
 */
public class Match {

    final int DECKCAPACITY = 52;
    // double playerBet = 0;

    CheckWinner checkWinner;
    Deck myDeck; // instantiates a deck object     
    Dealer dealer;
    CheckBJ checkBJ;
    Bet bet;

    public Match(Player player) {
        checkWinner = new CheckWinner();
        myDeck = new Deck(DECKCAPACITY);
        dealer = new Dealer();
        checkBJ = new CheckBJ();
        bet = new Bet();

        this.begin(player);
    }

    public void begin(Player player) {

        Scanner scan = new Scanner(System.in);

        // Prints state of player and prompts user for a bet amount
        System.out.println("Welcome " + player.getPlayerName() + ", You have " + player.getPlayerWins() + " wins, " + player.getPlayerLoss()
                + " losses and currently have a balance of $" + player.getPlayerBalance() + "\n");

        // playerBet = player.placeBet(); // sets bet to value returned by placeBet()
        bet.placeBet(player);

        while (!(player.getPlayerFinished() || dealer.getDealerFinished())) {

            // INITIAL DEAL of 2 cards p/player (dealer's second card ealth face down)
            for (int i = 0; i < 2; i++) {
                player.hit(myDeck.deal());
                dealer.hit(myDeck.deal());
            }

            
            checkBJ.checkBlackJack(player, dealer);

            // USER'S TURN   
            if ((!(player.getPlayerFinished() || dealer.getDealerFinished()))) {
                player.play(dealer, player, myDeck);
            }

            if ((player.calcHandValue() < 21)) {//is player hasnt bust
                printState(player, dealer);
                // DEALERS TURN if player has stood/not bust
                dealer.play(dealer, player, myDeck);
            }

        }

        System.out.println("ACTUAL PLAYER VALUE: " + player.calcHandValue());
        checkWinner.winCondition(player, dealer);

        bet.settleBet(player);

    }

    // break;
    public void printState(Player player, Dealer dealer) {
        System.out.println("=================================================================");
        System.out.println(player);
        System.out.println(dealer);
        System.out.println("=================================================================");
    }

}
