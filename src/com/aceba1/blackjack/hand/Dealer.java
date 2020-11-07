package com.aceba1.blackjack.hand;

public class Dealer extends Hand { // Dealer always follows this predictable logic
  @Override
  public int turn(int iterNum) {
    if (getValue() > 17)
      return 2; // Stand on 17 and up
    return 1; // Hit on 16 and down
  }

  public String getName() {
    return "Dealer";
  }

  @Override
  public int bet() {
    return 0;
  }
}
