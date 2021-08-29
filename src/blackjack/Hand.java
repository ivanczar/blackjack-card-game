package blackjack;

import java.util.ArrayList;

/**
 *
 * @author ivanc
 */
public class Hand {

    // member variables
    private ArrayList<Card> hand; // stores an arraylist of cards
    private int handValue; // keeps track of the hand value

    //constructor
    public Hand() {
        setHand(new ArrayList());
        setHandValue(0);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public int getHandValue() {
        return handValue;
    }

    public void setHandValue(int handValue) {
        this.handValue = handValue;
    }

    public void add(Card card) //adds a card to the players hand array
    {

        getHand().add(card);
        setHandValue(getHandValue() + card.getValue().getCardValue());

    }

    public int calcHandValue() { // IS RETURN TYPE CORRECT???????

        int count = 0;

        for (Card c : getHand()) {

            count += c.getValue().getCardValue(); // gets int value of enum

        }
        return count;
    }

    public boolean isBlackJack() {
        if (getHandValue() != 21) {
            return false;
        } else {
            return true;
        }
    }

    public void printHandValue() {
        System.out.println("Hand Value is: " + this.getHandValue());
    }

    public String toString() { //prints array
        return getHand() + "";
    }

}
