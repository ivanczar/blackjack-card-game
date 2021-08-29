package blackjack;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author ivanc
 */
public class Player implements User {

    private String playerName;
    private double playerBalance;
    private Hand playerHand;
    private int playerWins;
    private int playerLoss;

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

    public Player(String userName, double balance, int wins, int losses) {
        setPlayerHand(new Hand());
        setPlayerName(userName);
        setPlayerBalance(balance);
        setPlayerWins(wins);
        setPlayerLoss(losses);
    }

    @Override
    public int calcHandValue() {
        return this.getPlayerHand().getHandValue();
    }

    // Methods
    public void stand() {

        System.out.println(getPlayerName() + "===============================");
        return;

    }

    @Override
    public void hit(Card card) {

        if (this.calcHandValue() >= 11 && card.getValue().equals(Value.ACE)) {

            playerHand.setHandValue(playerHand.getHandValue() - 10); // Corrects for ACE being 11 or 1
            playerHand.add(card);

        } else if (this.calcHandValue() < 21) {
            playerHand.add(card);
        }

    }

    public double placeBet() throws InputMismatchException {
        Scanner scan = new Scanner(System.in);
        double bet = 0;

        do {

            try {
                System.out.print("Please enter a bet amount which is below your current balance: ");
                bet = scan.nextDouble();

            } catch (InputMismatchException e) {
                scan.nextLine();

            }

        } while (bet > this.getPlayerBalance());

        this.setPlayerBalance(this.getPlayerBalance() - bet); // removes bet amount from balance
        return bet;
    }

    @Override
    public boolean play(Dealer dealer, Player player, Deck myDeck) throws InputMismatchException { //have initialBet set somewhere else to improve abstraction
        Scanner scan = new Scanner(System.in);
        
        int choice = 0;
        
        while (!(choice == 1 || choice == 2)) {
            System.out.println("Type 1 to Hit or 2 to Stand: ");
            choice = scan.nextInt();
        }


        boolean isBust = false;

        do {

            if (this.calcHandValue() < 21) {

                if (choice == 1) {

                    player.hit(myDeck.deal());
                    System.out.println("========================================");
                    System.out.println(player);
                    System.out.println(dealer);
                    System.out.println("=======================================");

                    if (this.calcHandValue() > 21) {
                        player.setPlayerLoss(player.getPlayerLoss() + 1); // increase loss
                        System.out.println(this.getPlayerName() + " Bust!");
                        isBust = true;
                        return isBust;
                    } else {
                        System.out.println("Hit(1) or Stand(2)?");
                        choice = scan.nextInt();
                        while (!(choice == 1 || choice == 2)) {
                            System.out.println("Type 1 to Hit or 2 to Stand: ");
                            choice = scan.nextInt();
                        }
                        
                    }
                }
            }

        } while (choice != 2);

        player.stand();
        return isBust;
    }

    @Override
    public String toString() {

        return getPlayerName() + "'s hand: " + getPlayerHand() + "(value: "
                + this.calcHandValue() + ")";

    }

}
