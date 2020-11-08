package com.aceba1.blackjack.card;

import com.aceba1.util.Input;

public class RiggedDeck implements Deck {
  @Override
  public String getName() {
    return "Rigged Deck";
  }

  @Override
  public int getSize() {
    return 999;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public void returnCard(Card card) {

  }

  @Override
  public Card pullCard() {
    System.out.println(">>> Inject card");
    return new Card(
      (byte)Input.getNum("> FaceID[1-13]: ", 1, 13),
      (byte)4
    );
  }
}
