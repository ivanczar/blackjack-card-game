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

    
    public void hit(Card card) {

        if (dealerHand.getHandValue() >= 11 && card.getValue().equals(Value.ACE)) {

            dealerHand.setHandValue(dealerHand.getHandValue() - 10); // Corrects for ACE being 11 or 1
            dealerHand.add(card);

        } else if (dealerHand.getHandValue() < 21) {
            dealerHand.add(card);
        }

    }

    public boolean play(Dealer dealer, Player player, Deck myDeck) {
        // DEALER TURN - CAN HIT IF HANDVALUE < 17   
        boolean isBust =false;
        if (dealer.getDealerHand().getHandValue() >= 17) {

            return true;
        }
       // if (dealer.getDealerHand().getHandValue() < 17  {
            // dealer only hits if allowed and is beneficial (i.e player has a higher handvalue than them)
            while ((dealer.getDealerHand().getHandValue() <= 17) &&
                (player.getPlayerHand().getHandValue() > dealer.getDealerHand().getHandValue()) 
                   ||(player.getPlayerHand().getHandValue() - dealer.getDealerHand().getHandValue()) ==1) {

                System.out.println("*Dealer Hit*");
                this.hit(myDeck.deal());
                System.out.println(dealer);

            }
                if (this.getDealerHand().getHandValue() > 21) {
                    System.out.println("*Dealer Bust!*");
                    isBust=true;
                    

                    
                }
    
            System.out.println(this);
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
