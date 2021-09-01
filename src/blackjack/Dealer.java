package blackjack;

/**
 *
 * @author Ivan
 */
public final class Dealer implements User {

//SOLID preferes to use an interface instead
    // of inheritance but i need inheritance for marks
    private Hand dealerHand;
    private boolean dealerFinished;

    public Hand getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
    }

    public boolean getDealerFinished() {
        return dealerFinished;
    }

    public void setDealerFinished(boolean dealerFinished) {
        this.dealerFinished = dealerFinished;
    }

    public Dealer() {

        setDealerHand(new Hand());
        setDealerFinished(false);
    }

    /**
     * If allowed, adds card to dealers hand. Also corrects for ACE being 11 or
     * 1
     *
     * @param card
     */
    @Override
    public void hit(Card card) {

        if (this.calcHandValue() < 21) {
            if (this.calcHandValue() >= 11 && card.getValue().equals(Value.ACE)) {

                getDealerHand().setHandValue(getDealerHand().getHandValue() - 10); // Corrects for ACE being 11 or 1
                getDealerHand().add(card);
            } else {
                getDealerHand().add(card);
            }

        }
    }

    /**
     * game logic for dealer's turn
     *
     * @param dealer
     * @param player
     * @param myDeck
     */
    @Override
    public void play(Dealer dealer, Player player, Deck myDeck) {
        // DEALER TURN - CAN HIT IF HANDVALUE < 17   

        // dealer only hits if allowed and is beneficial (eg player has a higher handvalue than them)
        while ((dealer.calcHandValue() < 17)
                && (player.calcHandValue() > dealer.calcHandValue())){

            System.out.println("*DEALER HITS*");
            this.hit(myDeck.deal());
            System.out.println(dealer);
            

        }
        if (dealer.calcHandValue() > 21) {  //checks dealer bust          
            setDealerFinished(true);

        }

    }

    @Override
    public int calcHandValue() {
        return this.getDealerHand().getHandValue();
    }

    @Override
    public String toString() {

        return "Dealer's hand: " + getDealerHand();

    }

}
