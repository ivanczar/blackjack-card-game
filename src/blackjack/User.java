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
// NOTE TO SELF: Implementing classes must implement ALL of these methods
public interface User {

    public void hit(Card card);

    public int calcHandValue();

    public void play(Dealer dealer, Player player, Deck myDeck);

}
