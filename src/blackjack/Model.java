package blackjack;

import java.util.Observable;

/**
 *
 * @author ivanc
 */
public class Model extends Observable {

    final int DECKCAPACITY = 52;
    // double playerBet = 0;

    CheckWinner checkWinner;
    Deck myDeck; // instantiates a deck object     
    Dealer dealer;
    CheckBJ checkBJ;
    Bet bet;

    public Model(Player player) {
        checkWinner = new CheckWinner();
        myDeck = new Deck(DECKCAPACITY);
        dealer = new Dealer();
        checkBJ = new CheckBJ();
        bet = new Bet();

        this.begin(player);
    }

    
    /**
     * Match logic
     * @param player 
     */
    public void begin(Player player) {
        Prompt prompt = new Prompt();

        // Prints state of player and prompts user for a bet amount
        System.out.println("Welcome " + player.getPlayerName() + ", You have " + player.getPlayerWins() + " wins, " + player.getPlayerLoss()
                + " losses and currently have a balance of $" + player.getPlayerBalance() + "\n");

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

            // DEALERS TURN if player has stood/not bust
            if ((player.calcHandValue() < 21) && !dealer.getDealerFinished()) {//is player hasnt bust

                prompt.printState(player, dealer);
                
                dealer.play(dealer, player, myDeck);
            }

        }

        checkWinner.winCondition(player, dealer);

        bet.settleBet(player);

    }

}
