package blackjack;

/**
 *
 * @author ivanc
 */
public class Card {

    // member variables
    private Suit suit;
    private Value value;

    // constructor
    public Card(Suit suit, Value value) {
        setSuit(suit);
        setValue(value);
    }

    // getters and setters
    public Suit getSuit() {
        return this.suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public String toString() {
        return getValue() + " of " + getSuit();
    }

}
