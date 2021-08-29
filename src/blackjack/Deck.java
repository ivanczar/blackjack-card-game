package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 *
 * @author ivanc
 */
public class Deck {

    // membaer variables
    private ArrayList<Card> deck; //arraylist of card objects
    private final int deckCapacity; // capacity should not change

    //constructor
    public Deck(int capacity) {
        setDeck(new ArrayList<Card>());
        this.deckCapacity = capacity;

        // Populates deck with cards
        for (Suit s : Suit.values()) {
            for (Value v : Value.values()) {
                Card card = new Card(s, v); // creates card with suit and value
                this.deck.add(card); // adds created card to deck Arraylist
            }

        }

        this.shuffle(); // shuffle deck after it has been created
    }

    // getter and setters
    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public int getDeckCapacity() {
        return deckCapacity;
    }

//uses shuffle method from Collections library to shuffle the deck via a Random object
    public void shuffle() { 

        long seed = System.currentTimeMillis(); // ensures shuffle is random every time by using system time as seed
        Collections.shuffle(deck, new Random(seed)); 

    }
    // removes and return card from top of deck
    public Card deal() throws NoSuchElementException {
        if (!getDeck().isEmpty()) {
            return deck.remove(0); 
        } else {
            throw new NoSuchElementException("All cards have been dealt.");
        }
    }

    public String toString() { 
        return getDeck() + " ";
    }

}
