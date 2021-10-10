/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.InputMismatchException;


/**
 *
 * @author Ivan
 */
public class Bet {

    static private double betAmount = 0; // static so accessible by all instances

//    public Bet() {
//        setBetAmount(0);
//    }
    public double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(double betAmount) {
        this.betAmount = betAmount;
    }

    /**
     * prompts user for bet amount, deducts amount from balance and
     * handles invalid input
     *
     * @param player
     * @throws InputMismatchException
     */
    public void placeBet(Player player, Double betAmount) throws InputMismatchException {

      //  Prompt prompt = new Prompt();

       // double numBet = prompt.betPrompt(player);

        setBetAmount(betAmount);
        player.setPlayerBalance(player.getPlayerBalance() - betAmount); // removes bet amount from players balance
    }

    /**
     * Pays player depending on match outcome
     *
     * @param player
     */
    public void settleBet(Player player) {
      
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
