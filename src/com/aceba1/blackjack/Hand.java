package com.aceba1.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {
  List<Card> cards = new ArrayList<>(8);
  int aceCount = 0;
  int topValue = 0;

  public int getSize() {
    return cards.size();
  }

  public int getValue() {
    if (topValue > 21 && aceCount > 0) {
      return topValue - Math.min((topValue - 11) / 10, aceCount) * 10;
    }
    return topValue;
  }

  public boolean isBust() {
    return topValue - aceCount * 10 > 21;
  }

  public void drawCard(Deck deck, boolean isPrivate) {
    Card card = deck.pullCard();
    card.setShow(!isPrivate);
    cards.add(card);

    topValue += Math.min(card.value, 10);
    if (card.value == 1) { // We push down the ace card value later, if it's too big
      aceCount++;
      topValue += 10;
    }
  }

  public Card pullCard(int index) {
    Card card = cards.remove(index);

    topValue -= Math.min(card.value, 10);
    if (card.value == 1) {
      aceCount--;
      topValue -= 10;
    }

    return card;
  }

  public void start(Deck deck) {
    drawCard(deck, true);
    drawCard(deck, false);
  }

  @Override
  public String toString() {
    return "Value: " + getValue() + "\nCards: " + cards.stream().map(Card::toString).collect(Collectors.joining(" "));
  }
}
