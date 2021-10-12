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

    private int winner = 0;
;
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
        if (player.isHasWon() || dealer.isHasWon() == true) { //if anyone has blackjack 
            checkWin();
        }
        this.setChanged();
        this.notifyObservers(this.player);
    }

    public void playerHit() {
        Card c = null;
        if (player.calcHandValue() <= 21) {
            c = myDeck.deal();
            player.hit(c);

        }
        if (player.calcHandValue() > 21) {
            player.setPlayerFinished(true);
            dealerPlay();
        }
        System.out.println(dealer);
        System.out.println(player);
        this.setChanged();
        this.notifyObservers(this.player);
    }

    public void playerStand() {
        player.setPlayerFinished(true);
        dealerPlay();
        this.setChanged();
        this.notifyObservers(this.player);
    }

    public void dealerPlay() {
        if ((player.calcHandValue() < 21) && !dealer.getDealerFinished()) {
            // DEALER TURN - CAN HIT IF HANDVALUE < 17   

            // dealer only hits if allowed and is beneficial (eg player has a higher handvalue than them)
            while ((dealer.calcHandValue() < 17)
                    && (player.calcHandValue() > dealer.calcHandValue())) {

                System.out.println("*DEALER HITS*");
                dealer.hit(myDeck.deal());
                System.out.println(dealer);

            }
            if (dealer.calcHandValue() > 21) {  //checks dealer bust          
                dealer.setDealerFinished(true);

            }
        }

        checkWin();

        this.setChanged();
        this.notifyObservers(this.player);

    }

    public void checkWin() {
        checkWinner.winCondition(player, dealer);
        this.winner = checkWinner.getWinner();

        switch (winner) {
            case (1):
                System.out.println("1");
                break;
            case (2):
                System.out.println("2");
                break;
            case (3):
                System.out.println("3");
                break;

        }
        setBet();
    }

    public void setBet() {

        this.bet.settleBet(player, winner);
//        this.setChanged();
//        this.notifyObservers(this.player); // update balance label
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
//        bet.settleBet(player);
    }

}
