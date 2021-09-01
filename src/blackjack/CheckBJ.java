/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

/**
 *
 * @author Ivan
 */
public class CheckBJ {

    // returns true if hand is blackjack (i.e handvalue ==true)
    public boolean isBlackJack(User user) {
        return user.calcHandValue() == 21;
    }

    /**
     * Checks if player gets natural blackjack (Ace & 10)
     * @param player
     * @param dealer 
     */
    public void checkBlackJack(Player player, Dealer dealer) {
        // BLACKJACK CHECK
        Prompt prompt = new Prompt();
        if (this.isBlackJack(player) && this.isBlackJack(dealer)) { // if both have blackjack

            System.out.println("Tie - Game Over");
            dealer.setDealerFinished(true);
            player.setPlayerFinished(true);

        } else if (this.isBlackJack(player)) { // if player has blackjack

            prompt.printState(player, dealer);

            System.out.println("*" + player.getPlayerName().toUpperCase() + " BLACKJACK!*");
            player.setPlayerFinished(true);

        } else if (this.isBlackJack(dealer)) {

            prompt.printState(player, dealer);

            System.out.println("*DEALER BLACKJACK!*");
            dealer.setDealerFinished(true);

        }

    }
}
