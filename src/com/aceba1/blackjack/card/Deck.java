package com.aceba1.blackjack.card;

public interface Deck {
  int getSize();

  boolean isEmpty();

  void returnCard(Card card);

  Card pullCard();

  String getName();
}
