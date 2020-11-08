package com.aceba1.blackjack.game;

import com.aceba1.blackjack.card.Deck;
import com.aceba1.blackjack.hand.Hand;
import com.aceba1.blackjack.hand.Holder;

import java.util.ArrayList;
import java.util.List;

//TODO: Refactor to solid class

public final class Game {

  Deck deck;

  /** The dealer should be the last item in this array */
  List<Holder> holders;

  Hand dealerHand = null;

  Hand getDealerHand() {
    return holders
      .get(holders.size() - 1)
      .getHand(0);
  }

  public Game() {

  }

  public Game(Deck deck, List<Holder> holders) {
    configure(deck, holders);
  }

  public void configure(Deck deck, List<Holder> holders) {
    setDeck(deck);
    setHolders(holders);
  }

  public void setDeck(Deck deck) {
    this.deck = deck;
  }

  public void setHolders(List<Holder> holders) {
    this.holders = holders;
  }

  public void start() {

    System.out.println("\nPLACING BETS...\n");

    for (Holder holder : holders)
      holder.bet();

    // Store the dealer's hand in a field
    dealerHand = getDealerHand();

    System.out.println("\nPASSING CARDS...\n");

    for (var holder : holders)
      startHolder(holder);

    getDealerHand().isBlackJack();

    sleep(1500);

    // Run the game!
    play();
  }

  private void startHolder(Holder holder) {
    for (int i = 0; i < holder.getHandCount(); i++) {
      Hand hand = holder.getHand(i);
      hand.drawCard(deck, true);
      hand.drawCard(deck, false);
      System.out.println(holder.getName() + " - " + hand);
    }
  }

  private void play() {
    // Start the game
    System.out.println("\nROUND START!");

    // Go through all the players and their hands
    for (Holder holder : holders) {
      sleep(750);

      System.out.println("\n" + holder.getName() + "'s turn!");

      // This expands to the current
      for (int hand = 0; hand < holder.getHandCount(); hand++)
        playHands(holder, hand);
    }

    sleep(750);
    System.out.println("\nROUND FINISH!\n");
    sleep(750);

    // End the game!
    finish();
  }

  private void playHands(Holder holder, int handIndex) {
    int cycle = 1;
    Hand hand = holder.getHand(handIndex);

    if (hand.getValue() == 21) {
      System.out.println("BLACKJACK");
      return;
    }

    if (hand != dealerHand && dealerHand.getPublicValue() == 11) {
      holder.viewHand(hand);
      if (holder.insurance(hand)) {
        // We already know, so we can modify their funds now
        if (dealerHand.isBlackJack()) {
          holder.modifyFunds(hand.bet);
          hand.insuredCovered = true;
        } else {
          holder.modifyFunds(-hand.bet / 2);
        }
      }
    }

    while (true) {
      switch (holder.turn(hand, cycle++, dealerHand.getPublicValue())) {
        // HIT
        case 1 -> {
          System.out.println("HIT");
          hand.drawCard(deck, true);

          if (!hand.isBust())
            continue;

          System.out.println(hand.toStringAuth()); //Bust
          return;
        }
        // STAY
        case 2 -> {
          System.out.println("STAY");

          return;
        }
        // DOUBLE
        case 3 -> {
          System.out.println("DOUBLE");
          hand.bet *= 2; // Double the bet value
          hand.drawCard(deck, true); // Take a card

          if (hand.isBust())
            System.out.println(hand.toStringAuth()); //Bust
          else
            holder.viewHand(hand);
          return;
        }
        case 4 -> {
          //TODO: Implement SPLIT!
          // Can insert to hands with index
          // Need a way to remove at end
          System.out.println("SPLIT");

          Hand newHand = new Hand(hand.bet);
          newHand.giveCard(hand.pullCard(hand.getSize() - 1));

          holder.addHand(newHand);
          // Do not return, replay this hand
          // Allow for DOUBLE
          cycle = 1;
        }
      }
    }
  }

  private void finish() {

    Hand dealer = getDealerHand();

    for (int h = 0; h < holders.size() - 1; h++) {
      Holder player = holders.get(h);
      for (int i = 0; i < player.getHandCount(); i++) {
        Hand hand = player.getHand(i);

        player.modifyFunds(payout(hand, dealer));

        System.out.println(player.getName() + " - " + hand.toStringAuth() + "\n");
        hand.returnAllCards(deck);
      }
    }

    System.out.println("Dealer - " + dealer.toStringAuth() + "\n");
    dealer.returnAllCards(deck);
  }

  private int valueOrBust(Hand hand) {
    int value = hand.getValue();
    if (value > 21) return 0;
    return value;
  }

  private int payout(Hand player, Hand dealer) {
    int pValue = valueOrBust(player);
    int dValue = valueOrBust(dealer);

    int payout;
    String suffix;
    if (player.insuredCovered) {
      payout = player.insuredCovered ? player.bet : 0;
      suffix = " (insured)";
    } else {
      payout = 0;
      suffix = "";
    }

    if (pValue > dValue) {
      payout += player.bet * 2;
      System.out.println("WIN : " + payout + suffix);
      return payout;
    }
    if (pValue == 0 || pValue < dValue) { // If the player busts, they lose no matter what!
      System.out.println("LOSE : " + payout + suffix);
      return 0;
    }
    payout += player.bet;
    System.out.println("PUSH : " + payout + suffix);
    return player.bet;
  }

  public final void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch(Exception E) { }
  }
}
