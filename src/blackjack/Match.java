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

    EndGame end;
    Deck myDeck; // instantiates a deck object     
    Dealer dealer;
    CheckBJ checkBJ;
    Bet bet;

    public Match(Player player) {
        end = new EndGame();
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
                + " losses and currently have a balance of $" + player.getPlayerBalance());

        // playerBet = player.placeBet(); // sets bet to value returned by placeBet()
        bet.placeBet(player);

        while (!(player.getPlayerFinished() || dealer.getDealerFinished())) {

            // INITIAL DEAL of 2 cards p/player (dealer's second card ealth face down)
            for (int i = 0; i < 2; i++) {
                player.hit(myDeck.deal());
                dealer.hit(myDeck.deal());
            }

            
            printState(player, dealer);
            checkBJ.checkBlackJack(player, dealer);

            

            
            // USER'S TURN   
            if ((!(player.getPlayerFinished() || dealer.getDealerFinished()))) {
                player.play(dealer, player, myDeck);
            }
            
            if ((player.calcHandValue()<21)) {//is player hasnt bust
                printState(player, dealer);
                // DEALERS TURN if player has stood/not bust
                dealer.play(dealer, player, myDeck);
            }

        }

        end.winCondition(player, dealer);

        bet.settleBet(player);

    }

    // break;
    public void printState(Player player, Dealer dealer) {
        System.out.println("=================================================================");
        System.out.println(player);
        System.out.println(dealer);
        System.out.println("=================================================================");
    }

    static class EndGame {

        static int winner = 0;

        /**
         * Determines who won and updates wins/losses
         *
         * @param player
         * @param dealer
         */
        public void winCondition(Player player, Dealer dealer) {
            //Checks winning conditions
            if(player.calcHandValue() > 21) // if player busts
            {
                System.out.println(player.getPlayerName() + " bust!");
                System.out.println("Dealer wins");
                player.setPlayerLoss(player.getPlayerLoss() + 1);
            }
            if(dealer.calcHandValue() > 21) // if dealer busts
            {
                 System.out.println("Dealer bust!");
                System.out.println(player.getPlayerName() +" wins");
                player.setPlayerWins(player.getPlayerWins() + 1);
                this.winner = 3;
            }
            if (dealer.getDealerHand().getHandValue() == player.getPlayerHand().getHandValue()) // tie
            {
                System.out.println("\n======Tie!======");
              

                this.winner = 1;
            } else if (dealer.getDealerHand().getHandValue() > player.getPlayerHand().getHandValue() && dealer.getDealerHand().getHandValue() <= 21) // dealer wins
            {
                System.out.println("\n======Dealer wins!======");
                player.setPlayerLoss(player.getPlayerLoss() + 1);

                this.winner = 2;
            } else if (dealer.getDealerHand().getHandValue() < player.getPlayerHand().getHandValue() && player.getPlayerHand().getHandValue() <= 21)// player wins
            {
                System.out.println("\n======" + player.getPlayerName() + " wins!======");
                
                player.setPlayerWins(player.getPlayerWins() + 1);

                this.winner = 3;
            }

        }

    }

}
