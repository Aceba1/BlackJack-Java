package com.aceba1.blackjack.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface Deck {
  int getSize();

  boolean isEmpty();

  void returnCard(Card card);

  Card pullCard();

  String getName();
}
