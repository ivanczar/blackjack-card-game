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
public class BJDriver {

    public static void main(String[] args) {

        System.out.println("Rules: The goal of blackjack is to beat the dealer's hand without going over 21.\n"
                + "Face cards are worth 10. Aces are worth 1 or 11, whichever makes a better hand.\n"
                + "Each player starts with two cards, one of the dealer's cards is hidden until the end.\n"
                + "To 'Hit' is to ask for another card. To 'Stand' is to hold your total and end your turn.\n"
                + "If you go over 21 you bust, and the dealer wins regardless of the dealer's hand.\n"
                + "If you are dealt 21 from the start (Ace & 10), you got a blackjack.\n"
                + "Blackjack means you win 2x the amount of your bet. Winning without blackjack is 1.5x.\n"
                + "Dealer will hit until their cards total 17 or higher.");

        String choice = "y";
        Account acc = new Account();
        Scanner scan = new Scanner(System.in);
        System.out.print("\n\nWelcome to BlackJack! Please enter your name "
                + "(case sensitive) to access your account. Otherwise a new "
                         + "account under this name will be created for you: ");
        String userName = scan.next();
        Player player = acc.checkPlayer(userName);
        
        Match match = new Match(player);
        acc.updateBalance(player);

    }

}
