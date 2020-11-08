package com.aceba1.blackjack.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShuffledDeck implements Deck {

  private static final int
    CARDS_FACE_START = 1, // ACE ...
    CARDS_FACE_END = 13,  // ... KING (No JOKER:14)
    CARDS_SUIT_START = 0, // CLOVER ...
    CARDS_SUIT_END = 3;   // ... STAR
  // 52 cards total (no jokers)

  @Override
  public String getName() {
    return "Ever-Shuffled Deck";
  }

  final List<Card> cards = new ArrayList<>(52);
  final Random random = new Random();

  public ShuffledDeck() {
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

    // Gets face variants
    for (byte face = CARDS_FACE_START; face <= CARDS_FACE_END; face++) {
      // Gets suit variants
      for (byte type = CARDS_SUIT_START; type < CARDS_SUIT_END; type++) {
        cards.add(new Card(face, type));
      }
    }
  }
}
