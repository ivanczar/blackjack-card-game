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

    EndGame end = new EndGame();
    Deck myDeck = new Deck(DECKCAPACITY); // instantiates a deck object     
    Dealer dealer = new Dealer();
    CheckBJ checkBJ = new CheckBJ();
    Bet bet = new Bet();

    public Match(Player player) {
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

            checkBJ.checkBlackJack(player, dealer);

            printState(player, dealer);

            // USER'S TURN   
            if ((!(player.getPlayerFinished() || dealer.getDealerFinished()))) {
                player.play(dealer, player, myDeck);
            }
            printState(player, dealer);
            if (!(player.getPlayerFinished())) {

                // dealers turn if player has stood/not bust
                dealer.play(dealer, player, myDeck);
            }

        }
        
        end.winCondition(player,dealer);
        
        
        bet.settleBet(player);


    }
    // break;
    public void printState(Player player, Dealer dealer) {
        System.out.println("=================================================================");
        System.out.println(player);
        System.out.println(dealer);
        System.out.println("=================================================================");
    }
        static class EndGame{
        
        static int winner = 0;
        /**
         * Determines who won and updates wins/losses
         * @param player
         * @param dealer 
         */
        public void winCondition(Player player, Dealer dealer){
                    //Checks winning conditions
        if (dealer.getDealerHand().getHandValue() == player.getPlayerHand().getHandValue()) // tie
        {
            System.out.println("\n======Tie!======");
           // player.setPlayerBalance(player.getPlayerBalance() + (bet.getBetAmount())); // return bet to player

            this.winner =1;
        } else if (dealer.getDealerHand().getHandValue() > player.getPlayerHand().getHandValue() && dealer.getDealerHand().getHandValue() <= 21) // dealer wins
        {
            System.out.println("\n======Dealer wins!======");
            player.setPlayerLoss(player.getPlayerLoss() + 1);

            this.winner=2;
        } else if (dealer.getDealerHand().getHandValue() < player.getPlayerHand().getHandValue() && player.getPlayerHand().getHandValue() <= 21)// player wins
        {
            System.out.println("\n======" + player.getPlayerName() + " wins!======");
            //player.setPlayerBalance(player.getPlayerBalance() + (bet.getBetAmount() * 1.5));
            player.setPlayerWins(player.getPlayerWins() + 1);

            this.winner=3;
        }
            System.out.println(this.winner + "IS WINNER");
        }
        
        
        
    }

}



