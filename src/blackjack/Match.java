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

    Deck myDeck = new Deck(DECKCAPACITY); // instantiates a deck object     
    Dealer dealer = new Dealer();

    public Match(Player player) {
        this.begin(player);
    }

    public void begin(Player player) {
        
        Scanner scan = new Scanner(System.in);

        // Prints state of player and prompts user for a bet amount
        System.out.println("Welcome " + player.getPlayerName() + ", You have " + player.getPlayerWins() + " wins, " + player.getPlayerLoss()
                + " losses and currently have a balance of $" + player.getPlayerBalance());

       // playerBet = player.placeBet(); // sets bet to value returned by placeBet()
       player.placeBet();


        while (true) {

            // INITIAL DEAL of 2 cards p/player (dealer's second card ealth face down)
            for (int i = 0; i < 2; i++) {
                player.hit(myDeck.deal());
                dealer.hit(myDeck.deal());
            }

            // BLACKJACK CHECK
            if (player.getPlayerHand().isBlackJack() && dealer.getDealerHand().isBlackJack()) { // if both have blackjack

                printState(player, dealer);
                System.out.println("Tie - Game Over");
                player.setPlayerBalance(player.getPlayerBalance() + (player.getPlayerBet())); // return bet to player

                return;
            } else if (player.getPlayerHand().isBlackJack()) { // if player has blackjack

                printState(player, dealer);
                System.out.println("BlackJack, You Won!");
                player.setPlayerBalance(player.getPlayerBalance() + (player.getPlayerBet() * 2)); // pay player
                player.setPlayerWins(player.getPlayerWins() + 1);

                return;
            } else if (dealer.getDealerHand().isBlackJack()) { // if dealer has blackjack
                printState(player, dealer);
                System.out.println(dealer);
                System.out.println("Whoops, house got blackjack");
                player.setPlayerLoss(player.getPlayerLoss() + 1);

                return;
            } else {

                System.out.println("========================================");
                System.out.println(player);
                System.out.println("Dealer's hand: [" + dealer.getDealerHand().getHand().get(0) + ", HIDDEN]  (value: UNKNOWN)");
                System.out.println("=======================================");

                // USER'S TURN              
                if (player.play(dealer, player, myDeck)) // if player busts
                {
                    System.out.println("\n===============Dealer wins!============");
                    return;

                }

                printState(player, dealer);

                // DEALER'S TURN
                if (dealer.play(dealer, player, myDeck)) // if dealer busts
                {

                    System.out.println("\n===============" + player.getPlayerName() + " wins!==========");
                    player.setPlayerBalance(player.getPlayerBalance() + (player.getPlayerBet() * 1.5));
                    player.setPlayerWins(player.getPlayerWins() + 1);
                    return;

                }

                //Checks winning conditions
                if (dealer.getDealerHand().getHandValue() == player.getPlayerHand().getHandValue()) // tie
                {
                    System.out.println("\n======Tie!======");
                    player.setPlayerBalance(player.getPlayerBalance() + (player.getPlayerBet())); // return bet to player

                    return;
                } else if (dealer.getDealerHand().getHandValue() > player.getPlayerHand().getHandValue() && dealer.getDealerHand().getHandValue() <= 21) // dealer wins
                {
                    System.out.println("\n======Dealer wins!======");
                    player.setPlayerLoss(player.getPlayerLoss() + 1);

                    return;
                } else if (dealer.getDealerHand().getHandValue() < player.getPlayerHand().getHandValue() && player.getPlayerHand().getHandValue() <= 21)// player wins
                {
                    System.out.println("\n======" + player.getPlayerName() + " wins!======");
                    player.setPlayerBalance(player.getPlayerBalance() + (player.getPlayerBet() * 1.5));
                    player.setPlayerWins(player.getPlayerWins() + 1);

                    return;
                }
            }
           // break;
        }

    }

    public static void printState(Player player, Dealer dealer) {
        System.out.println("=================================================================");
        System.out.println(player);
        System.out.println(dealer);
        System.out.println("=================================================================");
    }

}
