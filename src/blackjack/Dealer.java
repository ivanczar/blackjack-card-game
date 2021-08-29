package blackjack;

/**
 *
 * @author Ivan
 */
public final class Dealer implements User { //SOLID preferes to use an interface instead
    // of inheritance but i need inheritance for marks

    private Hand dealerHand;

    public Hand getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
    }

    public Dealer() {

        setDealerHand(new Hand());
    }

    /**
     * If allowed, adds card to dealers hand. Also corrects for ACE being 11 or 1
     * @param card 
     */
    public void hit(Card card) {

        if (this.calcHandValue() < 21) {
            if (this.calcHandValue() >= 11 && card.getValue().equals(Value.ACE)) {

                dealerHand.setHandValue(dealerHand.getHandValue() - 10); // Corrects for ACE being 11 or 1
                dealerHand.add(card);
            } else {
                dealerHand.add(card);
            }

        }
    }

    /**
     * game logic for dealer's turn
     *
     * @param dealer
     * @param player
     * @param myDeck
     * @return
     */
    public boolean play(Dealer dealer, Player player, Deck myDeck) {
        // DEALER TURN - CAN HIT IF HANDVALUE < 17   
        boolean isBust = false;

        // dealer only hits if allowed and is beneficial (i.e player has a higher handvalue than them)
        while ((dealer.calcHandValue() <= 17)
                && (player.calcHandValue() > dealer.calcHandValue())
                || (player.calcHandValue() - dealer.calcHandValue()) == 1) {

            System.out.println("*Dealer Hit*");
            this.hit(myDeck.deal());
            System.out.println(dealer);

        }
        if (dealer.calcHandValue() > 21) {
            System.out.println("*Dealer Bust!*");
            isBust = true;

        }

        return isBust;
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
