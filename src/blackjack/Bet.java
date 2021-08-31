/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Ivan
 */
public class Bet {

    static private double betAmount = 0; // static so accessible by all instances

    public Bet() {
        setBetAmount(0);
    }

    public double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(double betAmount) {
        this.betAmount = betAmount;
    }

    /**
     * returns and prompts user for bet amount, deducts amount from balance and
     * handles invalid input
     *
     * @return
     * @throws InputMismatchException
     */
    public void placeBet(Player player) throws InputMismatchException {
        Scanner scan = new Scanner(System.in);

        do {
            try {
                String playerBet = "0";
                System.out.print("Please enter a bet amount which is below or equal to your current balance: ");
                playerBet = scan.nextLine();

                if (playerBet.equalsIgnoreCase("q")) {
                    System.out.println("Exiting...");
                    System.exit(0);
                } else {
                    double numBet = Double.parseDouble(playerBet);

                    if (numBet > player.getPlayerBalance() || numBet < 0) {

                        System.err.println("Make sure bet is above 0 and below your balance amount: ");
                        playerBet = scan.nextLine();
                        if (playerBet.equalsIgnoreCase("q")) {
                            System.out.println("Exiting...");
                            System.exit(0);
                        }
                    }

                    setBetAmount(numBet);
                    player.setPlayerBalance(player.getPlayerBalance() - numBet); // removes bet amount from players balance
                    break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Input must be numerical ");

            }

        } while (true);

    }

    /**
     * Pays player depending on match outcome
     *
     * @param player
     */
    public void settleBet(Player player) {
        Dealer dealer = new Dealer();
        CheckWinner end = new CheckWinner();

        switch (end.winner) {
            case 1: //if tie return bet
                player.setPlayerBalance(player.getPlayerBalance() + (this.getBetAmount()));

                break;

            case 2:
                player.setPlayerBalance(player.getPlayerBalance() + ((this.getBetAmount() * 2)));
                break;

            case 3: // if player wins returns bet + 1.5xbet

                player.setPlayerBalance(player.getPlayerBalance() + (this.getBetAmount()));
                player.setPlayerBalance(player.getPlayerBalance() + ((this.getBetAmount() * 1.5)));

                break;

        }

    }

}
