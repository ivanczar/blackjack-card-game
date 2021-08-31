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

    public void playerState(Player player, Dealer dealer, Deck myDeck) {
        
        System.out.println("========================================");
        System.out.println(player);
        System.out.println("Dealer's hand: [" + dealer.getDealerHand().getHand().get(0) + ", HIDDEN]  (value: UNKNOWN)");
        System.out.println("=======================================");
    }

    public int playerPlayPrompt() {
        Scanner scan = new Scanner(System.in);
        // prompts user for action and catches invalid input
        do {
            try {
                String choice = "0";
                System.out.print("Hit(1) or Stand(2)?: ");
                choice = scan.nextLine();

                if (choice.equalsIgnoreCase("q")) {
                    System.out.println("Exiting...");
                    System.exit(0);
                } else {

                    
                    if (!((Integer.valueOf(choice) == 1 )|| (Integer.valueOf(choice) == 2))) {

                        System.out.println("That is not an option!: ");
                        choice = scan.nextLine();
                        if (choice.equalsIgnoreCase("q")) {
                            System.out.println("Exiting...");
                            System.exit(0);
                        }

                    }
                    Integer numChoice = Integer.parseInt(choice);

                    return numChoice;

                }
            } catch (NumberFormatException e) {
                System.out.println("Input must be numerical ");

            }

        } while (true);
    }

}
