package blackjack;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author ivanc
 */
public class Player implements User {

    // memeabr variables
    private String playerName;
    private double playerBalance;
    private Hand playerHand;
    private int playerWins;
    private int playerLoss;
    private boolean playerFinished;

    private double playerBet;

    public Player(String userName, double balance, int wins, int losses) {
        setPlayerHand(new Hand());
        setPlayerName(userName);
        setPlayerBalance(balance);
        setPlayerWins(wins);
        setPlayerLoss(losses);
        setPlayerFinished(false);

    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String userName) {
        this.playerName = userName;
    }

    public double getPlayerBalance() {
        return playerBalance;
    }

    public void setPlayerBalance(double balance) {
        this.playerBalance = balance;
    }

    public int getPlayerWins() {
        return playerWins;
    }

    public void setPlayerWins(int playerWins) {
        this.playerWins = playerWins;
    }

    public int getPlayerLoss() {
        return playerLoss;
    }

    public void setPlayerLoss(int playerLoss) {
        this.playerLoss = playerLoss;
    }

    public boolean getPlayerFinished() {
        return playerFinished;
    }

    public void setPlayerFinished(boolean playerFinished) {
        this.playerFinished = playerFinished;
    }

    @Override
    public int calcHandValue() {
        return this.getPlayerHand().getHandValue();
    }

    /**
     * If allowed, adds card to player hand. Also corrects for ACE being 11 or 1
     *
     * @param card
     */
    @Override
    public void hit(Card card) {

        if (this.calcHandValue() < 21) {
            if (this.calcHandValue() >= 11 && card.getValue().equals(Value.ACE)) {

                playerHand.setHandValue(playerHand.getHandValue() - 10); // Corrects for ACE being 11 or 1
                playerHand.add(card);
            } else {
                playerHand.add(card);
            }

        }
    }

    /**
     * game logic for player's turn
     *
     * @param dealer
     * @param player
     * @param myDeck
     * @return
     *
     */
    @Override
    public void play(Dealer dealer, Player player, Deck myDeck) {

        Prompt prompt = new Prompt();

        prompt.playerState(player, dealer, myDeck);

        int choice = prompt.playerPlayPrompt();

        while (choice != 2 && player.calcHandValue() <= 21) {
            player.hit(myDeck.deal()); //deal a card
            prompt.playerState(player, dealer, myDeck);

            if (player.calcHandValue() > 21) { // if player has bust

                setPlayerFinished(true);

                return;
            } else {

                choice = prompt.playerPlayPrompt();;

            }

        }

        System.out.println(player.getPlayerName() + " stands!");
        setPlayerFinished(true);
        return;
    }

    @Override
    public String toString() {

        return getPlayerName() + "'s hand: " + getPlayerHand();

    }

}
