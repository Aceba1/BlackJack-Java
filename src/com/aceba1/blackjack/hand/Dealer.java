package com.aceba1.blackjack.hand;

public class Dealer implements Holder { // Dealer always follows this predictable logic

  final Hand hand = new Hand(100); // Only one hand
  final boolean isDummy; // Changes display name

  public Dealer(boolean dummy) {
    this.isDummy = dummy;
  }

  @Override
  public void viewHand(Hand hand) { }

  @Override
  public int turn(Hand hand, int iterNum, int publicDealerHand) {
    if (hand.getValue() >= 17)
      return 2; // Stand on 17 and up
    return 1; // Hit on 16 and down
  }

  @Override
  public String getName() {
    return isDummy ? "Dealer AI" : "Dealer";
  }

  @Override
  public void bet() {
    // Already initialized
  }

  @Override
  public Hand getHand(int none) {
    return hand;
  }

  @Override
  public void modifyFunds(int newFunds) { }

  @Override
  public int getHandCount() {
    return 1;
  }

  @Override
  public void addHand(Hand newHand) { }

  @Override
  public boolean insurance(Hand hand) {
    return false; // what why would you insure against yourself
  }
}
