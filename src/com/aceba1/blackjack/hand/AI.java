package com.aceba1.blackjack.hand;

import java.util.ArrayList;
import java.util.List;

public class AI implements Holder {
  final List<Hand> hands = new ArrayList<>();

  @Override
  public void modifyFunds(int newFunds) {  }

  @Override
  public int getHandCount() {
    return hands.size();
  }

  @Override
  public Hand getHand(int index) {
    return hands.get(index);
  }

  @Override
  public void addHand(Hand newHand) {
    hands.add(newHand);
  }

  @Override
  public boolean insurance(Hand hand) {
    return false; // Never use insurance
  }

  @Override
  public int turn(Hand hand, int iterNum, int dealerVisible) {
    if (hand.canSplit())
      return AIStrategyMap.getPairChoice(hand.peekFaceValue(0), dealerVisible);

    int value = hand.getValue();

    if (hand.isSoft()) // Has any act card
      return AIStrategyMap.getSoftChoice(value, dealerVisible);
    else
      return AIStrategyMap.getHardChoice(value, dealerVisible);
  }

  @Override
  public void viewHand(Hand hand) { }

  @Override
  public void bet() {
    hands.clear();
    hands.add(new Hand(100));
  }

  @Override
  public String getName() {
    return "AI";
  }
}
