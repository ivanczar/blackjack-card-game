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

    // getters and setters
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

    /**
     * adds a card to the players hand array and updates hand value member
     * variable
     *
     * @param card
     */
    public void add(Card card) {

        getHand().add(card);
        setHandValue(getHandValue() + card.getValue().getCardValue());

    }


    @Override
    public String toString() {
        return getHand() + " (value: " + getHandValue() + ")";
    }

}
