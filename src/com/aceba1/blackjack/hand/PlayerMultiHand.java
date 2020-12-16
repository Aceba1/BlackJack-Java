package com.aceba1.blackjack.hand;

import com.aceba1.blackjack.Wallet;
import com.aceba1.util.Input;

public class PlayerMultiHand extends Player {
  public PlayerMultiHand(String name, Wallet wallet, int maxHands) {
    super(name, wallet);
    this.maxHands = maxHands;
  }

  public int maxHands = 3;

  @Override
  public void bet() {
    hands.clear();

    System.out.print(name + " - ");
    int count = Input.getNum("Number of Hands [1-" + maxHands + "]: ", 1, maxHands);

    for (int hand = 1; hand <= count; hand++) {
      System.out.println("\n- Hand " + hand);
      hands.add(new Hand(getBet()));
    }
  }
}
