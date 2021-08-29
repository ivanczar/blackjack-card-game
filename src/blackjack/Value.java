package blackjack;

/**
 *
 * @author ivanc
 */
/**
 * keeps card value as word and associated numerical value
 *
 * @author Ivan
 */
public enum Value {

    ACE(11),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10);

    private int cardValue = 0;

    private Value(int value) {
        this.cardValue = value;
    }

    /**
     * returns numerical value of card
     *
     * @return
     */
    public int getCardValue() {
        return this.cardValue;
    }

}
