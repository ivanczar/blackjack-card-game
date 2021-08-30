/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import blackjack.Match.EndGame;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Ivan
 */
public class Bet {

    EndGame end = new EndGame();
    static private double betAmount = 0;

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

        double playerBet = 0;
        do {

            try {
                System.out.print("Please enter a bet amount which is below or equal to your current balance: ");
                playerBet = scan.nextDouble();

            } catch (InputMismatchException e) {

                System.out.println("Input must be numerical");

            }
            scan.nextLine(); //clears buffer

        } while (playerBet > player.getPlayerBalance());
        System.out.println("IN PLACEBET before set: BET AMOUNT IS: " + this.getBetAmount());
        player.setPlayerBalance(player.getPlayerBalance() - playerBet); // removes bet amount from players balance
        setBetAmount(playerBet);
        System.out.println("IN PLACEBET after set: BET AMOUNT IS: " + this.getBetAmount());
    }

    public void settleBet(Player player) {
        Dealer dealer = new Dealer();

        
        switch (end.winner) {
            case 1:
                player.setPlayerBalance(player.getPlayerBalance() + (this.getBetAmount()));
                System.out.println("SETTLEBET TIE");
                break;
            
            case 3:
                System.out.println("IN SETTLEBET case 3: BET AMOUNT IS: " + this.getBetAmount());
                player.setPlayerBalance(player.getPlayerBalance() + ((this.getBetAmount() * 1.5)));
                System.out.println("SETTLEBET WIN");
                break;
            

        }

    }

}
