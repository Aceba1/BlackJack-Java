package com.aceba1.blackjack.hand;

import com.aceba1.blackjack.card.Card;
import com.aceba1.blackjack.card.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Hand implements Behavior {
  List<Card> cards = new ArrayList<>(8);
  int aceCount = 0;
  int topValue = 0;

  public int getSize() {
    return cards.size();
  }

  public int getPublicValue() {
    int publicValue = cards.stream()
      .filter(c -> c.show)
      .mapToInt(c -> Math.min(c.value, 10))
      .sum();

    if (publicValue > 21 && aceCount > 0) {
      return publicValue - Math.min((publicValue - 11) / 10, aceCount) * 10;
    }
    return publicValue;
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

  public void returnAllCards(Deck deck) {
    for (int i = cards.size() - 1; i >= 0; i--)
      deck.returnCard(pullCard(i));
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

  public String toStringAuth() {
    int value = getValue();
    return "Value: " + value + (value > 21 ? " - BUST" : "") +
      "\nCards: " + cards.stream()
      .map(Card::toStringAuth)
      .collect(Collectors.joining(" "));
  }

  @Override
  public String toString() {
    return "Value: " + getPublicValue() +
      "\nCards: " + cards.stream()
        .map(Card::toString)
        .collect(Collectors.joining(" "));
  }
}
