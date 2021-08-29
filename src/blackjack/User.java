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
public interface User {

    public void hit(Card card);

    public int calcHandValue();

    public boolean play(Dealer dealer, Player player, Deck myDeck);

  

}
