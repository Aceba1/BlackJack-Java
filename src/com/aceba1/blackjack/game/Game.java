package com.aceba1.blackjack.game;

import com.aceba1.blackjack.card.Deck;
import com.aceba1.blackjack.hand.Hand;
import com.aceba1.blackjack.hand.Holder;

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

  public Game() { }

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

    for (int h = 0; h < holders.size(); h++)
      startHolder(h, holders.get(h));

    sleep(1000);

    // Run the game!
    play();
  }

  private void startHolder(int holderIndex, Holder holder) {
    for (int i = 0; i < holder.getHandCount(); i++) {
      Hand hand = holder.getHand(i);
      hand.drawCard(deck, true);
      hand.drawCard(deck, false);
      System.out.println(holderIndex + " > " + holder.getName() + " - " + hand);
    }
  }

  private void play() {
    // Start the game
    System.out.println("\nROUND START!");

    // Go through all the players and their hands
    for (int i = 0; i < holders.size(); i++) {
      Holder holder = holders.get(i);
      sleep(500);

      System.out.println("\n" + i  + " > "+ holder.getName() + "'s turn!");

      // This expands to the current
      for (int hand = 0; hand < holder.getHandCount(); hand++)
        playHands(holder, hand);
    }

    sleep(500);
    System.out.println("\nROUND FINISH!\n");
    sleep(1000);

    // End the game!
    finish();

    sleep(1000); // Wait a little bit before returning to menu
  }

  private void playHands(Holder holder, int handIndex) {
    Hand hand = holder.getHand(handIndex);

    if (hand.getValue() == 21) {
      System.out.println("BLACKJACK");

      //hand.bet *= 1.25;
      // Multiplying here is risky for data loss, so we set a boolean
      hand.winByBlackjack = true;

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

    // Will loop the hand's turn, modifying the cycle value as it goes
    int cycle = 1;
    while (cycle != -1)
      cycle = handTurn(holder, hand, cycle);
  }

  // Will return the new cycle number, or -1 to break
  private int handTurn(Holder holder, Hand hand, int cycle) {
    switch (holder.turn(hand, cycle, dealerHand.getPublicValue())) {
      // HIT
      case 1 -> {
        return doHit(hand, cycle);
      }
      // STAY
      case 2 -> {
        return doStay();
      }
      // DOUBLE
      case 3 -> {
        return doDouble(holder, hand);
      }
      // SPLIT
      case 4 -> {
        return doSplit(holder, hand);
      }

      default -> {
        return cycle;
      }
    }
  }

  private int doHit(Hand hand, int cycle) {
    System.out.println("HIT");
    hand.drawCard(deck, true);

    if (!hand.isBust())
      return cycle + 1;

    System.out.println(hand.toStringAuth()); //Bust
    return -1; // Break
  }

  private int doStay() {
    System.out.println("STAY");

    return -1; // Break
  }

  private int doDouble(Holder holder, Hand hand) {
    System.out.println("DOUBLE");

    holder.modifyFunds(-hand.bet); // Deduct funds
    hand.bet *= 2; // Double the bet value
    hand.drawCard(deck, true); // Take a card

    if (hand.isBust())
      System.out.println(hand.toStringAuth()); // Bust
    else
      holder.viewHand(hand);

    return -1;  // Break
  }

  private int doSplit(Holder holder, Hand hand) {
    System.out.println("SPLIT");

    Hand newHand = new Hand(hand.bet);
    newHand.giveCard(hand.pullCard(hand.getSize() - 1));
    holder.addHand(newHand);

    // After splitting, each hand gets a new card
    hand.drawCard(deck, true);
    newHand.drawCard(deck, true);

    // Replay this hand
    // Allow for DOUBLE
    return 1;
  }

  private void finish() {

    Hand dealer = getDealerHand();

    for (int h = 0; h < holders.size() - 1; h++) {
      Holder player = holders.get(h);
      for (int i = 0; i < player.getHandCount(); i++) {
        Hand hand = player.getHand(i);

        player.modifyFunds(payout(hand, dealer));

        System.out.println(h + " > " + player.getName() + " - " + hand.toStringAuth() + "\n");
        hand.resetGameState();
        hand.returnAllCards(deck);
      }
    }

    System.out.println("Dealer - " + dealer.toStringAuth() + "\n");
    dealer.resetGameState();
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
      payout = player.bet;
      suffix = " (insured)";
    } else {
      payout = 0;
      suffix = "";
    }

    // Returns
    if (pValue == 0 || pValue < dValue) { // If the player busts, they lose no matter what
      System.out.println("LOSE : " + payout + suffix);
      return payout;
    }
    if (pValue == dValue && player.winByBlackjack == dealer.winByBlackjack) { // If it's a tie, or both win by BlackJack
      payout += player.bet;
      System.out.println("PUSH : " + payout + suffix);
      return payout;
    }
    if (pValue > dValue) {
      payout += player.winByBlackjack ? (int)(player.bet * 2.5) : (player.bet * 2);
      System.out.println("WIN : " + payout + suffix);
      return payout;
    }
  }

  public final void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch(Exception ignored) { }
  }
}
