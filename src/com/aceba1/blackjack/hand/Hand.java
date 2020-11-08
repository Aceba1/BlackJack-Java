package com.aceba1.blackjack.hand;

import com.aceba1.blackjack.card.Card;
import com.aceba1.blackjack.card.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Hand {

  private final List<Card> cards = new ArrayList<>(4); // Holds the cards

  public Hand(int initialBet) {
    this.bet = initialBet;
  }

  // Unique to BlackJack
  private int aceCount = 0;
  private int topValue = 0;
  public int bet = 0; // This will be used directly

  public boolean insuredCovered; // Only for Insurance

  public int getSize() {
    return cards.size();
  }

  // Safe displayable value
  public int getPublicValue() {
    int publicValue = cards.stream()
      .filter(c -> c.show)
      .mapToInt(c -> c.value == 1 ? 11 : Math.min(c.value, 10))
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

  public void drawCard(Deck deck, boolean isPrivate) {
    Card card = deck.pullCard();
    card.setShow(!isPrivate);
    giveCard(card);
  }

  public void giveCard(Card card) {
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
      "\t| Cards: " + cards.stream()
      .map(Card::toStringAuth)
      .collect(Collectors.joining(" "));
  }

  @Override
  public String toString() {
    return "Value: " + getPublicValue() +
      "\t| Cards: " + cards.stream()
        .map(Card::toString)
        .collect(Collectors.joining(" "));
  }

  public boolean isBlackJack() {
    return topValue == 21 && cards.size() == 2; // Can only be blackjack with an Ace and 10 card
  }

  public boolean isSoft() {
    return aceCount != 0;
  }

  public boolean isBust() {
    return topValue - aceCount * 10 > 21;
  }

  public int peekFaceValue(int index) {
    return cards.get(index).value;
  }

  public boolean canSplit() { //TODO: Check if rules allow splitting size over 2?
    return cards.size() == 2 &&
      cards.get(0).value == cards.get(1).value;
  }
}
