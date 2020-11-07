package com.aceba1.blackjack.game;

import com.aceba1.blackjack.card.Deck;
import com.aceba1.blackjack.hand.Hand;

import java.util.ArrayList;
import java.util.List;

public abstract class Game {
  Deck deck = new Deck();
  List<Hand> hands = new ArrayList<>();
  int[] bets;

  private void playHand(int index, Hand hand) {
    int cycle = 1;
    while (true) {
      switch (hand.turn(cycle++)) {

        case 1: // HIT
          System.out.println("HIT");
          hand.drawCard(deck, true);
          if (hand.isBust()) {
            System.out.println(hand.toStringAuth());
            return;
          }
          break;

        case 2: // STAND
          System.out.println("STAND");
          return;

        case 3: // DOUBLE
          System.out.println("DOUBLE");
          bets[index] *= 2; // Double the bet value
          hand.drawCard(deck, true); // Take a card
          if (hand.isBust())
            System.out.println(hand.toStringAuth());
          return;

        case 4:
          System.out.println("SPLIT");
          //TODO: Implement SPLIT!
          // Can insert to hands with index
          // Need a way to remove at end
          break;
      }
    }
  }

  private void play() {
    System.out.println("\nROUND START!");

    for (int i = 0; i < hands.size(); i++) {
      sleep(750);

      Hand hand = hands.get(i);
      System.out.println("\n" + hand.getName() + "'s turn!");
      playHand(i, hand);
    }

    sleep(750);
    System.out.println("\nROUND FINISH!\n");
    sleep(750);

    // End the game!
    finish();
  }

  private void startHand(Hand hand) {
    hand.drawCard(deck, true);
    hand.drawCard(deck, false);
    System.out.println(hand.getName() + " - " + hand);
  }

  public void start() {
    if (bets == null)
      bets = new int[hands.size()];

    for (int i = 0; i < hands.size(); i++) {
      bets[i] = hands.get(i).bet();
    }

    System.out.println("\nPASSING CARDS...\n");

    for (var hand : hands)
      startHand(hand);

    sleep(1500);

    // Run the game!
    play();
  }

  protected void finish() {
    System.out.println();
    for (var hand : hands) {
      System.out.println(hand.getName() + " - " + hand.toStringAuth());
      hand.returnAllCards(deck);
    }
  }

  public void sleep(long millisec) {
    try {
      Thread.sleep(millisec);
    } catch(Exception E) { }
  }
}
