package blackjack;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author ivanc
 */
public class Match {

    final int DECKCAPACITY = 52;
    double initialBet = 0;

    Deck myDeck = new Deck(DECKCAPACITY); // instantiates a deck object     
    Dealer dealer = new Dealer();

    public Match(Player player) {
        this.begin(player);
    }

    public void begin(Player player) {

        // Prints state of player and prompts user for a bet amount
        System.out.println("Welcome " + player.getPlayerName() + ", You have " + player.getPlayerWins() + " wins, " + player.getPlayerLoss()
                + " losses and currently have a balance of $" + player.getPlayerBalance());
        
        initialBet = player.placeBet();

        Scanner scan = new Scanner(System.in);

        while (true) {

            // INITIAL DEAL of 2 cards p/player (dealer's second card ealth face down)
            for (int i = 0; i < 2; i++) {
                player.hit(myDeck.deal());
                dealer.hit(myDeck.deal());
            }

            // BLACKJACK CHECK
            if (player.getPlayerHand().isBlackJack() && dealer.getDealerHand().isBlackJack()) {

                printState(player, dealer);
                System.out.println("Tie - Game Over");
                player.setPlayerBalance(player.getPlayerBalance() + (initialBet)); // return bet to player

                return;

            } else if (player.getPlayerHand().isBlackJack()) { // if player has blackjack

                printState(player, dealer);
                System.out.println("BlackJack, You Won!");
                player.setPlayerBalance(player.getPlayerBalance() + (initialBet * 2)); // pay player
                player.setPlayerWins(player.getPlayerWins() + 1);

                return;
            } else if (dealer.getDealerHand().isBlackJack()) { // if dealer has blackjack
                printState(player, dealer);
                System.out.println(dealer);
                System.out.println("Whoops, house got blackjack");
                player.setPlayerLoss(player.getPlayerLoss() + 1);

                return;

            } else {
                    player.hit(myDeck.deal());
                    System.out.println("========================================");
                    System.out.println(player);
                    System.out.println("Dealer's hand: [" + dealer.getDealerHand().getHand().get(0) + ", HIDDEN]  (value: UNKNOWN)");
                    System.out.println("=======================================");


                // USER'S TURN TO HIT/STAND              
                
                if (player.play(dealer, player, myDeck)) // if player busts
                {
                    System.out.println("Player Bust!");
                    System.out.println("\n===============Dealer wins!============");
                    player.setPlayerLoss(player.getPlayerLoss() + 1);
                    return;
                   
                }

                printState(player,dealer);

                // Dealers turn
                if (dealer.play(dealer, player, myDeck)) // if dealer busts
                {
                    System.out.println("Dealer Bust!");
                    System.out.println("\n==============="+ player.getPlayerName() + " wins!==========");
                    player.setPlayerBalance(player.getPlayerBalance() + (initialBet * 1.5));
                    player.setPlayerWins(player.getPlayerWins() + 1);
                    return;
                    
                }
            }

        }
        
    }
    
    public static void printState(Player player, Dealer dealer)
    {
            System.out.println("=================================================================");
            System.out.println(player);
            System.out.println(dealer);
            System.out.println("=================================================================");
    }
}

        // CHECK WINNING CONDITIONS
        // HOW TO DIRECT ALL POSSIBLE GAME ENDINGS TO EXECUTE THIS PART or should i add/subtract at every possible ending
//        if (dealer.getDealerHand().getHandValue() == player.getPlayerHand().getHandValue()) // tie
//        {
//            System.out.println("\n======Tie!======");
//            player.setPlayerBalance(player.getPlayerBalance() + (initialBet)); // return bet to player
//
//            return;
//        } else if (dealer.getDealerHand().getHandValue() > player.getPlayerHand().getHandValue() && dealer.getDealerHand().getHandValue() <= 21) // dealer wins
//        {
//            System.out.println("\n======Dealer wins!======");
//            player.setPlayerLoss(player.getPlayerLoss() + 1);
//
//            return;
//        } else if (dealer.getDealerHand().getHandValue() < player.getPlayerHand().getHandValue() && player.getPlayerHand().getHandValue() <= 21)// player wins
//        {
//            System.out.println("\n======" + player.getPlayerName() + " wins!======");
//            player.setPlayerBalance(player.getPlayerBalance() + (initialBet * 1.5));
//            player.setPlayerWins(player.getPlayerWins() + 1);
//
//            return;
//        }
//    }



