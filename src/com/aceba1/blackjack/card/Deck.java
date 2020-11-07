package com.aceba1.blackjack.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
  List<Card> cards = new ArrayList<>(52);
  Random random = new Random();

  public Deck() {
    initialize();
  }

  public int getSize() {
    return cards.size();
  }

  public boolean isEmpty() {
    return cards.size() == 0;
  }

  public void returnCard(Card card) {
    cards.add(card);
  }

  public Card pullCard() {
    int deckSize = cards.size();
    if (deckSize == 0) throw new RuntimeException("pullCard() : There are no cards left!");

    // Decrement for use. Representation of statement is nice despite irrelevance
    deckSize--;

    // We use a random to have the deck 'always shuffled'
    int randomPull = random.nextInt(deckSize);
    Card card = cards.get(randomPull);

    // This gets the last card and puts it where we pulled a card from
    // and removes the otherwise duplicate entry from the end.
    // End result would be the same as removing directly due to random access,
    // but knowing we need no real order removes having to shift other entries.
    cards.set(randomPull, cards.remove(deckSize)); // remove 'pops' the value!

    return card;
  }

  public void initialize() {
    cards.clear();
    // Gets Ace, Face, Jack, Queens and Kings
    for (byte face = 1; face <= 13; face++) {
      // Gets Clovers, Hearts, Spades and Stars
      for (byte type = 0; type < 4; type++) {
        cards.add(new Card(face, type));
      }
    }
    // 52 cards total, no jokers
  }
}
