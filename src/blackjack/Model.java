package blackjack;

import java.util.Observable;

/**
 *
 * @author ivanc
 */
public class Model extends Observable {

    final int DECKCAPACITY = 52;

    Deck myDeck; // instantiates a deck object  
//    Player player;
//    Dealer dealer;
    Data data = new Data();
    Bet bet;
    CheckBJ checkBJ;
    CheckWinner checkWinner;

    Database db = new Database();

    public Model() {
        checkWinner = new CheckWinner();
        myDeck = new Deck(DECKCAPACITY);
//        dealer = new Dealer();
        checkBJ = new CheckBJ();
        bet = new Bet();
        db.dbsetup();
        data.dealer = new Dealer();

    }

    /**
     * calls database checkname, which returns a player object which is then
     * assigned to this data player. Also calls populate info and leader table
     * in database and assigns return values to respective data fields.
     *
     * @param playerName
     * @param password
     */
    public void checkName(String playerName, String password) {

        System.out.println("MODEL CHECKNAME CALLED");
        this.data.player = db.checkName(playerName, password);

        data.rules = db.getRules();
//        data.leaderBoard = db.getLeaderBoard();

        data.credits = db.getCredits();

        this.setChanged();
        this.notifyObservers(this.data);

    }

    /**
     * sets bet value of bet object instance by calling the bet placeBet method,
     *
     * @param player
     * @param betAmount
     */
    public void placeBet(Player player, Double betAmount) {

        System.out.println("Bet placed");
        this.bet.placeBet(player, betAmount);

        this.setChanged();
        this.notifyObservers(this.data);
//        }

    }

    /**
     * Deals initial 2 cards for each player and checks for BlackJack condition
     */
    public void initialDeal() {
        System.out.println("Dealing initial caRds...");

        if (!(data.player.getPlayerFinished() || data.dealer.getDealerFinished())) {

            // INITIAL DEAL of 2 cards p/player (dealer's second card ealth face down)
            for (int i = 0; i < 2; i++) {
                data.player.hit(myDeck.deal());
                data.dealer.hit(myDeck.deal());
            }
        }
        System.out.println(data.dealer);
        System.out.println(data.player);
        checkBJ.checkBlackJack(data.player, data.dealer);
        if (data.player.isHasWon() || data.dealer.isHasWon() == true) { //if anyone has blackjack 
            checkWin();
        }
        this.setChanged();
        this.notifyObservers(this.data);
    }

    /**
     * Calls player hit method and calls checkWin and sets isBust=true if player
     * busts
     */
    public void playerHit() {
        Card c = null;
        if (data.player.calcHandValue() < 21) {

            data.player.hit(myDeck.deal());

        }
        if (data.player.calcHandValue() >= 21) {
            data.player.setPlayerFinished(true);
            data.player.isBust = true;
            this.setChanged();
            this.notifyObservers(this.data);
            checkWin();

        }
        System.out.println(data.dealer);
        System.out.println(data.player);
        this.setChanged();
        this.notifyObservers(this.data);

    }

    /**
     * sets player instance variables to stand status. Calls dealerPlay if
     * player has not yet bust
     */
    public void playerStand() {
        data.player.setPlayerFinished(true);
        data.player.hasStand = true;
        if (data.player.isBust == false) { // dealer plays only if player has stood (not bust)
            dealerPlay();
        }

        this.setChanged();
        this.notifyObservers(this.data);
    }

    /**
     * calls dealer hit method while allowed by rules, checks if dealer busts.
     */
    public void dealerPlay() {
        if ((data.player.calcHandValue() < 21) && data.player.getPlayerFinished() == true) {
            // DEALER TURN - CAN HIT IF HANDVALUE < 17   

            // dealer only hits if allowed and is beneficial (eg player has a higher handvalue than them)
            while ((data.dealer.calcHandValue() < 17)
                    && (data.player.calcHandValue() > data.dealer.calcHandValue())) {

                System.out.println("*DEALER HITS*");
                data.dealer.hit(myDeck.deal());
                this.setChanged();
                this.notifyObservers(this.data);

            }
            if (data.dealer.calcHandValue() > 21) {  //checks dealer bust          
                data.dealer.setDealerFinished(true);
                this.setChanged();
                this.notifyObservers(this.data);
            }
        }
        data.dealer.setDealerFinished(true);

        checkWin();

    }

    /**
     * Sets data winner instance variable to return value of CheckWinner
     * winCondition()
     */
    public void checkWin() {
        checkWinner.winCondition(data.player, data.dealer);
        data.winner = checkWinner.getWinner();

        switch (data.winner) {
            case (1):
                System.out.println("WINNER 1");
                break;
            case (2):
                System.out.println("WINNER 2");
                break;
            case (3):
                System.out.println("WINNER 3");
                break;
            case (4):
                System.out.println("WINNER 3");
                break;

        }
        settleBets();

        this.setChanged();
        this.notifyObservers(this.data);
    }

    /**
     * Calls the settleBet method of the Bet instance
     */
    public void settleBets() {

        this.bet.settleBet(data.player, data.winner);
        this.setChanged();
        this.notifyObservers(this.data); // update balance label

    }

    /**
     * Calls database quitGame(). sets data quitFlag to true
     */
    public void quitGame() {
        
        this.db.quitGame(data.player.getPlayerName(), data.player.getPlayerBalance(), data.player.getPlayerWins(), data.player.getPlayerLoss());
        data.leaderBoard = db.getLeaderBoard();
        this.data.quitFlag = true; // Mark quitFlag as false.

        this.setChanged();
        this.notifyObservers(this.data);

    }

    /**
     * Re-initializes objects and resets variables to default. Player object
     * remains
     */
    public void resetGame() {

//         db= new Database();
        new Model();
        data.player.setPlayerHand(new Hand());
        data.player.hasStand = false;
        data.player.setPlayerFinished(false);
        myDeck = new Deck(DECKCAPACITY);
        data.resetFlag = true;
        data.winner = 0;
        data.player.setHasWon(false);
        data.player.isBust = false;

        data.dealer = new Dealer();
        checkBJ = new CheckBJ();
        bet = new Bet();
        checkWinner = new CheckWinner();

        this.setChanged();
        this.notifyObservers(this.data); // update balance label

    }

    /**
     * Calls databse quitGame() re-initalizes objects and resets variables to
     * defAult, including player object
     */
    public void logout() {
        db.quitGame(data.player.getPlayerName(), data.player.getPlayerBalance(), data.player.getPlayerWins(), data.player.getPlayerLoss());
        db = new Database();
        new Model();
        data.player.setPlayerHand(new Hand());
        data.player.hasStand = false;
        data.player.setPlayerFinished(false);
        myDeck = new Deck(DECKCAPACITY);
        data.resetFlag = true;
        data.winner = 0;
        data.player.setHasWon(false);
        data.player.isBust = false;

        data.dealer = new Dealer();
        checkBJ = new CheckBJ();
        bet = new Bet();
        checkWinner = new CheckWinner();

    }

}
