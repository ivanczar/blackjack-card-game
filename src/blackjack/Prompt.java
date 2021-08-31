/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.Scanner;

/**
 *
 * @author Ivan
 */
public class Prompt {

    public double betPrompt(Player player) {

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

                    if (Double.valueOf(playerBet) > player.getPlayerBalance() || Double.valueOf(playerBet) < 0) {

                        System.out.println("Make sure bet is above 0 and below your balance amount: ");
                        playerBet = scan.nextLine();
//                        if (playerBet.equalsIgnoreCase("q")) {
//                            System.out.println("Exiting...");
//                            System.exit(0);
//                        }

                    }
                    Double numBet = Double.parseDouble(playerBet);
                    return numBet;

                }
            } catch (NumberFormatException e) {
                System.out.println("Input must be numerical ");

            }

        } while (true);

    }

    public int playerPlayPrompt() {
        int choice = 0;

        return choice;
    }

}
