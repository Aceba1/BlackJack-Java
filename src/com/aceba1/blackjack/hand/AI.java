package com.aceba1.blackjack.hand;

public class AI implements Holder {
  @Override
  public void modifyFunds(int newFunds) {  }

  @Override
  public int getHandCount() {
    return 0;
  }

  @Override
  public Hand getHand(int index) {
    return null;
  }

  @Override
  public void addHand(Hand newHand) {

  }

  @Override
  public boolean insurance(Hand hand) {
    return false; // Never use insurance
  }

  @Override
  public int turn(Hand hand, int iterNum, int visibleDealerHand) {
    return 0;
  }

  @Override
  public void viewHand(Hand hand) { }

  @Override
  public void bet() {

  }

  @Override
  public String getName() {
    return "AI";
  }
}
