package com.aceba1.blackjack.hand;

import java.util.ArrayList;
import java.util.List;

public interface Holder{

  void modifyFunds(int amount);

  int getHandCount();
  Hand getHand(int index);
  void addHand(Hand newHand);

  /**
   * If the dealer has an ace visible, prompt for insurance
   * @param hand The holder's hand in question
   * @return True to pay for insurance (If dealer has blackjack, lose nothing, otherwise lose half of bet)
   */
  boolean insurance(Hand hand);

  /**
   * This is the choice stage of the game for this behavior. It may loop or break as necessary
   * @param hand The holder's hand in question
   * @param iterNum How many turns this behavior has taken, starts at 1
   * @param visibleDealerHand The dealer's public card(s), Ace is 11
   * @return 1:HIT, 2:STAY, 3:DOUBLE, 4:SPLIT
   */
  int turn(Hand hand, int iterNum, int visibleDealerHand);

  /**
   * Optional function to print the hand to log after ending the turn with an unknown change
   * (Double / Split / Insurance)
   * @param hand The holder's hand in question
   */
  void viewHand(Hand hand);

  /**
   * This runs every start of the match.
   * It is expected for the value to be set to the Hand, and ensure at least one hand is present.
   */
  void bet();

  /**
   *
   * @return Unique name of
   */
  String getName();

  //TODO: Move behaviors to Holder types!
  //
}
