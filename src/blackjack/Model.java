package blackjack;

import java.util.Observable;

/**
 *
 * @author ivanc
 */
public class Model extends Observable {

    final int DECKCAPACITY = 52;

    Deck myDeck; // instantiates a deck object  
    Player player;
    Dealer dealer;
    Bet bet;
    CheckBJ checkBJ;
    CheckWinner checkWinner;

    Database db = new Database();

    public Model() {
        checkWinner = new CheckWinner();
        myDeck = new Deck(DECKCAPACITY);
        dealer = new Dealer();
        checkBJ = new CheckBJ();
        bet = new Bet();
        db.dbsetup();

    }

    public void checkName(String playerName, String password) {

        this.player = db.checkName(playerName, password);

        System.out.println(player.getPlayerBalance());
        this.setChanged();
        this.notifyObservers(this.player);

    }

    public void placeBet(Player player, Double betAmount) {
//        if (player.isLoginFlag() && betAmount <= player.getPlayerBalance()) {
        System.out.println("Bet placed");
        this.bet.placeBet(player, betAmount);

        this.setChanged();
        this.notifyObservers(this.player);
//        }

    }

    public void initialDeal() {
        System.out.println("Dealing initial caRds...");

        if (!(player.getPlayerFinished() || dealer.getDealerFinished())) {

            // INITIAL DEAL of 2 cards p/player (dealer's second card ealth face down)
            for (int i = 0; i < 2; i++) {
                player.hit(myDeck.deal());
                dealer.hit(myDeck.deal());
            }
        }
        System.out.println(dealer);
        System.out.println(player);
        checkBJ.checkBlackJack(player, dealer);
        this.setChanged();
        this.notifyObservers(this.player);
    }

    public void playerHit() {
        if (player.calcHandValue() <= 21) {
            player.hit(myDeck.deal());
        }
        if (player.calcHandValue() > 21) {
            player.setPlayerFinished(true);
        }
        System.out.println(dealer);
        System.out.println(player);
        this.setChanged();
        this.notifyObservers(this.player);
    }

    public void playerStand() {
        player.setPlayerFinished(true);
        this.setChanged();
        this.notifyObservers(this.player);
    }

    /**
     * Match logic
     *
     * @param player
     */
    public void begin(String playerName, String password) {

        //  Prompt prompt = new Prompt();
        // Prints state of player and prompts user for a bet amount
//        System.out.println("Welcome " + player.getPlayerName() + ", You have " + player.getPlayerWins() + " wins, " + player.getPlayerLoss()
//                + " losses and currently have a balance of $" + player.getPlayerBalance() + "\n");
        // USER'S TURN   
        if ((!(player.getPlayerFinished() || dealer.getDealerFinished()))) {

            player.play(dealer, player, myDeck);

        }

        // DEALERS TURN if player has stood/not bust
        if ((player.calcHandValue() < 21) && !dealer.getDealerFinished()) {//is player hasnt bust

            //prompt.printState(player, dealer);
            dealer.play(dealer, player, myDeck);
        }
        checkWinner.winCondition(player, dealer);

        bet.settleBet(player);
    }

}
