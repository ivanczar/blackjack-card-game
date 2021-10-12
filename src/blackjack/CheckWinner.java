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
public class CheckWinner {

    public static int getWinner() {
        return winner;
    }

    public static void setWinner(int aWinner) {
        winner = aWinner;
    }

    private static int winner = 0;

    /**
     * Determines who won and updates wins/losses
     *
     * @param player
     * @param dealer
     */
    public void winCondition(Player player, Dealer dealer) {

        //Checks winning conditions
        if (player.calcHandValue() == 21 && player.getPlayerHand().getHand().size() == 2) // if player natural blackjack
        {
            player.setPlayerWins(player.getPlayerWins() + 1);
            this.setWinner(2);
        }

        if (player.calcHandValue() > 21) // if player busts
        {
            System.out.println("*" + player.getPlayerName().toUpperCase() + " BUST!*");
            System.out.println("=======Dealer wins=========");
            player.setPlayerLoss(player.getPlayerLoss() + 1);
            this.setWinner(4);
        }
        if (dealer.calcHandValue() > 21) // if dealer busts
        {
            System.out.println("*DEALER BUST!*");
            System.out.println("=======" + player.getPlayerName().toUpperCase() + " wins!========");
            player.setPlayerWins(player.getPlayerWins() + 1);
            this.setWinner(3);
        }

        if (dealer.getDealerHand().getHandValue() > player.getPlayerHand().getHandValue() && dealer.getDealerHand().getHandValue() <= 21) // dealer natural bj or higher hand value
        {
            System.out.println("\n======Dealer wins!======");
            player.setPlayerLoss(player.getPlayerLoss() + 1);
            this.setWinner(4);

        }
        if (dealer.getDealerHand().getHandValue() < player.getPlayerHand().getHandValue() && player.getPlayerHand().getHandValue() <= 21)// player wins
        {
            System.out.println("\n======" + player.getPlayerName() + " wins!======");

            player.setPlayerWins(player.getPlayerWins() + 1);

            this.setWinner(3);
        }
        if (dealer.getDealerHand().getHandValue() == player.getPlayerHand().getHandValue()) // tie
        {
            System.out.println("\n======Tie!======");

            this.setWinner(1);
        }

    }

}
